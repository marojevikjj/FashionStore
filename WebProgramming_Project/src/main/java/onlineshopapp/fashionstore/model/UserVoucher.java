package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserVoucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Voucher voucher;

    private int quantity;
}
