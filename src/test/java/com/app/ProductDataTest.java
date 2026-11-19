package com.app;

import com.app.data.ProductData;
import com.app.model.Offer;
import com.app.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDataTest {

    @Test
    void testProductCountAndIds() {
        List<Product> products = ProductData.getAllProducts();
        assertEquals(8, products.size());

        for (int i = 1; i <= 8; i++) {
            Product p = ProductData.getProductById(String.valueOf(i));
            assertNotNull(p, "Product id " + i + " should exist");
            assertTrue(p.getName().startsWith("Dummy " + i));
            assertNotNull(p.getCategory());
            assertNotNull(p.getBrand());
            assertEquals(3, p.getOffers().size());
        }
    }

    @Test
    void testOffersAndLowestPrice() {
        for (Product product : ProductData.getAllProducts()) {
            List<Offer> offers = product.getOffers();
            assertEquals(3, offers.size());

            Offer lowest = product.getLowestOffer();
            assertNotNull(lowest);

            for (Offer offer : offers) {
                assertTrue(lowest.getPrice() <= offer.getPrice());
                assertNotNull(offer.getSiteName());
                assertNotNull(offer.getUrl());
                assertTrue(offer.getUrl().startsWith("https://example.com/"));
            }
        }
    }

    @Test
    void testCategoryFiltering() {
        List<Product> cleansers = ProductData.getProductsByCategory("Cleanser");
        assertFalse(cleansers.isEmpty());
        for (Product p : cleansers) {
            assertEquals("Cleanser", p.getCategory());
        }

        List<Product> all = ProductData.getProductsByCategory("All");
        assertEquals(8, all.size());
    }

    @Test
    void testSearchProducts() {
        List<Product> search1 = ProductData.searchProducts("Salicylic", "All");
        assertFalse(search1.isEmpty());
        assertEquals("2", search1.get(0).getId());

        List<Product> noMatches = ProductData.searchProducts("NonExistentKeywordXYZ", "All");
        assertTrue(noMatches.isEmpty());
    }
}
