package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    User register(String name, String username, String password,String repeatedPassword, Role role, String email);
    User findByEmail(String email);
    Optional<User> findUserByResetToken(String resetToken);
    User save(User user);
}
