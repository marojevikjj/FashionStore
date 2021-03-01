package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.SQLTransactionRollbackException;

@Data
@Entity
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    private String voucherName;

    private int discount;

    public Voucher(){}

    public Voucher(String voucherName, int discount) {
        this.voucherName = voucherName;
        this.discount = discount;
    }
}
