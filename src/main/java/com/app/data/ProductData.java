package com.app.data;

// EDIT HERE: Product data definitions. Add, remove, or modify dummy products and their retailer offers below.

import com.app.model.Offer;
import com.app.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ProductData {

    private static final List<Product> PRODUCTS = new ArrayList<>();

    static {
        // Dummy 1: Cleanser for Dry/Sensitive Skin
        PRODUCTS.add(new Product(
            "1",
            "Dummy 1 - Gentle Hydrating Foam Cleanser",
            "AuraSkin",
            "Cleanser",
            "images/placeholder.svg",
            "A soothing, pH-balanced amino acid foam cleanser designed to melt impurities without stripping vital moisture.",
            Arrays.asList("Hydrating", "pH-Balanced", "Fragrance-Free"),
            Arrays.asList("dry", "normal", "sensitive"),
            Arrays.asList("dryness", "redness"),
            "low",
            true,
            Arrays.asList("under-20", "20-29", "30-39", "40-49", "50+"),
            Arrays.asList(
                new Offer("Site A", 14.99, "https://example.com/dummy-1-a"),
                new Offer("Site B", 17.50, "https://example.com/dummy-1-b"),
                new Offer("Site C", 16.00, "https://example.com/dummy-1-c")
            )
        ));

        // Dummy 2: Cleanser for Oily/Acne-Prone Skin
        PRODUCTS.add(new Product(
            "2",
            "Dummy 2 - Clarifying Salicylic Acid Cleanser",
            "PureBotanics",
            "Cleanser",
            "images/placeholder.svg",
            "Formulated with 2% BHA to deeply unclog pores, reduce excess sebum, and prevent breakouts without irritation.",
            Arrays.asList("Clarifying", "BHA", "Pore-Clearing"),
            Arrays.asList("oily", "combination"),
            Arrays.asList("acne", "dullness"),
            "medium",
            false,
            Arrays.asList("under-20", "20-29", "30-39"),
            Arrays.asList(
                new Offer("Site A", 22.00, "https://example.com/dummy-2-a"),
                new Offer("Site B", 19.99, "https://example.com/dummy-2-b"),
                new Offer("Site C", 24.50, "https://example.com/dummy-2-c")
            )
        ));

        // Dummy 3: Moisturizer for Dry/Sensitive Skin
        PRODUCTS.add(new Product(
            "3",
            "Dummy 3 - Barrier Repair Ceramide Cream",
            "DermaGlow",
            "Moisturizer",
            "images/placeholder.svg",
            "Rich therapeutic barrier cream infused with 5 essential ceramides and fatty acids to heal compromised skin.",
            Arrays.asList("Ceramides", "Barrier Support", "Deep Moisture"),
            Arrays.asList("dry", "sensitive", "normal"),
            Arrays.asList("dryness", "redness", "wrinkles"),
            "medium",
            true,
            Arrays.asList("20-29", "30-39", "40-49", "50+"),
            Arrays.asList(
                new Offer("Site A", 28.50, "https://example.com/dummy-3-a"),
                new Offer("Site B", 31.00, "https://example.com/dummy-3-b"),
                new Offer("Site C", 26.99, "https://example.com/dummy-3-c")
            )
        ));

        // Dummy 4: Moisturizer for Oily/Combination Skin
        PRODUCTS.add(new Product(
            "4",
            "Dummy 4 - Oil-Free Matte Water Gel",
            "VelvetTouch",
            "Moisturizer",
            "images/placeholder.svg",
            "Weightless electrolyte-rich water gel that floods skin with moisture while delivering a shine-free matte finish.",
            Arrays.asList("Oil-Free", "Matte Finish", "Lightweight"),
            Arrays.asList("oily", "combination"),
            Arrays.asList("acne", "dullness"),
            "low",
            true,
            Arrays.asList("under-20", "20-29", "30-39"),
            Arrays.asList(
                new Offer("Site A", 15.49, "https://example.com/dummy-4-a"),
                new Offer("Site B", 18.00, "https://example.com/dummy-4-b"),
                new Offer("Site C", 16.99, "https://example.com/dummy-4-c")
            )
        ));

        // Dummy 5: Serum for Brightening & Tone
        PRODUCTS.add(new Product(
            "5",
            "Dummy 5 - Vitamin C & Niacinamide Glow Serum",
            "RadianceLab",
            "Serum",
            "images/placeholder.svg",
            "Potent 15% stabilized vitamin C complex paired with niacinamide to fade stubborn hyperpigmentation and boost luminosity.",
            Arrays.asList("Vitamin C", "Brightening", "Antioxidant"),
            Arrays.asList("combination", "normal", "oily", "dry"),
            Arrays.asList("dark spots", "dullness", "wrinkles"),
            "high",
            false,
            Arrays.asList("20-29", "30-39", "40-49", "50+"),
            Arrays.asList(
                new Offer("Site A", 48.00, "https://example.com/dummy-5-a"),
                new Offer("Site B", 42.99, "https://example.com/dummy-5-b"),
                new Offer("Site C", 45.50, "https://example.com/dummy-5-c")
            )
        ));

        // Dummy 6: Anti-Aging Night Serum
        PRODUCTS.add(new Product(
            "6",
            "Dummy 6 - Retinol Youth Renewal Night Serum",
            "AuraSkin",
            "Serum",
            "images/placeholder.svg",
            "Encapsulated gentle retinol serum that accelerates cellular turnover, reduces fine lines, and refines texture overnight.",
            Arrays.asList("Retinol", "Anti-Aging", "Firming"),
            Arrays.asList("normal", "dry", "combination"),
            Arrays.asList("wrinkles", "dark spots", "dullness"),
            "high",
            false,
            Arrays.asList("30-39", "40-49", "50+"),
            Arrays.asList(
                new Offer("Site A", 55.00, "https://example.com/dummy-6-a"),
                new Offer("Site B", 52.50, "https://example.com/dummy-6-b"),
                new Offer("Site C", 58.00, "https://example.com/dummy-6-c")
            )
        ));

        // Dummy 7: Broad-Spectrum Daily Sunscreen
        PRODUCTS.add(new Product(
            "7",
            "Dummy 7 - Invisible Daily Mineral Sunscreen SPF 50",
            "SolarShield",
            "Sunscreen",
            "images/placeholder.svg",
            "Non-nano zinc oxide sunscreen with zero white cast. Blocks UVA/UVB rays and environmental blue light gently.",
            Arrays.asList("Mineral SPF 50", "Zero White Cast", "Reef-Safe"),
            Arrays.asList("sensitive", "oily", "dry", "combination", "normal"),
            Arrays.asList("dark spots", "redness", "wrinkles"),
            "medium",
            true,
            Arrays.asList("under-20", "20-29", "30-39", "40-49", "50+"),
            Arrays.asList(
                new Offer("Site A", 25.00, "https://example.com/dummy-7-a"),
                new Offer("Site B", 21.99, "https://example.com/dummy-7-b"),
                new Offer("Site C", 24.50, "https://example.com/dummy-7-c")
            )
        ));

        // Dummy 8: Skin-Caring Makeup Base
        PRODUCTS.add(new Product(
            "8",
            "Dummy 8 - Tinted CC Serum Skin Perfector",
            "VelvetTouch",
            "Makeup",
            "images/placeholder.svg",
            "Breathable hybrid CC serum providing buildable dewy coverage while calming redness with centella asiatica.",
            Arrays.asList("Dewy Finish", "Color-Correcting", "Centella"),
            Arrays.asList("normal", "dry", "combination", "sensitive"),
            Arrays.asList("redness", "dullness", "dark spots"),
            "low",
            true,
            Arrays.asList("under-20", "20-29", "30-39", "40-49", "50+"),
            Arrays.asList(
                new Offer("Site A", 18.50, "https://example.com/dummy-8-a"),
                new Offer("Site B", 16.99, "https://example.com/dummy-8-b"),
                new Offer("Site C", 19.00, "https://example.com/dummy-8-c")
            )
        ));
    }

    public static List<Product> getAllProducts() {
        return Collections.unmodifiableList(PRODUCTS);
    }

    public static Product getProductById(String id) {
        if (id == null) return null;
        for (Product product : PRODUCTS) {
            if (product.getId().equalsIgnoreCase(id.trim())) {
                return product;
            }
        }
        return null;
    }

    public static List<String> getCategories() {
        return Arrays.asList("All", "Cleanser", "Moisturizer", "Serum", "Sunscreen", "Makeup");
    }

    public static List<Product> getProductsByCategory(String category) {
        if (category == null || category.equalsIgnoreCase("All") || category.trim().isEmpty()) {
            return getAllProducts();
        }
        List<Product> filtered = new ArrayList<>();
        for (Product product : PRODUCTS) {
            if (product.getCategory().equalsIgnoreCase(category.trim())) {
                filtered.add(product);
            }
        }
        return filtered;
    }

    public static List<Product> searchProducts(String query, String category) {
        List<Product> base = getProductsByCategory(category);
        if (query == null || query.trim().isEmpty()) {
            return base;
        }
        String q = query.trim().toLowerCase(Locale.ROOT);
        List<Product> matches = new ArrayList<>();
        for (Product product : base) {
            boolean matchesName = product.getName().toLowerCase(Locale.ROOT).contains(q);
            boolean matchesBrand = product.getBrand().toLowerCase(Locale.ROOT).contains(q);
            boolean matchesDesc = product.getDescription().toLowerCase(Locale.ROOT).contains(q);
            boolean matchesTag = product.getTags().stream().anyMatch(t -> t.toLowerCase(Locale.ROOT).contains(q));
            if (matchesName || matchesBrand || matchesDesc || matchesTag) {
                matches.add(product);
            }
        }
        return matches;
    }
}
