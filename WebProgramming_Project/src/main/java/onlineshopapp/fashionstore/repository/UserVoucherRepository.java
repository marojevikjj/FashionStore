package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.UserVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVoucherRepository extends JpaRepository<UserVoucher, Long> {
}
