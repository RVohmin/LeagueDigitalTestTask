package ru.league.test_league.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.league.test_league.models.Price;

import java.util.List;

@Repository

public interface PriceRepository extends CrudRepository<Price, Integer> {
 List<Price> getPricesByProduct_Id(int id);
// @Query("SELECT price c SET c.firstName = :firstName WHERE c.id = :id")
//    Price getActualPriceByDate(@Param("date") LocalDateTime date, @Param("id") int id);
}
