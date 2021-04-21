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
        List<Clothes> featuredProducts = this.getClothes(fp, 4);

        List<Clothes> lp = this.clothesService.sortDescendingByDate();
        List<Clothes> latestProducts = this.getClothes(lp, 8);

        List<Clothes> clothes = this.clothesService.listAll();
        Random rand = new Random();
        Clothes exclusive =null;
        if(lp.size() >= clothes.size())
            exclusive = clothes.get(rand.nextInt(clothes.size()));
        else
            exclusive = clothes.get(rand.nextInt(lp.size()));

        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("latestProducts", latestProducts);
        model.addAttribute("exclusive", exclusive);

        return "home";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
        return "access_denied";
    }


    private List<Clothes> getClothes(List<Clothes> c, int n){
        int tmp = 0;
        List<Clothes> listOfClothes = new ArrayList<>();
        for(Clothes cl : c){
            listOfClothes.add(cl);
            if(tmp == n-1)
                break;
            tmp++;
        }
        return listOfClothes;
    }
}
