//package onlineshopapp.fashionstore.integration;
//
//import onlineshopapp.fashionstore.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
//public class LogOutIntegrationTest {
//    //Log out
//    MockMvc mockMvc;
//
//
//    @BeforeEach
//    public void setup(WebApplicationContext wac) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//    }
//    @Test
//    public void testLogOut() throws Exception {
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/logout");
//        this.mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
//
//    }
//}
