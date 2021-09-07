package onlineshopapp.fashionstore.junit;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesGrade;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.Role;
import onlineshopapp.fashionstore.repository.ClothesRepository;
import onlineshopapp.fashionstore.repository.ProductRepository;
import onlineshopapp.fashionstore.service.impl.ClothesServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClothesServiceTest {

    @Mock
    private ClothesRepository clothesRepository;

    @Mock
    private ProductRepository productRepository;

    private ClothesServiceImpl service;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        Clothes c = new Clothes();
        c.setId((long) 6);
        c.setName("name");
        c.setGrade(4);

        Clothes c1 = new Clothes();
        c1.setId((long) 8);
        c1.setName("ime");
        c1.setGrade(2);

        List<Clothes> lista = List.of(c, c1);
        List<Clothes> clothes = List.of(c);
        when(clothesRepository.findAll()).thenReturn(clothes);
        when(clothesRepository.findAllByNameLike("%"+"name"+"%")).thenReturn(clothes);

        Page<Clothes> clothes1 = new PageImpl<>(List.of(c));
        when(productRepository.findAll(any(Pageable.class))).thenReturn(clothes1);

        Optional<Clothes> clothes2 = Optional.of(c);
        when(clothesRepository.findById((long)6)).thenReturn(clothes2);

        Clothes clothes3 = new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0);
        when(clothesRepository.save(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0))).thenReturn(clothes3);

        Optional<Clothes> cl = Optional.of(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0));
        when(clothesRepository.findById((long)10)).thenReturn(cl);

        when(clothesRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"))).thenReturn(lista);
        when(clothesRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(lista);
        when(clothesRepository.findAll(Sort.by(Sort.Direction.DESC, "grade"))).thenReturn(lista);

        this.service = Mockito.spy(new ClothesServiceImpl(this.clothesRepository, this.productRepository));
    }

    @Test
    public void listAllTest(){

        Clothes c = new Clothes();
        c.setId((long) 6);
        c.setName("name");

        List<Clothes> expectedResult = List.of(c);
        assertEquals(expectedResult, service.listAll());
    }

    @Test
    public void findAllTest(){

        Clothes c = new Clothes();
        c.setId((long) 6);
        c.setName("name");

        Page<Clothes> expectedResult = new PageImpl<>(List.of(c));
        assertEquals(expectedResult, service.findAll(1, "sortField", "sortDir"));
    }

    @Test
    public void findByIdTest(){

        Clothes s = new Clothes();
        s.setId((long) 6);
        s.setName("name");

        assertEquals(s, service.findById((long)6));
    }

    @Test
    public void createTest(){

        Clothes expectedResult = new Clothes("name", "description", "image", "image1",
                "image2", "image3", 0.0, 0.0, 0, 0, 0, 0);

        assertEquals(expectedResult, service.create("name", "description", "image", "image1",
                "image2", "image3", 0.0, 0.0, 0, 0, 0, 0));
    }

    @Test
    public void updateTest(){

        Clothes expectedResult = new Clothes("name", "description", "image", "image1",
                "image2", "image3", 0.0, 0.0, 0, 0, 0, 0);

        assertEquals(expectedResult, service.update((long)10, "name", "description", "image",
                "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0));
    }

    @Test
    public void deleteTest() {

        Clothes s = new Clothes();
        s.setId((long) 6);
        s.setName("name");

        assertEquals(s, service.delete((long) 6));
    }

    @Test
    public void findOptionalByIdTest(){

        Clothes s = new Clothes();
        s.setId((long) 6);
        s.setName("name");
        Optional<Clothes> clothes2 = Optional.of(s);

        assertEquals(clothes2, service.findOptionalById((long)6));
    }

    @Test
    public void listProductsByNameTest(){

        Clothes s = new Clothes();
        s.setId((long) 6);
        s.setName("name");
        List<Clothes> expectedResult = List.of(s);

        assertEquals(expectedResult, service.listProductsByName("name"));
    }

    @Test
    public void sortDescendingByDate(){

        Clothes s = new Clothes();
        s.setId((long) 6);
        s.setName("name");
        s.setGrade(4);


        Clothes c1 = new Clothes();
        c1.setId((long) 8);
        c1.setName("ime");
        c1.setGrade(2);

        List<Clothes> expectedResult = List.of(s, c1);

        assertEquals(expectedResult, service.sortDescendingByDate());
    }

    @Test
    public void sortAscendingAlphabetic(){

        Clothes s = new Clothes();
        s.setId((long) 6);
        s.setName("name");
        s.setGrade(4);


        Clothes c1 = new Clothes();
        c1.setId((long) 8);
        c1.setName("ime");
        c1.setGrade(2);

        List<Clothes> expectedResult = List.of(s, c1);

        assertEquals(expectedResult, service.sortAscendingAlphabetic());
    }

    @Test
    public void sortDescendingByGrade(){

        Clothes s = new Clothes();
        s.setId((long) 6);
        s.setName("name");
        s.setGrade(4);


        Clothes c1 = new Clothes();
        c1.setId((long) 8);
        c1.setName("ime");
        c1.setGrade(2);

        List<Clothes> expectedResult = List.of(s, c1);

        assertEquals(expectedResult, service.sortDescendingByGrade());
    }

    @Test
    public void updateFinalGrade(){

        Clothes clothes = new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0);
        List<ClothesGrade> clothesGrades = List.of(new ClothesGrade(new User("name", "username", "password", Role.ROLE_USER, "email"), new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0.0));

        service.updateFinalGrade(clothes, clothesGrades);

        verify(clothesRepository).save(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0));
    }

}
