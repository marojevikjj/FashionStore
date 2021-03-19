package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Clothes;

import java.util.List;
import java.util.Optional;

public interface ClothesService {
    List<Clothes> findAll();

    Clothes findById(Long id);

    List<Clothes> listAllClothes();

    Clothes create( String name, String description, String image, String image1, String image2, String image3, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL);

    Clothes update(Long id, String name, String description, String image, String image1, String image2, String image3, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL);

    Clothes delete(Long id);

    Optional<Clothes> findOptionalById(Long id);
}
