package onlineshopapp.fashionstore.junit;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.OrderedClothes;
import onlineshopapp.fashionstore.model.ShoppingCart;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.ShoppingCartStatus;
import onlineshopapp.fashionstore.model.exceptions.ProductNotFoundException;
import onlineshopapp.fashionstore.model.exceptions.ShoppingCartNotFoundException;
import onlineshopapp.fashionstore.model.exceptions.UserNotFoundException;
import onlineshopapp.fashionstore.repository.ClothesRepository;
import onlineshopapp.fashionstore.repository.OrderedClothesRepository;
import onlineshopapp.fashionstore.repository.ShoppingCartRepository;
import onlineshopapp.fashionstore.repository.UserRepository;
import onlineshopapp.fashionstore.service.ShoppingCartService;
import onlineshopapp.fashionstore.service.impl.ShoppingCartServiceImpl;
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
public class ShoppingCartTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClothesRepository clothesRepository;
    @Mock
    private OrderedClothesRepository orderedClothesRepository;

    private ShoppingCartService service;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        User user1 = new User();
        user1.setUsername("User 1");
        user1.setId((long) 1);

        User user3 = new User();
        user3.setId((long) 3);
        user3.setUsername("User 3");

        Clothes clothes = new Clothes();
        clothes.setId((long) 1);

        OrderedClothes orderedClothes = new OrderedClothes();
        orderedClothes.setId((long) 100);
        orderedClothes.setClothes(clothes);
        orderedClothes.setQuantity(2);
        orderedClothes.setPrice(orderedClothes.getClothes().getPrice() * 2);
        orderedClothes.setSize("M");
        orderedClothes.setUserId((long) 1);

        List<OrderedClothes> clothesList = new ArrayList<>();
        clothesList.add(orderedClothes);

        ShoppingCart cart = new ShoppingCart(user1);
        cart.setOrderedClothes(clothesList);
        cart.setId((long) 50);

        Mockito.when(this.userRepository.findByUsername("User 1"))
                .thenReturn(java.util.Optional.of(user1));

        Mockito.when(this.userRepository.findByUsername("User 3"))
                .thenReturn(java.util.Optional.of(user3));

        Mockito.when(this.clothesRepository.findById(orderedClothes.getClothes().getId()))
                .thenReturn(java.util.Optional.of(clothes));

        Mockito.when(this.shoppingCartRepository.save(Mockito.any(ShoppingCart.class)))
                .thenReturn(cart);

        Mockito.when(this.shoppingCartRepository.findByUserAndStatus(user3, ShoppingCartStatus.CREATED))
                .thenReturn(java.util.Optional.of(new ShoppingCart(user3)));

        Mockito.when(this.shoppingCartRepository.findById((long) 50))
                .thenReturn(java.util.Optional.of(cart));

        this.service = Mockito.spy(new ShoppingCartServiceImpl(this.shoppingCartRepository, this.userRepository,
                this.clothesRepository, this.orderedClothesRepository));
    }

    @Test
    public void addProductToShoppingCartTest() {

        Clothes clothes = new Clothes();
        clothes.setId((long) 1);
        OrderedClothes orderedClothes = new OrderedClothes();
        orderedClothes.setId((long) 100);
        orderedClothes.setClothes(clothes);
        orderedClothes.setQuantity(2);
        orderedClothes.setPrice(orderedClothes.getClothes().getPrice() * 2);
        orderedClothes.setSize("M");
        orderedClothes.setUserId((long) 1);

        Assert.assertEquals(getCartForUser1(), service.addProductToShoppingCart("User 1", orderedClothes));

    }

    @Test
    public void addProductToShoppingCartEmptyUserTest() {

        Assert.assertThrows("User 2", UserNotFoundException.class,
                () -> service.addProductToShoppingCart("User 2", new OrderedClothes()));
    }

    @Test
    public void addProductToShoppingCartEmptyOrderTest() {

        Clothes clothes = new Clothes();
        clothes.setId((long) 2);
        OrderedClothes orderedClothes = new OrderedClothes();
        orderedClothes.setClothes(clothes);

        Assert.assertThrows("2", ProductNotFoundException.class,
                () -> service.addProductToShoppingCart("User 1", orderedClothes));
    }

    @Test
    public void getActiveShoppingCartTest() {

        User u = new User();
        u.setUsername("User 3");
        u.setId((long) 3);

        Assert.assertEquals(new ShoppingCart(u), service.getActiveShoppingCart("User 3"));
    }

    @Test
    public void getActiveNewShoppingCartTest() {

        Assert.assertEquals(getCartForUser1(), service.getActiveShoppingCart("User 1"));
    }

    @Test
    public void getActiveShoppingCartEmptyUserTest() {

        Assert.assertThrows("User 2", UserNotFoundException.class,
                () -> service.getActiveShoppingCart("User 2"));
    }

    private ShoppingCart getCartForUser1() {

        User u = new User();
        u.setUsername("User 1");
        u.setId((long) 1);
        Clothes clothes = new Clothes();
        clothes.setId((long) 1);
        OrderedClothes orderedClothes = new OrderedClothes();
        orderedClothes.setId((long) 100);
        orderedClothes.setClothes(clothes);
        orderedClothes.setQuantity(2);
        orderedClothes.setPrice(orderedClothes.getClothes().getPrice() * 2);
        orderedClothes.setSize("M");
        orderedClothes.setUserId((long) 1);
        ShoppingCart cart = new ShoppingCart(u);
        cart.getOrderedClothes().add(orderedClothes);
        cart.setId((long) 50);

        return cart;
    }

    @Test
    public void listAllProductsInShoppingCartTest() {

        Assert.assertEquals(getCartForUser1().getOrderedClothes(), service.listAllProductsInShoppingCart((long) 50));
    }

    @Test
    public void listAllProductsInEmptyShoppingCartTest() {

        Assert.assertThrows("55", ShoppingCartNotFoundException.class,
                () -> service.listAllProductsInShoppingCart((long) 55));
    }

}


























