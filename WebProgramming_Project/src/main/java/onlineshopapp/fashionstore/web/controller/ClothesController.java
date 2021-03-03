package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.service.ClothesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ClothesController {
    private static ClothesService clothesService;

    public ClothesController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @GetMapping
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


}
