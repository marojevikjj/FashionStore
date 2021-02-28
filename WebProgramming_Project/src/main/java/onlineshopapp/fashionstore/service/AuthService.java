package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.User;

public interface AuthService {

    User login(String username, String password);
}
