package onlineshopapp.fashionstore.model;

import lombok.Data;
import onlineshopapp.fashionstore.model.enumerations.ShoppingCartStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Clothes> clothes;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;

    private LocalDateTime date;

    public ShoppingCart(){}

    public ShoppingCart(User user) {
        this.date = LocalDateTime.now();
        this.user = user;
        this.clothes = new ArrayList<>();
        this.status = ShoppingCartStatus.CREATED;
    }
}
