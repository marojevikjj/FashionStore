package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesGrade;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.repository.ClothesGradeRepository;
import onlineshopapp.fashionstore.service.ClothesGradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothesGradeServiceImpl implements ClothesGradeService {

    private final ClothesGradeRepository clothesGradeRepository;

    public ClothesGradeServiceImpl(ClothesGradeRepository clothesGradeRepository) {
        this.clothesGradeRepository = clothesGradeRepository;
    }


    @Override
    public ClothesGrade findByUserAndClothes(User user, Clothes clothes) {
        return this.clothesGradeRepository.findClothesGradeByUserAndClothes(user, clothes);
    }

    @Override
    public void updateGrade(ClothesGrade cg, double grade) {
        cg.setGrade(grade);
        this.clothesGradeRepository.save(cg);
    }

    @Override
    public void addGrade(User user, Clothes clothes, double grade) {
        this.clothesGradeRepository.save(new ClothesGrade(user, clothes, grade));
    }

    @Override
    public List<ClothesGrade> getGradesByClothes(Clothes clothes) {
        return this.clothesGradeRepository.findAllByClothes(clothes);
    }
}
