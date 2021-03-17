package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class PostmanOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    private String city;

    private Integer count;

    public PostmanOrder(User user, String city) {
        this.user = user;
        this.city = city;
        this.count = 0;
    }

    public PostmanOrder() {}
}
