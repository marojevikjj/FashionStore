package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ClothesComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2500)
    private String comment;

    @ManyToOne
    private User user;

    public ClothesComment(){}

    public ClothesComment(String comment, User user) {
        this.comment = comment;
        this.user = user;
    }
}
