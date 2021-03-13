package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.repository.ClothesRepository;
import onlineshopapp.fashionstore.service.ClothesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;

    public ClothesServiceImpl(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    @Override
    public List<Clothes> findAll() {
        return clothesRepository.findAll();
    }
}
