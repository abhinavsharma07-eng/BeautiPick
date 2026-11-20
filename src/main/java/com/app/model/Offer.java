package com.app.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Represents a seller offer for a product with pricing and outbound link.
 */
@Getter
@Setter
public class Offer implements Serializable {
    @Serial
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

    public String getFormattedPrice() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        return currencyFormat.format(price);
    }
}
