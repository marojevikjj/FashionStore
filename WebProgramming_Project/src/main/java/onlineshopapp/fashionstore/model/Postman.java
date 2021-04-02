package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Postman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String city;

    private Integer count;

    public Postman(User user, String city) {
        this.user = user;
        this.city = city;
        this.count = 0;
    }

    public Postman() {}
}
