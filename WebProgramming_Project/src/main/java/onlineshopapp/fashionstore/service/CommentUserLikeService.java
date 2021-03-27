package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.CommentUserLike;
import onlineshopapp.fashionstore.model.User;

import java.util.Optional;

public interface CommentUserLikeService {
    Optional<CommentUserLike> findByUerAndComment(User user, ClothesComment clothesComment);
    void createOrUpdate(int like, int dislike, User user, ClothesComment clothesComment, Optional<CommentUserLike> commentUserLike);
    int getTotalLikesFromComment(ClothesComment clothesComment);
    int getTotalDislikesFromComment(ClothesComment clothesComment);
}
