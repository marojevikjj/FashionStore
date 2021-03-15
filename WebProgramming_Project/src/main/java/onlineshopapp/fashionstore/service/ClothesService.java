package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Clothes;

import java.util.List;

public interface ClothesService {
    List<Clothes> findAll();

    Clothes findById(Long id);

    List<Clothes> listAllClothes();

    Clothes create( String name, String description, String image, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL);

    Clothes update(Long id, String name, String description, String image, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL);

    Clothes delete(Long id);
}
