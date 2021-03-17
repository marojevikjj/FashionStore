package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.UserVoucher;
import onlineshopapp.fashionstore.model.Voucher;

import java.util.List;
import java.util.Optional;

public interface UserVoucherService {
    UserVoucher searchByUserAndVoucher(User user, Optional<Voucher> voucher);
    void delete(Voucher voucher, User user);
}
