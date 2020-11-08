package ru.league.league.models;

public class Statistic {
    private String productName;
    private int minPrice;
    private int maxPrice;
    private double frequentPriceChange;

    public Statistic(String productName, int minPrice, int maxPrice, double frequentPriceChange) {
        this.productName = productName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.frequentPriceChange = frequentPriceChange;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getFrequentPriceChange() {
        return frequentPriceChange;
    }

    public void setFrequentPriceChange(double frequentPriceChange) {
        this.frequentPriceChange = frequentPriceChange;
    }
}
