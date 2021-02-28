package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.ConfirmationToken;
import onlineshopapp.fashionstore.repository.ConfirmationTokenRepository;
import onlineshopapp.fashionstore.service.ConfirmationTokenService;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void save(ConfirmationToken confirmationToken) {
        this.confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        return this.confirmationTokenRepository.findByConfirmationToken(confirmationToken);
    }
}
