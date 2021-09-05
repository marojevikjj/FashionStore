package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.SQLTransactionRollbackException;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(voucherName, voucher.voucherName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherName);
    }
}
