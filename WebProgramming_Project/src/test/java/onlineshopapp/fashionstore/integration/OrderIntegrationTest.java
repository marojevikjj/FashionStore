package onlineshopapp.fashionstore.integration;

import onlineshopapp.fashionstore.model.*;
import onlineshopapp.fashionstore.model.enumerations.Role;
import onlineshopapp.fashionstore.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class OrderIntegrationTest {

    MockMvc mockMvc;

    @Autowired
    UserService userService;
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    UserVoucherService userVoucherService;
    @Autowired
    VoucherService voucherService;
    @Autowired
    OrderService orderService;
    @Autowired
    PostmanOrderSerivce postmanOrderSerivce;
    @Autowired
    ClothesService clothesService;
    @Autowired
    OrderedClothesService orderedClothesService;

    private static boolean dataInitialized = false;
    private static User ordinaryUser;
    private static User postmanUser;
    private static User ordinaryUser2;
    private static Order order;
    private static ShoppingCart shoppingCart;
    private static Clothes clothes;
    private static OrderedClothes orderedClothes;

    @BeforeEach
    public void setup(WebApplicationContext wac) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        initData();
    }

    private void initData() {

        if(!dataInitialized) {

            ordinaryUser = userService.register("user", "user", "user", "user", Role.ROLE_USER, "useruser@gmail.com");
            ordinaryUser.setId((long) 1);
            ordinaryUser2 = userService.register("user2", "user2", "user2", "user2", Role.ROLE_USER, "useruser2@gmail.com");
            ordinaryUser2.setId((long) 10);
            postmanUser = userService.register("postmanUser", "postmanUser", "postmanUser", "postmanUser", Role.ROLE_USER, "postmanUser@gmail.com");
            postmanUser.setId((long) 2);
            postmanOrderSerivce.create(postmanUser, "Paris");
            clothes = clothesService.create("clothes 1", null, null, null, null,null,
                    100, 0 ,10, 10, 10, 10);
            orderedClothes = orderedClothesService.addNewOrderedClothes(new OrderedClothes(clothes, 1, "S", 100.0, (long) 10));
            shoppingCart = shoppingCartService.getActiveShoppingCart("user");
            shoppingCartService.addProductToShoppingCart("user", orderedClothes);
            order = orderService.createOrder("name", "surname", "address", "telephone", "Paris",
                    1000.0, ordinaryUser, shoppingCart.getOrderedClothes(), postmanUser);
            dataInitialized = true;
        }
    }

    @Test
    public void testShowOrderUserPage() throws Exception {

        MockHttpServletRequestBuilder orderRequest = MockMvcRequestBuilders.get("/orders")
                .with(request -> {request.setRemoteUser("user2");
                    return request;});

        this.mockMvc.perform(orderRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orders"))
                .andExpect(MockMvcResultMatchers.model().attribute("orders", new ArrayList<>()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("orders"));
    }

    @Test
    public void testShowOrderPostmanPage() throws Exception {

        MockHttpServletRequestBuilder orderRequest = MockMvcRequestBuilders.get("/orders")
                .with(request -> {request.setRemoteUser("postmanUser");
                    return request;});

        this.mockMvc.perform(orderRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orders"))
                .andExpect(MockMvcResultMatchers.model().attribute("orders", new ArrayList<>()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("orders"));
    }

    @Test
    public void testMakeOrderGetPage() throws Exception {

        MockHttpServletRequestBuilder orderRequest = MockMvcRequestBuilders.get("/makeOrder/discountPrice")
                .param("name", "name").param("surname", "surname")
                .param("address", "address").param("telephone", "2345678")
                .param("card", "2345667").param("total", "1000.0")
                .param("city", "Paris")
                .with(request -> {request.setRemoteUser("user");
                    return request;});
        this.mockMvc.perform(orderRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("name"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("surname"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("address"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("telephone"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("card"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("total"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("city"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("discount"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.model().attribute("error", "* Input field for voucher code is required!"))
                .andExpect(MockMvcResultMatchers.view().name("makeOrder"));
    }

    @Test
    public void testMakeOrderPostPage() throws Exception {

        MockHttpServletRequestBuilder orderRequest = MockMvcRequestBuilders.post("/makeOrder")
                .param("total", "1000.0")
                .with(request -> {request.setRemoteUser("user");
                    return request;});
        this.mockMvc.perform(orderRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("total"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("discount"))
                .andExpect(MockMvcResultMatchers.model().attribute("discount", 1000.0))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.view().name("makeOrder"));
    }

    @Test
    public void testPurchaseProducts() throws Exception {

        MockHttpServletRequestBuilder orderRequest = MockMvcRequestBuilders.post("/purchase")
                .param("name", "name").param("surname", "surname")
                .param("address", "address").param("telephone", "2345678")
                .param("card", "2345667").param("discount", "1000.0")
                .param("total", "1000.0").param("city", "Paris")
                .with(request -> {request.setRemoteUser("user");
                    return request;});
        this.mockMvc.perform(orderRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attribute("orders", this.orderService.findOrdersByUser((User) this.userService.loadUserByUsername("user"))))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/orders"));
    }

}
