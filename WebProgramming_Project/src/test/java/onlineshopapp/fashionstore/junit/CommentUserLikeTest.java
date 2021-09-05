package onlineshopapp.fashionstore.junit;

import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.CommentUserLike;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.repository.CommentUserLikeRepository;
import onlineshopapp.fashionstore.service.CommentUserLikeService;
import onlineshopapp.fashionstore.service.impl.CommentUserLikeServiceImpl;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CommentUserLikeTest {

    @Mock
    private CommentUserLikeRepository commentUserLikeRepository;

    private CommentUserLikeService service;

    @Before
    public void init() {

        CommentUserLike like1 = new CommentUserLike(1, 0, new User(), new ClothesComment());
        CommentUserLike like2 = new CommentUserLike(1, 0, new User(), new ClothesComment());
        CommentUserLike dislike = new CommentUserLike(0, 1, new User(), new ClothesComment());

        List<CommentUserLike> commentUserLikeList = new ArrayList<>();
        commentUserLikeList.add(like1);
        commentUserLikeList.add(like2);
        commentUserLikeList.add(dislike);

        Mockito.when(this.commentUserLikeRepository.findCommentUserLikeByClothesCommentAndUser(Mockito.any(ClothesComment.class),
                Mockito.any(User.class))).thenReturn(java.util.Optional.of(like1));

        Mockito.when(this.commentUserLikeRepository.findCommentUserLikesByClothesComment(Mockito.any(ClothesComment.class)))
                .thenReturn(commentUserLikeList);

        this.service = Mockito.spy(new CommentUserLikeServiceImpl(this.commentUserLikeRepository));
    }

    @Test
    public void findByUserAndCommentTest() {

        CommentUserLike like = new CommentUserLike(1, 0, new User(), new ClothesComment());

        Assert.assertEquals(like, service.findByUerAndComment(new User(), new ClothesComment()).get());
    }

    @Test
    public void findByUserEmptyAndCommentTest() {

        Assert.assertEquals(Optional.empty(), service.findByUerAndComment(null, new ClothesComment()));
    }

    @Test
    public void findByUserAndCommentEmptyTest() {

        Assert.assertEquals(Optional.empty(), service.findByUerAndComment(new User(), null));
    }

    @Test
    public void createTest() {

        CommentUserLike like = new CommentUserLike(1, 0, new User(), new ClothesComment());

        Assert.assertEquals(like, service.createOrUpdate(1, 0, new User(), new ClothesComment(), Optional.empty()));
    }

    @Test
    public void updateTest() {

        CommentUserLike like1 = new CommentUserLike(1, 0, new User(), new ClothesComment());
        CommentUserLike like2 = new CommentUserLike(0, 1, new User(), new ClothesComment());

        Assert.assertEquals(like2, service.createOrUpdate(0, 1, new User(), new ClothesComment(), Optional.of(like1)));
    }

    @Test
    public void getTotalLikesFromCommentTest() {

        Assert.assertEquals(2, service.getTotalLikesFromComment(new ClothesComment()));
    }

    @Test
    public void getTotalLikesFromCommentEmptyTest() {

        Assert.assertEquals(0, service.getTotalLikesFromComment(null));
    }

    @Test
    public void getTotalDislikesFromCommentTest() {

        Assert.assertEquals(1, service.getTotalDislikesFromComment(new ClothesComment()));
    }

    @Test
    public void getTotalDislikesFromCommentEmptyTest() {

        Assert.assertEquals(0, service.getTotalDislikesFromComment(null));
    }

}
