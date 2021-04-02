package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Voucher;
import onlineshopapp.fashionstore.repository.VoucherRepository;
import onlineshopapp.fashionstore.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        return voucherRepository.findById(id);
    }

    @Override
    public Voucher create(String voucherName, int discount) {
        return this.voucherRepository.save(new Voucher(voucherName,discount));
    }
}
