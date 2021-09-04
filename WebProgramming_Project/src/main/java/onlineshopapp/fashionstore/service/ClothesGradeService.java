package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesGrade;
import onlineshopapp.fashionstore.model.User;

import java.util.List;

public interface ClothesGradeService {

    ClothesGrade findByUserAndClothes(User user, Clothes clothes);

    ClothesGrade updateGrade(ClothesGrade cg, double grade);

    ClothesGrade addGrade(User user, Clothes clothes, double grade);

    List<ClothesGrade> getGradesByClothes(Clothes clothes);
}
