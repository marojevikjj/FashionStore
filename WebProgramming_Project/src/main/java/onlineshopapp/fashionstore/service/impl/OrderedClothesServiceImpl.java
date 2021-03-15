package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.OrderedClothes;
import onlineshopapp.fashionstore.repository.OrderedClothesRepository;
import onlineshopapp.fashionstore.service.OrderedClothesService;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderedClothesServiceImpl implements OrderedClothesService {

    private final OrderedClothesRepository orderedClothesRepository;

    public OrderedClothesServiceImpl(OrderedClothesRepository orderedClothesRepository) {
        this.orderedClothesRepository = orderedClothesRepository;
    }

    @Override
    public OrderedClothes addNewOrderedClothes(OrderedClothes orderedClothes) {
//        OrderedClothes orderedClothes = new OrderedClothes(clothes, quantity, size, price, userId);
        return this.orderedClothesRepository.save(orderedClothes);
    }

    @Override
    public Optional<OrderedClothes> findById(Long id) {
        return this.orderedClothesRepository.findById(id);
    }

    @Override
    public void delete(OrderedClothes orderedClothes) {
        this.orderedClothesRepository.delete(orderedClothes);
    }

}
