package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.PostmanOrder;
import onlineshopapp.fashionstore.model.User;

import java.util.List;

public interface PostmanOrderSerivce {
    List<PostmanOrder> findFromRegion(String city);
}
