package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.service.ClothesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"/home","/"})
public class HomeController {

    private final ClothesService clothesService;

    public HomeController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @GetMapping
    public String showHomePage(Model model) {

        List<Clothes> featuredProducts = this.clothesService.sortDescendingByGrade();
        List<Clothes> latestProducts = this.clothesService.sortDescendingByDate();

        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("latestProducts", latestProducts);
        return "home";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
        return "access_denied";
    }
}
