package ru.league.test_league.service;

import org.springframework.stereotype.Service;
import ru.league.test_league.models.Price;
import ru.league.test_league.repository.PriceRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PriceService {
    private final PriceRepository repository;

    public PriceService(PriceRepository repository) {
        this.repository = repository;
    }

    public Collection<Price> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Price> getByProductId(int id) {
        return repository.getPricesByProduct_Id(id);
    }

    public Optional<Price> findById(Price price) {
        return repository.findById(price.getPrice_id());
    }

    public Price save(Price price) {
        return repository.save(price);
    }

    public void delete(Price price) {
        repository.delete(price);
    }
}
