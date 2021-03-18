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
    public UserVoucher searchByUserAndVoucher(User user, Optional<Voucher> voucher) {
        if(!voucher.isPresent())
            return null;
        else{
            List<UserVoucher> byUser = this.userVoucherRepository.findUserVouchersByUser(user);
            for(UserVoucher uv : byUser)
                if(uv.getVoucher().equals(voucher.get()))
                    return uv;
        }
        return null;
    }

    @Override
    public void delete(Voucher voucher, User user) {
        List<UserVoucher> vouchers = this.userVoucherRepository.findUserVouchersByUser(user);
        for(UserVoucher uv : vouchers){
            if(uv.getVoucher() == voucher){
                uv.setQuantity(uv.getQuantity() - 1);
                if(uv.getQuantity() == 0){
                    this.userVoucherRepository.delete(uv);
                    break;
                }
                this.userVoucherRepository.save(uv);
                break;
            }
        }
    }
}
