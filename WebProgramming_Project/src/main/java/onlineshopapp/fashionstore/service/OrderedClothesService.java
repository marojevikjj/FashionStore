package onlineshopapp.fashionstore.service;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.OrderedClothes;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface OrderedClothesService{
    OrderedClothes addNewOrderedClothes(OrderedClothes orderedClothes);
    Optional<OrderedClothes> findById(Long id);
    void delete(OrderedClothes orderedClothes);
}
