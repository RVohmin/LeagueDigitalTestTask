package ru.league.league.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priceId;
    private int price;
    private LocalDateTime priceDate;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Price(int priceId, int price, LocalDateTime priceDate) {
        this.priceId = priceId;
        this.price = price;
        this.priceDate = priceDate;
    }

    public Price() {
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(LocalDateTime priceDate) {
        this.priceDate = priceDate;
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
