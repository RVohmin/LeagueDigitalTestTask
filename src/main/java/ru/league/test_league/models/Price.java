package ru.league.test_league.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int price_id;
    private int price;
    private LocalDateTime price_date;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Price(int price_id, int price, LocalDateTime price_date) {
        this.price_id = price_id;
        this.price = price;
        this.price_date = price_date;
    }

    public Price() {
    }

    public int getPrice_id() {
        return price_id;
    }

    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getPrice_date() {
        return price_date;
    }

    public void setPrice_date(LocalDateTime price_date) {
        this.price_date = price_date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

//    @Override
//    public String toString() {
//        return "Price{" +
//                "price_id=" + price_id +
//                ", price=" + price +
//                ", price_date=" + price_date +
//                ", product=" + product +
//                '}';
//    }

}
