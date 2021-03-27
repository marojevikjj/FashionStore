package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesGrade;
import onlineshopapp.fashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesGradeRepository extends JpaRepository<ClothesGrade, Long> {
    ClothesGrade findClothesGradeByUserAndClothes(User user, Clothes clothes);

    List<ClothesGrade> findAllByClothes(Clothes clothes);
}
