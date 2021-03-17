package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.PostmanOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostmanOrderRepository extends JpaRepository<PostmanOrder, Long> {

    List<PostmanOrder> findAllByCity(String city);
}
