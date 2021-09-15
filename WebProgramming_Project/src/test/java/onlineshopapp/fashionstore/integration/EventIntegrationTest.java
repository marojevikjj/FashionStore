package onlineshopapp.fashionstore.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import onlineshopapp.fashionstore.model.Event;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.Role;
import onlineshopapp.fashionstore.service.EventService;
import onlineshopapp.fashionstore.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
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
    @Autowired
    private ObjectMapper objectMapper;

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
    public void setup(WebApplicationContext wac) throws ParseException {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        initData();
    }

    public void initData() throws ParseException {

        if(!dataInitialized) {

            admin = userService.register("admin", "admin", "admin", "admin", Role.ROLE_ADMIN, "adminadmin@gmail.com");
            user = userService.register("user", "user", "user", "user", Role.ROLE_USER, "useruser@gmail.com");
            start = "2020-06-08";
            end = "2021-06-08";
            inputDateFormat = new SimpleDateFormat("yyy-MM-dd");
            event1 = new Event("test", "test", LocalDateTime.ofInstant(inputDateFormat.parse(start).toInstant(),
                    ZoneId.systemDefault()), LocalDateTime.ofInstant(inputDateFormat.parse(end).toInstant(),
                    ZoneId.systemDefault()), user);
            event1.setId((long) 1);
            eventService.save(event1);

            dataInitialized = true;
        }
    }

    @Test
    public void testAllEvents() throws Exception {
        MockHttpServletRequestBuilder eventRequest = MockMvcRequestBuilders.get("/allevents");

        List<Event> events = new ArrayList<>();
        events.add(event1);
        this.mockMvc.perform(eventRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.content().contentType((MediaType) events));
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value((long) 1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("test"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("test"));
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testAddEvent() throws Exception {
        MockHttpServletRequestBuilder eventRequest = MockMvcRequestBuilders.post("/event")
                .param("Event", String.valueOf(event1));


        this.mockMvc.perform(eventRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
//          .andExpect(MockMvcResultMatchers.content().contentType());
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
