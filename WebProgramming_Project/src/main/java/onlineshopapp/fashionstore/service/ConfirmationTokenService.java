package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.ConfirmationToken;

public interface ConfirmationTokenService {
    void save(ConfirmationToken confirmationToken);
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
