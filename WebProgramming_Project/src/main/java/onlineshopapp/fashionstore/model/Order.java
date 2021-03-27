package onlineshopapp.fashionstore.model;

import lombok.Data;
import onlineshopapp.fashionstore.model.enumerations.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
//        this.postman = postman;
//        this.orderedClothes = orderedClothes;
        this.orderStatus = OrderStatus.ORDERED;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.price = price;
        this.postman = postman;
        this.date = LocalDateTime.now();
    }
}
