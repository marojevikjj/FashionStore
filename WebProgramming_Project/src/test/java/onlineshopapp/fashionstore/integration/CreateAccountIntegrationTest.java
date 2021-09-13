package onlineshopapp.fashionstore.integration;

import onlineshopapp.fashionstore.model.enumerations.Role;
import onlineshopapp.fashionstore.service.AuthService;
import onlineshopapp.fashionstore.service.EventService;
import onlineshopapp.fashionstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class CreateAccountIntegrationTest {
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    AuthService authService;

    private void initData() {
        //za inicijalizirame na podatoci
        //users,events,products
        String user = "user";
        String admin = "admin";

        userService.register(user, user, user, user, Role.ROLE_USER, "user@gmail.com");
        userService.register(admin, admin, admin, admin, Role.ROLE_ADMIN, "admin@gmail.com");

    }

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    //Create account - getCreateAccountPage, createAccount
    //ne rabotat tie sto se so preauthorize admin

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testGetCreateAccountPage() throws Exception {
        MockHttpServletRequestBuilder events = MockMvcRequestBuilders.get("/create-account");
        this.mockMvc.perform(events).andExpect(MockMvcResultMatchers.view().name("createAccount"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testGetCreateAccountPageError() throws Exception {
        MockHttpServletRequestBuilder events = MockMvcRequestBuilders.get("/create-account").param("error", "error");
        this.mockMvc.perform(events).andExpect(MockMvcResultMatchers.view().name("createAccount"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.model().attribute("error", "error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testCreateAccountFail() throws Exception {
        MockHttpServletRequestBuilder events = MockMvcRequestBuilders.post("/create-account").param("error", "error");
        this.mockMvc.perform(events).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("redirect:/create-account?error="));

    }
    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testCreateAccount() throws Exception {
        MockHttpServletRequestBuilder events = MockMvcRequestBuilders.post("/create-account")
                .param("username","username").param("password","password")
                .param("repeatedPassword","password").param("name","name").param("email","j@gmail.com").param("role",Role.ROLE_USER.toString());


        this.mockMvc.perform(events)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.view().name("createAccount"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("success"))
                .andExpect(MockMvcResultMatchers.model().attribute("success", true))
                .andExpect(MockMvcResultMatchers.status().isOk());
//        this.mockMvc.perform(events).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("redirect:/create-account?error="));

    }

}
