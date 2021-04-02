package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.User;

import java.util.List;

public interface ClothesCommentService {

    ClothesComment addNewComment(User user, Clothes clothes, String comment);

    List<ClothesComment> findCommentsByProduct(Long id);

    ClothesComment findById(Long id);

    void updateLikes(int l, int d, ClothesComment clothesComment);
}
