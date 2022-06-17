//package onlineshopapp.fashionstore.junit;
//import onlineshopapp.fashionstore.model.Event;
//import onlineshopapp.fashionstore.model.User;
//import onlineshopapp.fashionstore.model.enumerations.Role;
//import onlineshopapp.fashionstore.model.exceptions.UserNotFoundException;
//import onlineshopapp.fashionstore.repository.EventRepository;
//import onlineshopapp.fashionstore.repository.UserRepository;
//import onlineshopapp.fashionstore.service.EventService;
//import onlineshopapp.fashionstore.service.impl.EventServiceImpl;
//import org.checkerframework.checker.units.qual.A;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EventServiceTest {
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private EventRepository eventRepository;
//
//    private EventService service;
//
//    @Before
//    public void init() {
//        this.service = Mockito.spy(new EventServiceImpl(this.eventRepository,this.userRepository));
//        User user1 = new User("name", "username1", "password", Role.ROLE_USER, "email");
//        User user2 = new User("name", "username1", "password", Role.ROLE_USER, "email");
//        Event event1 = new Event( "title", "description", LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(4), user1);
//        Event event2 = new Event( "title2", "description2", LocalDateTime.now().minusDays(4), LocalDateTime.now().plusDays(5), user2);
//        List<Event> eventList = new ArrayList<>();
//        eventList.add(event1);
//        eventList.add(event2);
//
//        List<Event> eventListForUser = new ArrayList<>();
//        eventListForUser.add(event1);
//
//        Mockito.when(this.eventRepository.findByStartGreaterThanEqualAndFinishLessThanEqual(Mockito.any(LocalDateTime.class),Mockito.any(LocalDateTime.class))).thenReturn(eventList);
//        Mockito.when(this.eventRepository.findAll()).thenReturn(eventList);
//        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(java.util.Optional.of(user1));
//        Mockito.when(this.eventRepository.findAllByUser(Mockito.any(User.class))).thenReturn(eventListForUser);
//
//    }
//
//
//    @Test
//    public void testFindAllByUserFail() {
//        Assert.assertThrows("UserNotFoundException",
//                UserNotFoundException.class,
//        () -> this.service.findAllByUser(null));
//
//    }
//
//
//}
