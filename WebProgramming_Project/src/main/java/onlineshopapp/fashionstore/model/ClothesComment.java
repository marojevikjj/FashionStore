package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class ClothesComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2500)
    private String comment;

    @ManyToOne
    private User user;

    @ManyToOne
    private Clothes clothes;

    private int totalLikes;

    private int totalDislikes;

//    @OneToMany
//    private List<CommentLikes> likes;

    private LocalDateTime date;

    public ClothesComment(){}

    public ClothesComment(User user, Clothes clothes, String comment, int totalLikes, int totalDislikes) {
        this.comment = comment;
        this.user = user;
        this.date = LocalDateTime.now();
        this.clothes = clothes;
        this.totalLikes = totalLikes;
        this.totalDislikes = totalDislikes;
    }
}
