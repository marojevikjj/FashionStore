package onlineshopapp.fashionstore.model;


import lombok.Data;
import onlineshopapp.fashionstore.model.enumerations.Role;

import javax.persistence.*;

@Data
@Entity
//@Table(name = "online_shop_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String email;

    public User(){}

    public User(String name, String username, String password, Role role, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }
}
