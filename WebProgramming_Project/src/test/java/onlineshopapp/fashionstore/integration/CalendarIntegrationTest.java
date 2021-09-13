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
public class CalendarIntegrationTest {

    MockMvc mockMvc;
    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    AuthService authService;

    private static boolean dataInitialized = false;




    @Test
    void contextLoads() {
    }
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

    //Calendar controller tests
    @Test
    public void testGetEvents() throws Exception {
        MockHttpServletRequestBuilder events = MockMvcRequestBuilders.get("/allevents");
        this.mockMvc.perform(events)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

    }

    //Calendar - getCalendar, getAdminCalendar, getEventList
    @Test
    public void testGetCalendar() throws Exception {
        MockHttpServletRequestBuilder events = MockMvcRequestBuilders.get("/calendar");
        this.mockMvc.perform(events).andExpect(MockMvcResultMatchers.view().name("calendar")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testGetAdminCalendar() throws Exception {
        MockHttpServletRequestBuilder events = MockMvcRequestBuilders.get("/admin-calendar");
        this.mockMvc.perform(events).andExpect(MockMvcResultMatchers.view().name("admin-calendar"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
