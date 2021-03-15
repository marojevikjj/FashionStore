package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OrderedClothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Clothes clothes;

    private Integer quantity;

    private String size;

    private Double price;

    private Long userId;

    public OrderedClothes(Clothes clothes, Integer quantity, String size, Double price, Long userId) {
        this.clothes = clothes;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
        this.userId = userId;
    }

    public OrderedClothes() {}
}
