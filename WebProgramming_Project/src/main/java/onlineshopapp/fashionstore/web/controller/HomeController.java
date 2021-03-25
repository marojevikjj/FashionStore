package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.service.ClothesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping({"/home","/"})
public class HomeController {

    private final ClothesService clothesService;

    public HomeController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @GetMapping
    public String showHomePage(Model model) {

        List<Clothes> fp = this.clothesService.sortDescendingByGrade();
        Collections.shuffle(fp);
        List<Clothes> featuredProducts = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            Clothes element1 = fp.get(i);
            featuredProducts.add(element1);
        }

        List<Clothes> lp = this.clothesService.sortDescendingByDate();
        List<Clothes> latestProducts = new ArrayList<>();

        for(int i = 0; i < 8; i++){
            Clothes element = lp.get(i);
            latestProducts.add(element);
        }

        List<Clothes> clothes = this.clothesService.listAll();
        Random rand = new Random();
        Clothes exclusive = clothes.get(rand.nextInt(lp.size()));

        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("latestProducts", latestProducts);
        model.addAttribute("exclusive", exclusive);
        return "home";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
        return "access_denied";
    }
}
