package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 5000)
    private String description;

    private double price;

    private double grade;

    private int quantitySizeS;

    private int quantitySizeM;

    private int quantitySizeL;

    private int quantitySizeXL;

    public Clothes(String name, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL) {
        this.grade = grade;
        this.name = name;
        this.price = price;
        this.quantitySizeS = quantitySizeS;
        this.quantitySizeM = quantitySizeM;
        this.quantitySizeL = quantitySizeL;
        this.quantitySizeXL = quantitySizeXL;
    }

    public Clothes(){}
}
