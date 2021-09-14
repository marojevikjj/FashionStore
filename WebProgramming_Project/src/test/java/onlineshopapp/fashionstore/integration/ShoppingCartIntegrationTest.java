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
import org.springframework.web.context.WebApplicationContext;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ShoppingCartIntegrationTest {

    MockMvc mockMvc;

    @Autowired
    UserService userService;
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    OrderedClothesService orderedClothesService;
    @Autowired
    ClothesService clothesService;

    private static boolean dataInitialized = false;
    private static User user;
    private static ShoppingCart shoppingCart;
    private static OrderedClothes orderedClothes;
    private static Clothes clothes;
    private static User userWithoutCart;

    @BeforeEach
    public void setup(WebApplicationContext wac) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        initData();
    }

    private void initData() {

        if(!dataInitialized){
            user = userService.register("user", "user", "user", "user", Role.ROLE_USER, "useruser@gmail.com");
            shoppingCart = shoppingCartService.getActiveShoppingCart("user");
            clothes = clothesService.create("clothes 1", null, null, null, null,null,
                    100, 0 ,10, 10, 10, 10);
            clothes.setId((long) 1);
            orderedClothes = orderedClothesService.addNewOrderedClothes(new OrderedClothes(clothes, 1, "S", 100.0, (long) 10));
            shoppingCartService.addProductToShoppingCart("user", orderedClothes);
            userWithoutCart = userService.register("userWithoutCart", "userWithoutCart", "userWithoutCart", "userWithoutCart",
                    Role.ROLE_USER, "userWithoutCart@gmail.com");
            shoppingCartService.getActiveShoppingCart(userWithoutCart.getUsername()).setOrderedClothes(null);
            dataInitialized = true;
        }

    }


    @Test
    @WithMockUser(username = "user")
    public void testGetShoppingCartPage() throws Exception {

        MockHttpServletRequestBuilder shoppingCartRequest = MockMvcRequestBuilders.get("/shoppingCart")
                .with(request -> {request.setRemoteUser("user");
                    return request;});
        this.mockMvc.perform(shoppingCartRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("bodycContents"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodycContents", "shoppingCart"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("total"))
                .andExpect(MockMvcResultMatchers.model().attribute("total", orderedClothes.getPrice()))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("empty"))
                .andExpect(MockMvcResultMatchers.view().name("cart"));
    }

    @Test
    @WithMockUser(username = "userWithoutCart")
    public void testGetShoppingCartPageNewCart() throws Exception {

        MockHttpServletRequestBuilder shoppingCartRequest = MockMvcRequestBuilders.get("/shoppingCart")
                .with(request -> {request.setRemoteUser("userWithoutCart");
                    return request;});
        this.mockMvc.perform(shoppingCartRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("products"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("bodycContents"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("total"))
                .andExpect(MockMvcResultMatchers.view().name("cart"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAddProductToShoppingCart() throws Exception {

        MockHttpServletRequestBuilder shoppingCartRequest = MockMvcRequestBuilders.post("/shoppingCart/add/" + clothes.getId())
                .param("size", "S")
                .param("quantity", "1")
                .principal(getPrincipal("user"));

        this.mockMvc.perform(shoppingCartRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/shoppingCart"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAddProductToShoppingCartError() throws Exception {

        MockHttpServletRequestBuilder shoppingCartRequest = MockMvcRequestBuilders.post("/shoppingCart/add/" + clothes.getId())
                .param("size", "S")
                .param("quantity", "200")
                .principal(getPrincipal("user"));

        this.mockMvc.perform(shoppingCartRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products?error=Requested quantity is not available at the moment!"));
    }

    protected UsernamePasswordAuthenticationToken getPrincipal(String username) {

        UserDetails user = this.userService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities());

        return authentication;
    }

    @Test
    @WithMockUser(username = "user")
    public void testDeleteProductFromShoppingCart() throws Exception {

        MockHttpServletRequestBuilder shoppingCartRequest = MockMvcRequestBuilders.post("/shoppingCart/delete/" + orderedClothes.getId())
                .with(request -> {request.setRemoteUser("user");
                    return request;});

        this.mockMvc.perform(shoppingCartRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/shoppingCart"));
    }
}





















