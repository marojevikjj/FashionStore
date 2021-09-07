package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 5000)
    private String description;

    private String image;

    private String image1;

    private String image2;

    private String image3;

    private double price;

    private int grade;

    private int quantitySizeS;

    private int quantitySizeM;

    private int quantitySizeL;

    private int quantitySizeXL;

    private LocalDateTime dateCreated;

    public Clothes(String name, String description, String image, String image1, String image2, String image3, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.price = price;
        this.grade = (int)grade;
        this.quantitySizeS = quantitySizeS;
        this.quantitySizeM = quantitySizeM;
        this.quantitySizeL = quantitySizeL;
        this.quantitySizeXL = quantitySizeXL;
        this.dateCreated = LocalDateTime.now();
    }

    public Clothes(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Objects.equals(id, clothes.id) && Objects.equals(name, clothes.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
