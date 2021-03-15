package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.exceptions.InvalidClothesIdException;
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

    @Override
    public Clothes findById(Long id) {
        return this.clothesRepository.findById(id).orElseThrow(InvalidClothesIdException::new);
    }

    @Override
    public List<Clothes> listAllClothes() {
        return this.clothesRepository.findAll();
    }

    @Override
    public Clothes create(String name, String description, String image, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL) {
        Clothes product = new Clothes(name, description, image, price, grade, quantitySizeS, quantitySizeM, quantitySizeL, quantitySizeXL);
        return this.clothesRepository.save(product);
    }


    @Override
    public Clothes update(Long id, String name, String description, String image, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL) {
        Clothes product = this.findById(id);
        product.setName(name);
        product.setDescription(description);
        product.setImage(image);
        product.setPrice(price);
        product.setGrade(grade);
        product.setQuantitySizeS(quantitySizeS);
        product.setQuantitySizeM(quantitySizeM);
        product.setQuantitySizeL(quantitySizeL);
        product.setQuantitySizeXL(quantitySizeXL);
        return this.clothesRepository.save(product);
    }

    @Override
    public Clothes delete(Long id) {
        Clothes product = this.findById(id);
        this.clothesRepository.delete(product);
        return product;
    }
}
