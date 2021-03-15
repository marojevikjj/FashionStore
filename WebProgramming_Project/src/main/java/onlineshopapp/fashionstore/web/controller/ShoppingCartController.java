package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.*;
import onlineshopapp.fashionstore.service.*;
import org.hibernate.criterion.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ClothesService clothesService;
    private final OrderedClothesService orderedClothesService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, ClothesService clothesService, OrderedClothesService orderedClothesService) {
        this.shoppingCartService = shoppingCartService;
        this.clothesService = clothesService;
        this.orderedClothesService = orderedClothesService;
    }


    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        double total = 0.0;
        for(OrderedClothes c : this.shoppingCartService.listAllProductsInShoppingCart(shoppingCart.getId()))
            total += c.getPrice();
        model.addAttribute("products", this.shoppingCartService.listAllProductsInShoppingCart(shoppingCart.getId()));
        model.addAttribute("bodycContents", "shoppingCart");
        model.addAttribute("total", total);
        return "cart";
    }


    @PostMapping("/add/{id}")
    public String addProductToShoppingCart(@PathVariable Long id,
                                           @RequestParam String size,
                                           @RequestParam Integer quantity, Authentication authentication){

        User user = (User) authentication.getPrincipal();
        Clothes clothes = clothesService.findById(id);
        if((size.equals("S") && clothes.getQuantitySizeS()<quantity) || (size.equals("M") && clothes.getQuantitySizeM()<quantity)
            || (size.equals("L") && clothes.getQuantitySizeL()<quantity) || (size.equals("XL") && clothes.getQuantitySizeXL()<quantity))
            return "redirect:/products"; // TUKA TREBA NEKOJ MESSAGE DA E POJAVI

        if(size.equals("S"))
            clothes.setQuantitySizeS(clothes.getQuantitySizeS() - quantity);
        else if(size.equals("M"))
            clothes.setQuantitySizeM(clothes.getQuantitySizeM() - quantity);
        else if(size.equals("L"))
            clothes.setQuantitySizeL(clothes.getQuantitySizeL() - quantity);
        else
            clothes.setQuantitySizeXL(clothes.getQuantitySizeXL() - quantity);

        OrderedClothes orderedClothes = new OrderedClothes(clothes, quantity, size, clothes.getPrice()*quantity, user.getId());
        this.shoppingCartService.addProductToShoppingCart(user.getUsername(), orderedClothes);
        return "redirect:/shoppingCart";
    }

    @PostMapping("/delete/{id}")
    public String deleteProductFromShoppingCart(@PathVariable Long id, HttpServletRequest req){

        OrderedClothes orderedClothes = this.orderedClothesService.findById(id).get();
        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        Clothes clothes = orderedClothes.getClothes();
        List<OrderedClothes> oc = shoppingCart.getOrderedClothes();

        if(orderedClothes.getSize().equals("S"))
            clothes.setQuantitySizeS(clothes.getQuantitySizeS() + orderedClothes.getQuantity());
        else if(orderedClothes.getSize().equals("M"))
            clothes.setQuantitySizeM(clothes.getQuantitySizeM() + orderedClothes.getQuantity());
        else if(orderedClothes.getSize().equals("L"))
            clothes.setQuantitySizeL(clothes.getQuantitySizeL() + orderedClothes.getQuantity());
        else
            clothes.setQuantitySizeXL(clothes.getQuantitySizeXL() + orderedClothes.getQuantity());

        oc.remove(orderedClothes);
        shoppingCart.setOrderedClothes(oc);
        orderedClothesService.delete(orderedClothes);
        return "redirect:/shoppingCart";
    }
}
