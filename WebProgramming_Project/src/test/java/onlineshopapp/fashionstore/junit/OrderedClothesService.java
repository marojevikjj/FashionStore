package onlineshopapp.fashionstore.junit;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.OrderedClothes;
import onlineshopapp.fashionstore.repository.OrderedClothesRepository;
import onlineshopapp.fashionstore.service.impl.OrderedClothesServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderedClothesService {

    @Mock
    private OrderedClothesRepository orderedClothesRepository;

    private OrderedClothesServiceImpl service;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);

        OrderedClothes orderedClothes1 = new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, (long)6);
        Optional<OrderedClothes> orderedClothes = Optional.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, (long)6));


        Mockito.when(orderedClothesRepository.save(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, (long)6))).thenReturn(orderedClothes1);
        Mockito.when(orderedClothesRepository.findById((long)6)).thenReturn(orderedClothes);

        this.service = Mockito.spy(new OrderedClothesServiceImpl(this.orderedClothesRepository));
    }

    @Test
    public void addNewOrderedClothesTest(){

        OrderedClothes orderedClothes = new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, (long)6);
        OrderedClothes expectedResult = new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, (long)6);

        assertEquals(expectedResult, service.addNewOrderedClothes(orderedClothes));
    }

    @Test
    public void findByIdTest(){

        Optional<OrderedClothes> expectedResult = Optional.of(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, (long)6));

        assertEquals(expectedResult, service.findById((long)6));
    }

    @Test
    public void deleteTest(){

        OrderedClothes orderedClothes = new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, (long)6);
        service.delete(orderedClothes);

        verify(orderedClothesRepository).delete(new OrderedClothes(new Clothes("name", "description", "image", "image1", "image2", "image3", 0.0, 0.0, 0, 0, 0, 0), 0, "size", 0.0, (long)6));
    }
}
