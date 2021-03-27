package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesGrade;
import onlineshopapp.fashionstore.model.User;

import java.util.List;
import java.util.Optional;

public interface ClothesGradeService {

    ClothesGrade findByUserAndClothes(User user, Clothes clothes);
    void updateGrade(ClothesGrade cg, double grade);

    void addGrade(User user, Clothes clothes, double grade);

    List<ClothesGrade> getGradesByClothes(Clothes clothes);
}
