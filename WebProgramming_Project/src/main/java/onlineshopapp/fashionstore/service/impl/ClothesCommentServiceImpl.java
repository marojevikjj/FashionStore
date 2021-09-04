package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.exceptions.ClothesCommentNotFound;
import onlineshopapp.fashionstore.repository.ClothesCommentRepository;
import onlineshopapp.fashionstore.service.ClothesCommentService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ClothesCommentServiceImpl implements ClothesCommentService {

    private final ClothesCommentRepository clothesCommentRepository;

    public ClothesCommentServiceImpl(ClothesCommentRepository clothesCommentRepository) {
        this.clothesCommentRepository = clothesCommentRepository;
    }

    @Override
    public ClothesComment addNewComment(User user, Clothes clothes, String comment) {
        return this.clothesCommentRepository.save(new ClothesComment(user, clothes, comment, 0, 0));
    }

    @Override
    public List<ClothesComment> findCommentsByProduct(Long id) {
        List<ClothesComment> comments = this.clothesCommentRepository.findAllByClothes_Id(id);
        if(comments != null){
            comments.sort(Comparator.comparing(ClothesComment::getDate));
        }
        return comments;
    }

    @Override
    public ClothesComment findById(Long id) throws ClothesCommentNotFound {
        return this.clothesCommentRepository.findById(id).orElseThrow(() -> new ClothesCommentNotFound("not found"));
    }

    @Override
    public ClothesComment updateLikes(int l, int d, ClothesComment clothesComment) throws ClothesCommentNotFound {
        if(clothesComment == null)
            throw new ClothesCommentNotFound("not found");
        clothesComment.setTotalLikes(l);
        clothesComment.setTotalDislikes(d);
        return this.clothesCommentRepository.save(clothesComment);
    }
}
