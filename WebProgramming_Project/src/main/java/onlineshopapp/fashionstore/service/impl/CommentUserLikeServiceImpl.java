package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.CommentUserLike;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.repository.CommentUserLikeRepository;
import onlineshopapp.fashionstore.service.CommentUserLikeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentUserLikeServiceImpl implements CommentUserLikeService {

    private final CommentUserLikeRepository commentUserLikeRepository;

    public CommentUserLikeServiceImpl(CommentUserLikeRepository commentUserLikeRepository) {
        this.commentUserLikeRepository = commentUserLikeRepository;
    }

    @Override
    public Optional<CommentUserLike> findByUerAndComment(User user, ClothesComment clothesComment) {
        return this.commentUserLikeRepository.findCommentUserLikeByClothesCommentAndUser(clothesComment, user);
    }

    @Override
    public CommentUserLike createOrUpdate(int like, int dislike, User user, ClothesComment clothesComment, Optional<CommentUserLike> commentUserLike) {

        CommentUserLike c;
        if(commentUserLike.isPresent())
        {
            c = commentUserLike.get();
            c.setCommentLike(like);
            c.setCommentDislike(dislike);
        }
        else c = new CommentUserLike(like, dislike, user, clothesComment);

        this.commentUserLikeRepository.save(c);
        return c;
    }

    @Override
    public int getTotalLikesFromComment(ClothesComment clothesComment) {

        int count = 0;
        List<CommentUserLike> commentUserLikes =  this.commentUserLikeRepository.findCommentUserLikesByClothesComment(clothesComment);
        for(CommentUserLike c : commentUserLikes){
            if(c.getCommentLike() == 1)
                count += 1;
        }
        return count;
    }

    @Override
    public int getTotalDislikesFromComment(ClothesComment clothesComment) {

        int count = 0;
        List<CommentUserLike> commentUserLikes =  this.commentUserLikeRepository.findCommentUserLikesByClothesComment(clothesComment);
        for(CommentUserLike c : commentUserLikes){
            if(c.getCommentDislike() == 1)
                count += 1;
        }
        return count;
    }
}
