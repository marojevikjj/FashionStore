package onlineshopapp.fashionstore.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothesComment that = (ClothesComment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
