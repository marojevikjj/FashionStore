package onlineshopapp.fashionstore.junit;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.exceptions.ClothesCommentNotFound;
import onlineshopapp.fashionstore.repository.ClothesCommentRepository;
import onlineshopapp.fashionstore.service.ClothesCommentService;
import onlineshopapp.fashionstore.service.impl.ClothesCommentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ClothesCommentTest {

    @Mock
    private ClothesCommentRepository clothesCommentRepository;

    private ClothesCommentService service;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        Clothes c = new Clothes();
        c.setId((long) 10);

        ClothesComment clothesComment = new ClothesComment(new User(), c, "comment1", 0, 0);
        clothesComment.setId((long) 1);

        List<ClothesComment> clothesCommentList = new ArrayList<>();
        clothesCommentList.add(clothesComment);

        Mockito.when(this.clothesCommentRepository.save(Mockito.any(ClothesComment.class)))
                .thenReturn(clothesComment);

        Mockito.when(this.clothesCommentRepository.findById((long) 1))
                .thenReturn(java.util.Optional.of(clothesComment));

        Mockito.when(this.clothesCommentRepository.findAllByClothes_Id((long) 10))
                .thenReturn(clothesCommentList);

        this.service = Mockito.spy(new ClothesCommentServiceImpl(this.clothesCommentRepository));
    }

    @Test
    public void addNewCommentTest() {

        ClothesComment cc = new ClothesComment();
        cc.setId((long) 1);

        Assert.assertEquals(cc, service.addNewComment(new User(), new Clothes(), "comment1"));
    }

    @Test
    public void foundCommentsByProductTest() {

        Assert.assertEquals(1, service.findCommentsByProduct((long) 10).size());
    }

    @Test
    public void notFoundCommentsByProductTest() {

        List<ClothesComment> empty = new ArrayList<>();

        Assert.assertEquals(empty, service.findCommentsByProduct(null));
    }

    @Test
    public void updateLikesTest() throws ClothesCommentNotFound {

        Assert.assertEquals(0, service.updateLikes(2, 1, new ClothesComment()).getTotalLikes());
        Assert.assertEquals(0, service.updateLikes(2, 1, new ClothesComment()).getTotalDislikes());

    }

    @Test
    public void updateLikesEmptyObjectTest() {

        Assert.assertThrows("not found", ClothesCommentNotFound.class,
                () -> service.updateLikes(1, 1, null));
    }

    @Test
    public void foundByIdTest() throws ClothesCommentNotFound {

        ClothesComment cc = new ClothesComment();
        cc.setId((long) 1);

        Assert.assertEquals(cc, service.findById((long) 1));
    }

    @Test
    public void notFoundByIdTest() {

        Assert.assertThrows("not found", ClothesCommentNotFound.class,
                () -> service.findById(null));
    }


}
