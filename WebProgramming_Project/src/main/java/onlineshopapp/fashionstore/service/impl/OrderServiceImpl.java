package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Order;
import onlineshopapp.fashionstore.model.OrderedClothes;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.OrderStatus;
import onlineshopapp.fashionstore.repository.OrderRepository;
import onlineshopapp.fashionstore.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(String name, String surname, String address, String telephone, String city, double discount, User user, List<OrderedClothes> orderedClothes, User postman) {
        Order order = new Order(user, orderedClothes, name, surname, address, telephone, city, discount, postman);
        return this.orderRepository.save(order);
    }

    @Override
    public List<Order> findOrdersByUser(User user) {
        return this.orderRepository.findAllByUser(user);
    }

    @Override
    public List<Order> findOrdersByPostman(User postman) {
        return this.orderRepository.findAllByPostman(postman);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public void changeOrderStatus(Long id, String status) {
        Order order = this.orderRepository.findById(id).get();
        System.out.println("STATUSSSSSSSSSSSSSSSSSSSSSS"+status);
        System.out.println("STATUSSSSSSSSSSSSSSSSSSSSSS"+OrderStatus.valueOf(status));
        order.setOrderStatus(OrderStatus.valueOf(status));
        this.orderRepository.save(order);
    }
}
