package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.service.ClothesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ClothesService clothesService;

    public ProductsController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

  /*  @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Clothes> products = this.clothesService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("bodyContent", "products");
        return "products";
    }
*/

    @GetMapping
    public String showProducts(@RequestParam(required = false) String nameSearch, @RequestParam(required = false) Long categoryId, Model model) {
        if (nameSearch == null && categoryId == null) {
            model.addAttribute("products", this.clothesService.listAllClothes());
        } /* else {
            model.addAttribute("products", this.service.listProductsByNameAndCategory(nameSearch, categoryId));
        } */
        return "products";
    }
}
