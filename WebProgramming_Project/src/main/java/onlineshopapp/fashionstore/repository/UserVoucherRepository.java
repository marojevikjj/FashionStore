package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.UserVoucher;
import onlineshopapp.fashionstore.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserVoucherRepository extends JpaRepository<UserVoucher, Long> {
    List<UserVoucher> findUserVouchersByUser(User user);
}
