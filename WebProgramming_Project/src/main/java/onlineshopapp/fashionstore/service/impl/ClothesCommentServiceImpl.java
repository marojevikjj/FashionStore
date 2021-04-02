package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.User;
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
            comments.sort(Comparator.comparing(o -> o.getDate()));
        }
        return comments;
    }

    @Override
    public ClothesComment findById(Long id) {
        return this.clothesCommentRepository.findById(id).get();
    }

    @Override
    public void updateLikes(int l, int d, ClothesComment clothesComment) {
        clothesComment.setTotalLikes(l);
        clothesComment.setTotalDislikes(d);
        this.clothesCommentRepository.save(clothesComment);
    }
}
