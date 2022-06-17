//package onlineshopapp.fashionstore.integration;
//
//import onlineshopapp.fashionstore.model.Order;
//import onlineshopapp.fashionstore.model.User;
//import onlineshopapp.fashionstore.model.enumerations.OrderStatus;
//import onlineshopapp.fashionstore.model.enumerations.Role;
//import onlineshopapp.fashionstore.service.OrderService;
//import onlineshopapp.fashionstore.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import java.util.ArrayList;
//import java.util.List;
//
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
//public class PostmanIntegrationTest {
//
//    MockMvc mockMvc;
//
//    @Autowired
//    OrderService orderService;
//    @Autowired
//    UserService userService;
//
//    private static boolean dataInitialized = false;
//    private static Order order;
//
//    @BeforeEach
//    public void setup(WebApplicationContext wac) {
//
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//        initData();
//    }
//
//    public void initData() {
//
//        if(!dataInitialized) {
//            String postmanName = "postman";
//            String userName = "user";
//            User postman = this.userService.register(postmanName, postmanName, postmanName, postmanName,
//                    Role.ROLE_POSTMAN, "postman@gmail.com");
//            User user = this.userService.register(userName, userName, userName, userName,
//                    Role.ROLE_USER, "user@gmail.com");
//            order = this.orderService.createOrder("name", "surname", "address",
//                    "telephone", "city", 10, user, null, postman);
//            order.setId((long) 1);
//            dataInitialized = true;
//        }
//    }
//
//    @Test
//    @WithMockUser(username = "postman", roles={"POSTMAN"})
//    public void testGetPostmanOrders() throws Exception {
//
//        MockHttpServletRequestBuilder postmanRequest = MockMvcRequestBuilders.get("/postman/orders")
//                .with(request -> {request.setRemoteUser("postman");
//                return request;});
//        List<Order> orders = new ArrayList<>();
//        orders.add(order);
//        this.mockMvc.perform(postmanRequest)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("postmanOrders"));
//    }
//
//    @Test
//    @WithMockUser(username = "postman", roles={"POSTMAN"})
//    public void testChangeOrderStatus() throws Exception {
//
//        MockHttpServletRequestBuilder postmanRequest = MockMvcRequestBuilders.post("/postman/changeStatus/" + order.getId())
//                .param("status", OrderStatus.OnYourWay.toString());
//        this.mockMvc.perform(postmanRequest)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/postman/orders"));
//    }
//
//
//}
