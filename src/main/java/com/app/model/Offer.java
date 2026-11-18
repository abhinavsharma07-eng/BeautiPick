package com.app.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Represents a seller offer for a product with pricing and outbound link.
 */
public class Offer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String siteName;
    private double price;
    private String url;

    public Offer() {
    }

    public Offer(String siteName, double price, String url) {
        this.siteName = siteName;
        this.price = price;
        this.url = url;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormattedPrice() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormat.format(price);
    }
}
