//package onlineshopapp.fashionstore.model;
//
//import lombok.Data;
//import onlineshopapp.fashionstore.model.enumerations.OrderStatus;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//
//@Data
//@Entity
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    private User user;
//
////    @ManyToOne
////    private User postman;
//
//    @ManyToMany
//    private List<Clothes> orderedClothes;
//
//    @Enumerated(EnumType.STRING)
//    private OrderStatus orderStatus;
//
//    public Order(){}
//
//    public Order(User user, List<Clothes> orderedClothes) {
//        this.user = user;
////        this.postman = postman;
//        this.orderedClothes = orderedClothes;
//        this.orderStatus = OrderStatus.ORDERED;
//    }
//}
