package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ClothesGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Clothes clothes;

    private double grade;

    public ClothesGrade(User user, Clothes clothes, double grade) {
        this.user = user;
        this.clothes = clothes;
        this.grade = grade;
    }

    public ClothesGrade() {}
}
