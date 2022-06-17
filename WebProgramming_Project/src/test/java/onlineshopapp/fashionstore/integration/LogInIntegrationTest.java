//package onlineshopapp.fashionstore.integration;
//
//import onlineshopapp.fashionstore.model.enumerations.Role;
//import onlineshopapp.fashionstore.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.servlet.http.HttpServletRequest;
//
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
//public class LogInIntegrationTest {
//    //Log in - getLoginPage, log in
//    MockMvc mockMvc;
//
//
//    @Autowired
//    UserService userService;
//
//    @BeforeEach
//    public void setup(WebApplicationContext wac) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//        initData();
//    }
//    private void initData() {
//
//        userService.register("user","user","user","user",Role.ROLE_USER,"useruser@gmail.com");
//        userService.register("admin","admin","admin","admin",Role.ROLE_ADMIN,"admin@gmail.com");
//
//    }
//    @Test
//    public void testGetLogInPage() throws Exception {
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/login");
//        this.mockMvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("login"));
//
//    }
//    @Test
//    public void testGetLogInPageFail() throws Exception {
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/login").param("error","error");
//        this.mockMvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
//                .andExpect(MockMvcResultMatchers.view().name("login"));
//
//    }
//
//    @Test
//    public void testSuccessLoginUser() throws Exception {
//        userService.register("adminnew","adminnew","adminnew","adminnew",Role.ROLE_ADMIN,"adminnew@gmail.com");
//
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.addParameter("username","adminnew");
//        request.addParameter("password","adminnew");
//
//
//        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/login").requestAttr("request",request);
//        this.mockMvc.perform(productRequest).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/products"));
//
//    }
//
//    @Test
//    public void testFailLogIn() throws Exception {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.addParameter("username","name");
//        request.addParameter("password","password");
//
//        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/login").requestAttr("request",request);
//        this.mockMvc.perform(productRequest).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error=Incorrect username or password"));
//
//    }
//
//
//}
//
//
//
