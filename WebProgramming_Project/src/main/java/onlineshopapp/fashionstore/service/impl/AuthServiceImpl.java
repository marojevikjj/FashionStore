package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.exceptions.InvalidArgumentsException;
import onlineshopapp.fashionstore.model.exceptions.InvalidUserCredentialsException;
import onlineshopapp.fashionstore.repository.UserRepository;
import onlineshopapp.fashionstore.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(InvalidUserCredentialsException::new);
    }

}
