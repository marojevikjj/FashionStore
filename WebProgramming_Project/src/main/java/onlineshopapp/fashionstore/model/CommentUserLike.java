package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CommentUserLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int commentLike;

    private int commentDislike;

    @ManyToOne
    private User user;

    @ManyToOne
    private ClothesComment clothesComment;

    public CommentUserLike(){}

    public CommentUserLike(int commentLike, int commentDislike, User user, ClothesComment clothesComment) {
        this.commentLike = commentLike;
        this.commentDislike = commentDislike;
        this.user = user;
        this.clothesComment = clothesComment;
    }
}
