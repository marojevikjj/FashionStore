package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.ShoppingCart;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndStatus(User user,
                                               ShoppingCartStatus status);
}
