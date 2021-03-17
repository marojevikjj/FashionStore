package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.OrderedClothes;
import onlineshopapp.fashionstore.model.ShoppingCart;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.ShoppingCartStatus;
import onlineshopapp.fashionstore.model.exceptions.ProductNotFoundException;
import onlineshopapp.fashionstore.model.exceptions.ShoppingCartNotFoundException;
import onlineshopapp.fashionstore.model.exceptions.UserNotFoundException;
import onlineshopapp.fashionstore.repository.ShoppingCartRepository;
import onlineshopapp.fashionstore.repository.UserRepository;
import onlineshopapp.fashionstore.service.ClothesService;
import onlineshopapp.fashionstore.service.OrderedClothesService;
import onlineshopapp.fashionstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ClothesService clothesService;
    private final OrderedClothesService orderedClothesService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ClothesService clothesService, OrderedClothesService orderedClothesService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.clothesService = clothesService;
        this.orderedClothesService = orderedClothesService;
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, OrderedClothes clothes) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        boolean flag = false;
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Clothes c = this.clothesService.findOptionalById(clothes.getClothes().getId())
                .orElseThrow(() -> new ProductNotFoundException(clothes.getClothes().getId()));
        for(OrderedClothes oc : shoppingCart.getOrderedClothes()){
            if(oc.getUserId().equals(user.getId()) && oc.getClothes().getId().equals(clothes.getClothes().getId()) && oc.getSize().equals(clothes.getSize())){
                oc.setQuantity(oc.getQuantity() + clothes.getQuantity());
                oc.setPrice(oc.getPrice() + clothes.getPrice());
                flag = true;
                break;
            }
        }
        if(!flag)
        {
            OrderedClothes orderedClothes = this.orderedClothesService.addNewOrderedClothes(clothes);
            shoppingCart.getOrderedClothes().add(orderedClothes);
        }

        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository.findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public List<OrderedClothes> listAllProductsInShoppingCart(Long cartId) {
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getOrderedClothes();

    }

    @Override
    public void deleteOrderedClothes(ShoppingCart shoppingCart) {
        shoppingCart.setOrderedClothes(null);
        this.shoppingCartRepository.save(shoppingCart);
    }


}
