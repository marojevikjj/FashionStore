//package onlineshopapp.fashionstore.junit;
//
//import onlineshopapp.fashionstore.model.Clothes;
//import onlineshopapp.fashionstore.model.Order;
//import onlineshopapp.fashionstore.model.OrderedClothes;
//import onlineshopapp.fashionstore.model.User;
//import onlineshopapp.fashionstore.model.enumerations.Role;
//import onlineshopapp.fashionstore.repository.OrderRepository;
//import onlineshopapp.fashionstore.service.impl.OrderServiceImpl;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(MockitoJUnitRunner.class)
//public class OrderServiceTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    private OrderServiceImpl service;
//
//    @Before
//    public void init(){
//
//        MockitoAnnotations.initMocks(this);
//
//        Order order = new Order(new User("name", "username", "password", Role.ROLE_USER, "email"), List.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, 0L)), "name", "surname", "address", "telephone", "city", 0.0, new User("name", "username", "password", Role.ROLE_USER, "email"));
//        Mockito.when(orderRepository.save(new Order(new User("name", "username", "password", Role.ROLE_USER, "email"), List.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, 0L)), "name", "surname", "address", "telephone", "city", 0.0, new User("name", "username", "password", Role.ROLE_USER, "email")))).thenReturn(order);
//
//        List<Order> orders = List.of(new Order(new User("name", "username", "password", Role.ROLE_USER, "email"), List.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, 0L)), "name", "surname", "address", "telephone", "city", 0.0, new User("name", "username", "password", Role.ROLE_USER, "email")));
//        Mockito.when(orderRepository.findAllByUser(new User("name", "username", "password", Role.ROLE_USER, "email"))).thenReturn(orders);
//        Mockito.when(orderRepository.findAllByPostman(new User("name", "username", "password", Role.ROLE_USER, "email"))).thenReturn(orders);
//
//        Optional<Order> order1 = Optional.of(new Order(new User("name", "username", "password", Role.ROLE_USER, "email"), List.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, 0L)), "name", "surname", "address", "telephone", "city", 0.0, new User("name", "username", "password", Role.ROLE_USER, "email")));
//        Mockito.when(orderRepository.findById(0L)).thenReturn(order1);
//
//        this.service = Mockito.spy(new OrderServiceImpl(this.orderRepository));
//    }
//
//    @Test
//    public void createOrderTest() {
//        User user = new User("name", "username", "password", Role.ROLE_USER, "email");
//        List<OrderedClothes> orderedClothes = List.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, 0L));
//        User postman = new User("name", "username", "password", Role.ROLE_USER, "email");
//        Order expectedResult = new Order(new User("name", "username", "password", Role.ROLE_USER, "email"), List.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, 0L)), "name", "surname", "address", "telephone", "city", 0.0, new User("name", "username", "password", Role.ROLE_USER, "email"));
//
//        assertEquals(expectedResult, service.createOrder("name", "surname", "address", "telephone", "city", 0.0, user, orderedClothes, postman));
//
//    }
//
//    @Test
//    public void findOrdersByUserTest(){
//
//        User user = new User("name", "username", "password", Role.ROLE_USER, "email");
//        List<Order> expectedResult = List.of(new Order(new User("name", "username", "password", Role.ROLE_USER, "email"), List.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, 0L)), "name", "surname", "address", "telephone", "city", 0.0, new User("name", "username", "password", Role.ROLE_USER, "email")));
//
//        assertEquals(expectedResult,service.findOrdersByUser(user));
//    }
//
//    @Test
//    public void findOrdersByPostmanTest(){
//
//        User postman = new User("name", "username", "password", Role.ROLE_USER, "email");
//        List<Order> expectedResult = List.of(new Order(new User("name", "username", "password", Role.ROLE_USER, "email"), List.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, 0L)), "name", "surname", "address", "telephone", "city", 0.0, new User("name", "username", "password", Role.ROLE_USER, "email")));
//
//        assertEquals(expectedResult, service.findOrdersByPostman(postman));
//    }
//
//    @Test
//    public void findByIdTest(){
//
//        Optional<Order> expectedResult = Optional.of(new Order(new User("name", "username", "password", Role.ROLE_USER, "email"), List.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, 0L)), "name", "surname", "address", "telephone", "city", 0.0, new User("name", "username", "password", Role.ROLE_USER, "email")));
//
//        assertEquals(expectedResult, service.findById(0L));
//    }
//
//
//}
