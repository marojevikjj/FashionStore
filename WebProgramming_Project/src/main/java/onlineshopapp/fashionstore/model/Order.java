package onlineshopapp.fashionstore.model;

import lombok.Data;
import onlineshopapp.fashionstore.model.enumerations.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private User postman;

    @OneToMany
    private List<OrderedClothes> orderedClothes;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String address;

    private String telephone;

    private String name;

    private String surname;

    private String city;

    private double price;

    private LocalDateTime date;

    public Order(){}

    public Order(User user, List<OrderedClothes> orderedClothes, String name, String surname, String address, String telephone, String city, double price, User postman) {
        this.user = user;
        this.orderStatus = OrderStatus.Ordered;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.price = price;
        this.postman = postman;
        this.date = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.price, price) == 0 && Objects.equals(id, order.id) && Objects.equals(user, order.user) && Objects.equals(postman, order.postman) && Objects.equals(orderedClothes, order.orderedClothes) && orderStatus == order.orderStatus && Objects.equals(address, order.address) && Objects.equals(telephone, order.telephone) && Objects.equals(name, order.name) && Objects.equals(surname, order.surname) && Objects.equals(city, order.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, postman, orderedClothes, orderStatus, address, telephone, name, surname, city, price);
    }
}
