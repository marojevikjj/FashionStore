package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.ConfirmationToken;
import onlineshopapp.fashionstore.model.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, String> {
    PasswordResetToken findByToken(String Token);
}
