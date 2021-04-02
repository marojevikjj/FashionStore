package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.Clothes;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Clothes, Long> {

}
