package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.ClothesComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesCommentRepository extends JpaRepository<ClothesComment, Long> {
}
