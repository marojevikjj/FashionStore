package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.exceptions.InvalidClothesIdException;
import onlineshopapp.fashionstore.repository.ClothesRepository;
import onlineshopapp.fashionstore.repository.ProductRepository;
import onlineshopapp.fashionstore.service.ClothesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClothesServiceImpl implements ClothesService {

    private final ClothesRepository clothesRepository;
    private final ProductRepository productRepository;

    public ClothesServiceImpl(ClothesRepository clothesRepository, ProductRepository productRepository) {
        this.clothesRepository = clothesRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Page<Clothes> findAll(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 2, sort);
        return productRepository.findAll(pageable);
    }

    @Override
    public Clothes findById(Long id) {
        return this.clothesRepository.findById(id).orElseThrow(InvalidClothesIdException::new);
    }


    @Override
    public Clothes create(String name, String description, String image, String image1, String image2, String image3, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL) {
        Clothes product = new Clothes(name, description, image, image1, image2, image3, price, grade, quantitySizeS, quantitySizeM, quantitySizeL, quantitySizeXL);
        return this.clothesRepository.save(product);
    }




    @Override
    public Clothes update(Long id, String name, String description, String image, String image1, String image2, String image3, double price, double grade, int quantitySizeS, int quantitySizeM, int quantitySizeL, int quantitySizeXL) {
        Clothes product = this.findById(id);
        product.setName(name);
        product.setDescription(description);
        product.setImage(image);
        product.setImage1(image1);
        product.setImage2(image2);
        product.setImage3(image3);
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

    @Override
    public Optional<Clothes> findOptionalById(Long id) {
        return clothesRepository.findById(id);
    }

    @Override
    public List<Clothes> listProductsByName(String name) {
        String nameLike = "%"+name+"%";
        if(name != null)
            return clothesRepository.findAllByNameLike(nameLike);
        else
            return clothesRepository.findAll();
    }

    @Override
    public List<Clothes> sortDescendingByDate() {
        return this.clothesRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"));
    }

    @Override
    public List<Clothes> sortAscendingAlphabetic() {
        return this.clothesRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public List<Clothes> sortDescendingByGrade() {
        return this.clothesRepository.findAll(Sort.by(Sort.Direction.DESC, "grade"));
    }

    @Override
    public List<Clothes> listAll() {
        return this.clothesRepository.findAll();
    }
}
