package com.app.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Represents a skincare or beauty product with attributes, tagging, and seller offers.
 */
@Getter
@Setter
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String brand;
    private String category;
    private String imageUrl;
    private String description;
    private List<String> tags = new ArrayList<>();
    private List<String> skinTypes = new ArrayList<>();
    private List<String> concerns = new ArrayList<>();
    private String budgetLevel;
    private boolean suitableForSensitive;
    private List<String> ageGroups = new ArrayList<>();
    private List<Offer> offers = new ArrayList<>();

    public Product() {
    }

    public Product(String id, String name, String brand, String category, String imageUrl,
                   String description, List<String> tags, List<String> skinTypes,
                   List<String> concerns, String budgetLevel, boolean suitableForSensitive,
                   List<String> ageGroups, List<Offer> offers) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        if (tags != null) this.tags = new ArrayList<>(tags);
        if (skinTypes != null) this.skinTypes = new ArrayList<>(skinTypes);
        if (concerns != null) this.concerns = new ArrayList<>(concerns);
        this.budgetLevel = budgetLevel;
        this.suitableForSensitive = suitableForSensitive;
        if (ageGroups != null) this.ageGroups = new ArrayList<>(ageGroups);
        if (offers != null) this.offers = new ArrayList<>(offers);
    }


    public Offer getLowestOffer() {
        if (offers == null || offers.isEmpty()) {
            return null;
        }
        Offer lowest = offers.get(0);
        for (Offer offer : offers) {
            if (offer.getPrice() < lowest.getPrice()) {
                lowest = offer;
            }
        }
        return lowest;
    }

    public double getLowestPrice() {
        Offer lowest = getLowestOffer();
        return (lowest != null) ? lowest.getPrice() : 0.0;
    }

    public String getFormattedLowestPrice() {
        Offer lowest = getLowestOffer();
        if (lowest != null) {
            return lowest.getFormattedPrice();
        }
        return NumberFormat.getCurrencyInstance(Locale.US).format(0.0);
    }

    public List<Offer> getSortedOffers() {
        List<Offer> sorted = new ArrayList<>(this.offers);
        sorted.sort(Comparator.comparingDouble(Offer::getPrice));
        return sorted;
    }
}
