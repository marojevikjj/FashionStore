//package onlineshopapp.fashionstore.junit;
//
//import onlineshopapp.fashionstore.model.Postman;
//import onlineshopapp.fashionstore.model.User;
//import onlineshopapp.fashionstore.model.enumerations.Role;
//import onlineshopapp.fashionstore.repository.PostmanOrderRepository;
//import onlineshopapp.fashionstore.service.impl.PostmanOrderServiceImpl;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(MockitoJUnitRunner.class)
//public class PostmanOrderServiceTest {
//
//    @Mock
//    private PostmanOrderRepository postmanOrderRepository;
//
//    private PostmanOrderServiceImpl service;
//
//    @Before
//    public void init(){
//
//        MockitoAnnotations.initMocks(this);
//
//        List<Postman> postman = List.of(new Postman(new User("name", "username", "password", Role.ROLE_USER, "email"), "city"));
//        Mockito.when(postmanOrderRepository.findAllByCity("city")).thenReturn(postman);
//
//        Postman postman1 = new Postman(new User("name", "username", "password", Role.ROLE_USER, "email"), "city");
//        Mockito.when(postmanOrderRepository.save(new Postman(new User("name", "username", "password", Role.ROLE_USER, "email"), "city"))).thenReturn(postman1);
//
//        this.service = Mockito.spy(new PostmanOrderServiceImpl(this.postmanOrderRepository));
//
//    }
//
//    @Test
//    public void findFromRegionTest(){
//
//        List<Postman> expectedResult = List.of(new Postman(new User("name", "username", "password", Role.ROLE_USER, "email"), "city"));
//
//        assertEquals(expectedResult, service.findFromRegion("city"));
//    }
//
//    @Test
//    public void createTest(){
//
//        User user = new User("name", "username", "password", Role.ROLE_USER, "email");
//        Postman expectedResult = new Postman(new User("name", "username", "password", Role.ROLE_USER, "email"), "city");
//
//        assertEquals(expectedResult, service.create(user, "city"));
//    }
//}
