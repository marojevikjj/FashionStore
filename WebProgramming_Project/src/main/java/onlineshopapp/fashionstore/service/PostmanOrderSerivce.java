package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Postman;
import onlineshopapp.fashionstore.model.User;

import java.util.List;

public interface PostmanOrderSerivce {
    List<Postman> findFromRegion(String city);
    Postman create(User user, String city);
}
