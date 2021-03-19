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

    private String image;

    private String image1;

    private String image2;

    private String image3;

    private double price;

    private double grade;

    private int quantitySizeS;

    private int quantitySizeM;

    private int quantitySizeL;

    private int quantitySizeXL;

    public Clothes(String name, String description, String image, String image1, String image2, String image3, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.price = price;
        this.grade = grade;
        this.quantitySizeS = quantitySizeS;
        this.quantitySizeM = quantitySizeM;
        this.quantitySizeL = quantitySizeL;
        this.quantitySizeXL = quantitySizeXL;
    }

    public Clothes(){}
}
