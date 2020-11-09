package ru.league.league.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.league.league.models.Price;
import ru.league.league.models.Product;
import ru.league.league.models.Statistic;
import ru.league.league.service.PriceService;
import ru.league.league.service.ProductService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private static final Comparator<Price> COMPARATOR = Comparator.comparing(Price::getPriceDate);

    /**
     * path to .csv file
     */
    @Value("${file.path}")
    private File path;

    private final ProductService productService;
    private final PriceService priceService;

    public ProductController(ProductService productService, PriceService priceService) {
        this.productService = productService;
        this.priceService = priceService;
    }

    /**
     *
     * @param data - String data in format "2020-11-05".
     * @return Map with product Name as key and actual price as value
     */
    @GetMapping("/{data}")
    public synchronized ResponseEntity<Map<String, Integer>> findAllProductsWithActualPrice(@PathVariable("data") String data) {
        var dateWithTime = getDateFromString(data);
        var products = productService.findAll().parallelStream().collect(Collectors.toList());

        final var mapResult = products.parallelStream()
                .collect(Collectors
                        .toMap(
                                Product::getName,
                                x -> x.getPrices().parallelStream()
                                        .filter(o1 -> o1.getPriceDate().isBefore(dateWithTime))
                        .max(COMPARATOR).get().getPrice()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(mapResult);
    }

    /**
     * Method for get statistic by each product
     * @return List with Statistic objects
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<Statistic>> getStatistic() {
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        var products = this.productService.findAll().parallelStream().collect(Collectors.toList());

        final List<Statistic> statisticList = products.parallelStream()
                .map(product -> {
                    try {
                        return threadPool.submit(() -> {
                            var prices = product.getPrices();
                            var firstPrice = prices.parallelStream().min(COMPARATOR).get();
                            var lastPrice = prices.parallelStream().max(COMPARATOR).get();
                            var resultDays = ChronoUnit.DAYS.between(firstPrice.getPriceDate(), lastPrice.getPriceDate()) + 1;
                            var frequent = prices.size() / (double) resultDays;
                            return new Statistic(product.getName(), firstPrice.getPrice(), lastPrice.getPrice(), frequent);
                        }).get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(statisticList);
    }

    @GetMapping("/upload")
    public ResponseEntity<String> upload() {
        LOGGER.info("Starting upload from file at {}", LocalDateTime.now());
        String line = "";
        String cvsSplitBy = ";";
        Integer countProducts = 0;
        Product newProduct = null;
        Price newPrice;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] val = line.split(cvsSplitBy);
                LocalDateTime date = getDateFromString(val[4]);
                newProduct = new Product(Integer.parseInt(val[0]), val[1]);
                newPrice = new Price(Integer.parseInt(val[2]), Integer.parseInt(val[3]), date);
                newPrice.setProduct(newProduct);
                var tempProduct = productService.findById(newProduct).orElse(null);
                if (tempProduct == null) {
                    productService.save(newProduct);
                }
                priceService.save(newPrice);
                countProducts++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Ending upload from file at {}", LocalDateTime.now());
        LOGGER.info("Processed {} records", countProducts);
        return ResponseEntity.status(HttpStatus.OK).body(countProducts + " records processed!");
    }

    private LocalDateTime getDateFromString(String date) {
        final var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateFromFile = LocalDate.parse(date, dtf);
        return LocalDateTime.of(dateFromFile, LocalDateTime.now().toLocalTime());
    }
}
