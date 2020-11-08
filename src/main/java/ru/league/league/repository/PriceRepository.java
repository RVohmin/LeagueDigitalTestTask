package ru.league.league.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.league.league.models.Price;

import java.util.List;

@Repository

public interface PriceRepository extends CrudRepository<Price, Integer> {
 List<Price> getPricesByProductId(int id);
}
