package ru.league.test_league.repository;

import org.springframework.data.repository.CrudRepository;
import ru.league.test_league.models.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
//    @Query("SELECT customer c SET c.firstName = :firstName WHERE c.id = :id")
//    Integer setNewFirstNameForId(@Param("firstName") String firstName, @Param("id") long id);
}
