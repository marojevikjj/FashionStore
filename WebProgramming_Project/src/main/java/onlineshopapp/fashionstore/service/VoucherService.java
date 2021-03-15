package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Voucher;

import java.util.Optional;

public interface VoucherService {
    Optional<Voucher> findById(Long id);
}
