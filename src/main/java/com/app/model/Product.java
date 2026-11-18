package com.app.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Represents a skincare or beauty product with attributes, tagging, and seller offers.
 */
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getSkinTypes() {
        return skinTypes;
    }

    public void setSkinTypes(List<String> skinTypes) {
        this.skinTypes = skinTypes;
    }

    public List<String> getConcerns() {
        return concerns;
    }

    public void setConcerns(List<String> concerns) {
        this.concerns = concerns;
    }

    public String getBudgetLevel() {
        return budgetLevel;
    }

    public void setBudgetLevel(String budgetLevel) {
        this.budgetLevel = budgetLevel;
    }

    public boolean isSuitableForSensitive() {
        return suitableForSensitive;
    }

    public void setSuitableForSensitive(boolean suitableForSensitive) {
        this.suitableForSensitive = suitableForSensitive;
    }

    public List<String> getAgeGroups() {
        return ageGroups;
    }

    public void setAgeGroups(List<String> ageGroups) {
        this.ageGroups = ageGroups;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
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
