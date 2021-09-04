package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.exceptions.ClothesCommentNotFound;

import java.util.List;

public interface ClothesCommentService {

    ClothesComment addNewComment(User user, Clothes clothes, String comment);

    List<ClothesComment> findCommentsByProduct(Long id);

    ClothesComment findById(Long id) throws ClothesCommentNotFound;

    ClothesComment updateLikes(int l, int d, ClothesComment clothesComment) throws ClothesCommentNotFound;
}
