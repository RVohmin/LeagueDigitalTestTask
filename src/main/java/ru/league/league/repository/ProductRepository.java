package ru.league.league.repository;

import org.springframework.data.repository.CrudRepository;
import ru.league.league.models.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
