package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Order;
import onlineshopapp.fashionstore.model.OrderedClothes;
import onlineshopapp.fashionstore.model.User;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(String name, String surname, String address, String telephone, String city, double discount, User user, List<OrderedClothes> orderedClothes, User postman);
    List<Order> findOrdersByUser(User user);
    List<Order> findOrdersByPostman(User postman);
    Optional<Order> findById(Long id);
    void changeOrderStatus(Long id, String status);
}
