package ru.league.test_league.service;

import org.springframework.stereotype.Service;
import ru.league.test_league.models.Product;
import ru.league.test_league.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Product> findById(Product product) {
        return repository.findById(product.getId());
    }

    public Product save(Product product) {
        return repository.save(product);
    }


    public void delete(Product product) {
        repository.delete(product);
    }

}
