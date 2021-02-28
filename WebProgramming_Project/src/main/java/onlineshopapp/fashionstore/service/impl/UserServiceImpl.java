package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.PasswordResetToken;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.Role;
import onlineshopapp.fashionstore.model.exceptions.InvalidUsernameOrPasswordException;
import onlineshopapp.fashionstore.model.exceptions.PasswordsDoNotMatchException;
import onlineshopapp.fashionstore.model.exceptions.UsernameAlreadyExistsException;
import onlineshopapp.fashionstore.repository.UserRepository;
import onlineshopapp.fashionstore.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String name, String username, String password,String repeatedPassword, Role role, String email) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatedPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(name,username,passwordEncoder.encode(password),role,email);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }
    @Override
    public User findByEmail(String email) {
        this.userRepository.findByEmailIgnoreCase(email);
        return this.userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameAlreadyExistsException(email));
    }

    @Override
    public Optional<User> findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

}