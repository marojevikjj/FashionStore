package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothesGrade that = (ClothesGrade) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
