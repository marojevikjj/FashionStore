package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesCommentRepository extends JpaRepository<ClothesComment, Long> {

    List<ClothesComment> findAllByClothes_Id(Long id);
}
