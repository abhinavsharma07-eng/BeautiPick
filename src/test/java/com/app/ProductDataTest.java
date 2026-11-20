package com.app;

import com.app.data.DatabaseConfig;
import com.app.data.ProductData;
import com.app.model.Offer;
import com.app.model.Product;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDataTest {

    @Test
    void testNonNumericIdReturnsNullWithoutThrowing() {
        assertNull(ProductData.getProductById(null));
        assertNull(ProductData.getProductById(""));
        assertNull(ProductData.getProductById("   "));
        assertNull(ProductData.getProductById("abc"));
        assertNull(ProductData.getProductById("invalid-id"));
        assertNull(ProductData.getProductById("123a"));
    }

    @Test
    void testDatabaseConfigMissingVariablesDetection() {
        if (!DatabaseConfig.isConfigured()) {
            List<String> missing = DatabaseConfig.getMissingEnvVariables();
            assertFalse(missing.isEmpty(), "Missing variables should be reported");
            for (String var : missing) {
                assertTrue(var.equals("DB_URL") || var.equals("DB_USER") || var.equals("DB_PASSWORD"));
            }
        }
    }

    @Test
    void testDatabaseLiveQueries() {
        Assumptions.assumeTrue(DatabaseConfig.isConfigured(),
            "Skipping live database test: DB_URL, DB_USER, or DB_PASSWORD environment variables are not set");

        List<Product> products = ProductData.getAllProducts();
        assertNotNull(products);
        assertEquals(18, products.size(), "Database should contain exactly 18 seeded products");

        for (Product p : products) {
            assertNotNull(p.getId());
            assertNotNull(p.getName());
            assertNotNull(p.getBrand());
            assertNotNull(p.getCategory());
        }

        boolean anyHasSkinType = products.stream().anyMatch(p -> !p.getSkinTypes().isEmpty());
        assertTrue(anyHasSkinType, "Products should have targeted skin types populated");

        Product p1 = ProductData.getProductById("1");
        assertNotNull(p1);
        assertFalse(p1.getSkinTypes().isEmpty(), "Product 1 should have skin types");

        Product p13 = ProductData.getProductById("13");
        assertNotNull(p13);
        assertTrue(p13.isSuitableForSensitive(), "Product 13 should be sensitive safe");
    }

    @Test
    void testCategoryFilterLiveDatabase() {
        Assumptions.assumeTrue(DatabaseConfig.isConfigured(),
            "Skipping live database test: DB_URL, DB_USER, or DB_PASSWORD environment variables are not set");

        List<String> categories = ProductData.getCategories();
        assertNotNull(categories);
        assertTrue(categories.contains("All"));
    }
}
