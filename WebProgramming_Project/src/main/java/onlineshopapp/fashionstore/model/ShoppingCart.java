package onlineshopapp.fashionstore.model;

import lombok.Data;
import onlineshopapp.fashionstore.model.enumerations.ShoppingCartStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany
    private List<OrderedClothes> orderedClothes;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;

    private LocalDateTime date;

    public ShoppingCart(){}

    public ShoppingCart(User user) {
        this.date = LocalDateTime.now();
        this.user = user;
        this.orderedClothes = new ArrayList<>();
        this.status = ShoppingCartStatus.CREATED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }
}
