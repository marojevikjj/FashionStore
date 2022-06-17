//package onlineshopapp.fashionstore.integration;
//
//import onlineshopapp.fashionstore.model.enumerations.Role;
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
//@ActiveProfiles("prod")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
//public class RegisterIntegrationTest {
//    //Register - Get register page
//    MockMvc mockMvc;
//
//
//    @Autowired
//    UserService userService;
//
//    @BeforeEach
//    public void setup(WebApplicationContext wac) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//    }
//    @Test
//    public void testGetRegisterPage() throws Exception {
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/register");
//        this.mockMvc.perform(request)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("register"));
//
//    }
//    @Test
//    public void testGetSuccessfulRegistration() throws Exception {
//
//        MockHttpServletRequestBuilder events = MockMvcRequestBuilders.post("/register")
//                .param("username","marojevikjovana").param("password","username123")
//                .param("repeatedPassword","username123").param("name","name").param("email","marojevikjovana@gmail.com");
//
//        this.mockMvc.perform(events)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attribute("email", "marojevikjovana@gmail.com"))
//                .andExpect(MockMvcResultMatchers.view().name("successfulRegistration"));
//
//    }
//}
