package onlineshopapp.fashionstore.repository;

import onlineshopapp.fashionstore.model.Clothes;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRerository extends PagingAndSortingRepository<Clothes, Long> {
}
