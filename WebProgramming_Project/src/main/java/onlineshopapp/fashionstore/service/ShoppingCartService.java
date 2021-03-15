package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.OrderedClothes;
import onlineshopapp.fashionstore.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart addProductToShoppingCart(String username, OrderedClothes clothes);
    ShoppingCart getActiveShoppingCart(String username);
    List<OrderedClothes> listAllProductsInShoppingCart(Long cartId);
//    void deleteOrderedClothes(OrderedClothes orderedClothes);
}
