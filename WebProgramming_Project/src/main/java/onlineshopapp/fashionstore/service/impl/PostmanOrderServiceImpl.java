package onlineshopapp.fashionstore.service.impl;

import onlineshopapp.fashionstore.model.Postman;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.repository.PostmanOrderRepository;
import onlineshopapp.fashionstore.service.PostmanOrderSerivce;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostmanOrderServiceImpl implements PostmanOrderSerivce {

    private final PostmanOrderRepository postmanOrderRepository;

    public PostmanOrderServiceImpl(PostmanOrderRepository postmanOrderRepository) {
        this.postmanOrderRepository = postmanOrderRepository;
    }

    @Override
    public List<Postman> findFromRegion(String city) {
        return this.postmanOrderRepository.findAllByCity(city);
    }



    @Override
    public Postman create(User user, String city) {
        return this.postmanOrderRepository.save(new Postman(user,city));
    }
}
