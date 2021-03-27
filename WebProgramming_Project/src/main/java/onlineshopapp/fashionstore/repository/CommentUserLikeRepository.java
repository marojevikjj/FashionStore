package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.CommentUserLike;
import onlineshopapp.fashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentUserLikeRepository extends JpaRepository<CommentUserLike, Long> {

    Optional<CommentUserLike> findCommentUserLikeByClothesCommentAndUser(ClothesComment clothesComment, User user);
    List<CommentUserLike> findCommentUserLikesByClothesComment(ClothesComment clothesComment);
}
