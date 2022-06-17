//package onlineshopapp.fashionstore.junit;
//
//import onlineshopapp.fashionstore.model.User;
//import onlineshopapp.fashionstore.model.enumerations.Role;
//import onlineshopapp.fashionstore.model.exceptions.InvalidArgumentsException;
//import onlineshopapp.fashionstore.model.exceptions.InvalidUserCredentialsException;
//import onlineshopapp.fashionstore.model.exceptions.InvalidUsernameOrPasswordException;
//import onlineshopapp.fashionstore.repository.UserRepository;
//import onlineshopapp.fashionstore.service.AuthService;
//import onlineshopapp.fashionstore.service.impl.AuthServiceImpl;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AuthServiceTest {
//    @Mock
//    private UserRepository userRepository;
//
//    private AuthService service;
//
//    @Before
//    public void init() {
//        User user = new User("name", "username", "password", Role.ROLE_USER, "email");
//        Mockito.when(this.userRepository.findByUsernameAndPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(java.util.Optional.of(user));
//        this.service = Mockito.spy(new AuthServiceImpl(this.userRepository));
//    }
//
//    @Test
//    public void testNullUsername() {
//        Assert.assertThrows("InvalidArgumentsException expected",
//                InvalidArgumentsException.class,
//                () -> this.service.login(null, "password"));
//    }
//
//    @Test
//    public void testEmptyUsername() {
//        String username = "";
//        Assert.assertThrows("InvalidArgumentsException expected",
//                InvalidArgumentsException.class,
//                () -> this.service.login(username, "password"));
//    }
//
//    @Test
//    public void testEmptyPassword() {
//        String username = "username";
//        String password = "";
//        Assert.assertThrows("InvalidArgumentsException expected",
//                InvalidArgumentsException.class,
//                () -> this.service.login(username, password));
//    }
//
//    @Test
//    public void testNullPassword() {
//        String username = "username";
//        String password = null;
//        Assert.assertThrows("InvalidArgumentsException expected",
//                InvalidArgumentsException.class,
//                () -> this.service.login(username, password));
//    }
//
//    @Test
//    public void testSuccessfulLogIn() {
//        User user = this.service.login("username","password");
//        Assert.assertNotNull("User is null", user);
//        Assert.assertEquals("username",user.getUsername());
//        Assert.assertEquals("password",user.getPassword());
//    }
//
//
//}
