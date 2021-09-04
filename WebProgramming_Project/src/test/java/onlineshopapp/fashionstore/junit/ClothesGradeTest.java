package onlineshopapp.fashionstore.junit;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesGrade;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.repository.ClothesGradeRepository;
import onlineshopapp.fashionstore.service.ClothesGradeService;
import onlineshopapp.fashionstore.service.impl.ClothesGradeServiceImpl;
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
public class ClothesGradeTest {

    @Mock
    private ClothesGradeRepository clothesGradeRepository;

    private ClothesGradeService service;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        User u = new User();
        u.setId((long) 10);

        Clothes c = new Clothes();
        c.setId((long) 10);

        ClothesGrade clothesGrade = new ClothesGrade(u, c, 0.0);
        clothesGrade.setId((long) 1);

        List<ClothesGrade> clothesGradeList = new ArrayList<>();
        clothesGradeList.add(clothesGrade);

        Mockito.when(this.clothesGradeRepository.save(Mockito.any(ClothesGrade.class)))
                .thenReturn(clothesGrade);

        Mockito.when(this.clothesGradeRepository.findAllByClothes(Mockito.any(Clothes.class)))
                .thenReturn(clothesGradeList);

        Mockito.when(this.clothesGradeRepository.findClothesGradeByUserAndClothes(Mockito.any(User.class), Mockito.any(Clothes.class)))
                .thenReturn(clothesGrade);

        this.service = Mockito.spy(new ClothesGradeServiceImpl(this.clothesGradeRepository));
    }

    @Test
    public void foundClothesGradeByUserAndClothesTest() {

        ClothesGrade cg = new ClothesGrade();
        cg.setId((long) 1);

        Assert.assertEquals(cg, service.findByUserAndClothes(new User(), new Clothes()));
    }

    @Test
    public void notFoundClothesGradeByUserAndClothesTest() {

        Assert.assertNull(service.findByUserAndClothes(null, null));
    }

    @Test
    public void updateGradeTest() {

        ClothesGrade cg = new ClothesGrade();
        cg.setId((long) 1);

        Assert.assertEquals(cg, service.updateGrade(new ClothesGrade(), 5));
    }

    @Test
    public void addGradeTest() {

        ClothesGrade cg = new ClothesGrade();
        cg.setId((long) 1);

        Assert.assertEquals(cg, service.addGrade(new User(), new Clothes(), 5));
    }

    @Test
    public void getGradesByClothesTest() {

        Assert.assertEquals(1, service.getGradesByClothes(new Clothes()).size());
    }

    @Test
    public void notFoundGradesByClothesTest() {

        List<ClothesGrade> clothesGrades = new ArrayList<>();

        Assert.assertEquals(clothesGrades, service.getGradesByClothes(null));
    }

}
