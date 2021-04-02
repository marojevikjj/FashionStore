package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    List<Clothes> findAllByNameLike(String name);


}
