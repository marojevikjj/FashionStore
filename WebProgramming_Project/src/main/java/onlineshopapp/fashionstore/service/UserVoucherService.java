package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.UserVoucher;
import onlineshopapp.fashionstore.model.Voucher;

import java.util.Optional;

public interface UserVoucherService {
    UserVoucher searchByUserAndVoucher(User user, Voucher voucher);
}
