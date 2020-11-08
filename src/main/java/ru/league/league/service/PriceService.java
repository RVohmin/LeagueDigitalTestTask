package ru.league.league.service;

import org.springframework.stereotype.Service;
import ru.league.league.models.Price;
import ru.league.league.repository.PriceRepository;

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
        return repository.getPricesByProductId(id);
    }

    public Optional<Price> findById(Price price) {
        return repository.findById(price.getPriceId());
    }

    public Price save(Price price) {
        return repository.save(price);
    }

    public void delete(Price price) {
        repository.delete(price);
    }
}
