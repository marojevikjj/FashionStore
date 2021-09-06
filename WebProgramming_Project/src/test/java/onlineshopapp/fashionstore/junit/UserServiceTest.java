package onlineshopapp.fashionstore.junit;

import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.Role;
import onlineshopapp.fashionstore.model.exceptions.*;
import onlineshopapp.fashionstore.repository.UserRepository;
import onlineshopapp.fashionstore.service.UserService;
import onlineshopapp.fashionstore.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

//unit testovi se nameneti da proverat biznis logika
// za da ne se koristat vistinski vrednosti za repostirory se koristi @Mock

@RunWith(MockitoJUnitRunner.class)


public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService service;

    @Before
    public void init() {
        //prvovo znaci deka site mock instanci ke se kreiraat
        MockitoAnnotations.initMocks(this);
        //
        User user = new User("name", "username", "password", Role.ROLE_USER, "email");
        //koga ke se povika userRepostitory save kaj sto argument e bilo koj objekt od klasa user, togas vrati go user objektot gore kreiran
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(this.userRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        //inicijalizacija na user servicot
        this.service = Mockito.spy(new UserServiceImpl(this.userRepository, this.passwordEncoder));
    }
    @Test
    public void testSuccessRegister() {
        User user = this.service.register("name", "username", "password", "password", Role.ROLE_USER, "test@gmail.com");
        //se verifikuva deka metodot e povikan
        Mockito.verify(this.service).register("name", "username", "password", "password", Role.ROLE_USER, "test@gmail.com");
        Assert.assertNotNull("User is null", user);
        Assert.assertEquals("name do not mach", "name", user.getName());
        Assert.assertEquals("role do not mach", Role.ROLE_USER, user.getRole());
        Assert.assertEquals("email do not mach", "email", user.getEmail());
        Assert.assertEquals("password do not mach", "password", user.getPassword());
        Assert.assertEquals("username do not mach", "username", user.getUsername());
    }

    @Test
    public void testNullUsername() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register("name", null, "password", "password", Role.ROLE_USER, "test@gmail.com"));
    }

    @Test
    public void testEmptyUsername() {
        String username = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register("name", username, "password", "password", Role.ROLE_USER, "test@gmail.com"));
    }

    @Test
    public void testEmptyPassword() {
        String username = "username";
        String password = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register("name", username, password, "password", Role.ROLE_USER, "test@gmail.com"));
    }

    @Test
    public void testNullPassword() {
        String username = "username";
        String password = null;
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register("name", username, password, "password", Role.ROLE_USER, "test@gmail.com"));
    }

    @Test
    public void testPasswordMismatch() {
        String username = "username";
        String password = "password";
        String confirmPassword = "otherPassword";
        Assert.assertThrows("PasswordsDoNotMatchException expected",
                PasswordsDoNotMatchException.class,
                () -> this.service.register("name", username, password, confirmPassword, Role.ROLE_USER, "test@gmail.com"));
    }

    @Test
    public void testDuplicateUsername() {
        User user = new User("name", "username", "password", Role.ROLE_USER, "test@gmail.com");
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        String username = "username";
        Assert.assertThrows("UsernameAlreadyExistsException expected",
                UsernameAlreadyExistsException.class,
                () -> this.service.register("name", username, "password", "password", Role.ROLE_USER, "test@gmail.com"));
    }

    @Test
    public void testDuplicateEmail() {
        User user = new User("name", "username1", "password", Role.ROLE_USER, "test@gmail.com");
        Mockito.when(this.userRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(Optional.of(user));
        String username = "username";
        Assert.assertThrows("EmailAlreadyExists expected",
                EmailAlreadyExistsException.class,
                () -> this.service.register("name", username, "password", "password", Role.ROLE_USER, "test@gmail.com"));
    }

    @Test
    public void testInvalidEmail() {
        Assert.assertThrows("InvalidEmail expected",
                InvalidEmailException.class,
                () -> this.service.register("name", "username", "password", "password", Role.ROLE_USER, "wrong"));
    }

    @Test
    public void testFindByEmailSuccess() {

        User user = new User("name", "username", "password", Role.ROLE_USER, "email");

        Assert.assertEquals(user, service.findByEmail("email"));
    }
    @Test
    public void testFindByEmailFail() {
        Assert.assertThrows("InvalidArgumentsException expected",
                InvalidArgumentsException.class,
                () -> this.service.findByEmail(null));

    }

}
