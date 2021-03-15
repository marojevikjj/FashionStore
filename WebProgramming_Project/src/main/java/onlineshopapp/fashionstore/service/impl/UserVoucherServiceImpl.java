package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.UserVoucher;
import onlineshopapp.fashionstore.model.Voucher;
import onlineshopapp.fashionstore.repository.UserVoucherRepository;
import onlineshopapp.fashionstore.service.UserVoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserVoucherServiceImpl implements UserVoucherService {

    private final UserVoucherRepository userVoucherRepository;

    public UserVoucherServiceImpl(UserVoucherRepository userVoucherRepository) {
        this.userVoucherRepository = userVoucherRepository;
    }

    @Override
    public UserVoucher searchByUserAndVoucher(User user, Voucher voucher) {
        List<UserVoucher> byUser = this.userVoucherRepository.findUserVouchersByUser(user);
        for(UserVoucher uv : byUser)
            if(uv.getVoucher().equals(voucher))
                return uv;
        return null;
    }
}
