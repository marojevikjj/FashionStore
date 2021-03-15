package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.OrderedClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedClothesRepository extends JpaRepository<OrderedClothes, Long> {
}
