package onlineshopapp.fashionstore.integration;

import onlineshopapp.fashionstore.model.Event;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.Role;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class EventIntegrationTest {

    MockMvc mockMvc;

    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;

    private static boolean dataInitialized = false;
    private static User admin;
    private static User user;
    private static Event event1;
    private static Event event2;
    public static List<Event> events;
    public static Date startDate;
    public static Date endDate;
    public static String start;
    public static String end;
    public static SimpleDateFormat inputDateFormat;
    public static LocalDateTime startDateTime;
    public static LocalDateTime endDateTime;
    public static List<Event> eventsInRange;

    @BeforeEach
    public void setup(WebApplicationContext wac) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        initData();
    }

    public void initData() {

        if(!dataInitialized) {

            admin = userService.register("admin", "admin", "admin", "admin", Role.ROLE_ADMIN, "adminadmin@gmail.com");
            user = userService.register("user", "user", "user", "user", Role.ROLE_USER, "useruser@gmail.com");


            event1= new Event("test", "test", null, null, user);
            event2= new Event("test", "test", null, null, user);

            events = new ArrayList<>();
            events.add(event1);
            events.add(event2);

            startDate = null;
            endDate = null;
            start = "2020-06-08";
            end = "2021-06-08";
            inputDateFormat = new SimpleDateFormat("yyy-MM-dd");
            try {
                startDate = inputDateFormat.parse(start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                endDate = inputDateFormat.parse(end);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            startDateTime =LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
            endDateTime =LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());

            eventsInRange = new ArrayList<>();
            eventsInRange = eventService.findAllByDateBetween(startDateTime, endDateTime);

            dataInitialized = true;
        }
    }

    @Test
    public void testAllEvents() throws Exception {
        MockHttpServletRequestBuilder eventRequest = MockMvcRequestBuilders.get("/allevents/");


        this.mockMvc.perform(eventRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
             //   .andExpect(MockMvcResultMatchers.content().contentType(events));
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testAddEvent() throws Exception {
        MockHttpServletRequestBuilder eventRequest = MockMvcRequestBuilders.post("/event/")
                .param("Event", "event");


        this.mockMvc.perform(eventRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
               //  .andExpect(MockMvcResultMatchers.content().contentType());
    }


    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testUpdateEvent() throws Exception {
        MockHttpServletRequestBuilder eventRequest = MockMvcRequestBuilders.patch("/event/")
                .param("Event", "event");


        this.mockMvc.perform(eventRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
               // .andExpect(MockMvcResultMatchers.content().contentType());
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testRemoveEvent() throws Exception {
        MockHttpServletRequestBuilder eventRequest = MockMvcRequestBuilders.delete("/event/")
                .param("Event", "event");


        this.mockMvc.perform(eventRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        // .andExpect(MockMvcResultMatchers.content().contentType());
    }

    @Test
    @WithMockUser(username = "user")
    public void testGetEventsInRange() throws Exception{
        MockHttpServletRequestBuilder eventRequest = MockMvcRequestBuilders.delete("/events/")
                .param("start", "2020-06-08")
                .param("end", "2021-06-08")
                .with(request -> {request.setRemoteUser("user");
                    return request;});

        this.mockMvc.perform(eventRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        // .andExpect(MockMvcResultMatchers.content().contentType());

    }
}
