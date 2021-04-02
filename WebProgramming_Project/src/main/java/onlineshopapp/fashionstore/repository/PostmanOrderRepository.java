package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.Postman;
import onlineshopapp.fashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostmanOrderRepository extends JpaRepository<Postman, Long> {

    List<Postman> findAllByCity(String city);
//    Postman create(User user, String city);
}
