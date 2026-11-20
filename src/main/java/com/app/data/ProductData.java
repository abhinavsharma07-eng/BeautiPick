package com.app.data;

import com.app.model.Offer;
import com.app.model.Product;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads product data and retailer offers from the MySQL database using HikariCP.
 * Automatically bootstraps schema and seed data if the database tables are empty.
 */
public class ProductData {

    private static final Logger LOGGER = Logger.getLogger(ProductData.class.getName());
    private static volatile boolean initialized = false;

    private static void ensureDatabaseInitialized(Connection conn) {
        if (initialized) {
            return;
        }
        synchronized (ProductData.class) {
            if (initialized) {
                return;
            }
            try (Statement st = conn.createStatement()) {
                try {
                    st.execute("ALTER TABLE products MODIFY COLUMN budget_level VARCHAR(50) NULL DEFAULT NULL");
                } catch (SQLException ignored) {
                }

                boolean isEmpty = true;
                try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM products")) {
                    if (rs.next()) {
                        isEmpty = (rs.getInt(1) == 0);
                    }
                } catch (SQLException tableMissing) {
                    executeSqlScript(conn, "schema.sql");
                    isEmpty = true;
                }

                if (isEmpty) {
                    executeSqlScript(conn, "seed.sql");
                } else {
                    // Check if skin_type attributes are present in existing database
                    boolean hasSkinTypes = false;
                    try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM product_attributes WHERE kind = 'skin_type'")) {
                        if (rs.next()) {
                            hasSkinTypes = (rs.getInt(1) > 0);
                        }
                    } catch (SQLException ignored) {
                    }
                    if (!hasSkinTypes) {
                        executeSqlScript(conn, "migration_skin_types.sql");
                    }
                }

                try (ResultSet checkRs = st.executeQuery("SELECT COUNT(*) FROM products")) {
                    if (checkRs.next() && checkRs.getInt(1) > 0) {
                        initialized = true;
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Database init check failed: {0}", e.getMessage());
            }
        }
    }

    private static void executeSqlScript(Connection conn, String scriptName) {
        try (InputStream is = ProductData.class.getClassLoader().getResourceAsStream(scriptName)) {
            if (is == null) {
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    String trimmed = line.trim();
                    if (trimmed.startsWith("--") || trimmed.startsWith("/*") || trimmed.isEmpty()) {
                        continue;
                    }
                    sb.append(line).append("\n");
                    if (trimmed.endsWith(";")) {
                        String sql = sb.toString().trim();
                        if (sql.endsWith(";")) {
                            sql = sql.substring(0, sql.length() - 1).trim();
                        }
                        if (!sql.isEmpty()) {
                            try (Statement stmt = conn.createStatement()) {
                                stmt.execute(sql);
                            } catch (SQLException e) {
                                LOGGER.log(Level.WARNING, "SQL execution failed for statement in {0}: {1}", new Object[]{scriptName, e.getMessage()});
                            }
                        }
                        sb.setLength(0);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to read SQL script {0}: {1}", new Object[]{scriptName, e.getMessage()});
        }
    }

    public static List<Product> getAllProducts() {
        String sql = "SELECT id, name, brand, category, image_url, description, budget_level, suitable_for_sensitive FROM products ORDER BY id ASC";
        try (Connection conn = DatabaseConfig.getConnection()) {
            ensureDatabaseInitialized(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    products.add(mapProductRow(rs));
                }
                populateAttributesAndOffers(conn, products);
                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading all products from database", e);
        }
    }

    public static Product getProductById(String id) {
        if (id == null || !id.trim().matches("\\d+")) {
            return null;
        }

        String sql = "SELECT id, name, brand, category, image_url, description, budget_level, suitable_for_sensitive FROM products WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection()) {
            ensureDatabaseInitialized(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, Integer.parseInt(id.trim()));
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Product product = mapProductRow(rs);
                        populateAttributesAndOffers(conn, Collections.singletonList(product));
                        return product;
                    }
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error loading product id " + id + " from database", e);
        }
    }

    public static List<String> getCategories() {
        String sql = "SELECT DISTINCT category FROM products WHERE category IS NOT NULL AND TRIM(category) != '' ORDER BY category ASC";
        List<String> categories = new ArrayList<>();
        categories.add("All");

        try (Connection conn = DatabaseConfig.getConnection()) {
            ensureDatabaseInitialized(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String cat = rs.getString("category");
                    if (cat != null && !cat.trim().equalsIgnoreCase("All")) {
                        categories.add(cat.trim());
                    }
                }
            }
        } catch (Exception e) {
            return Arrays.asList("All", "Cleanser", "Cream", "Lotion", "Moisturizer", "Serum", "Sunscreen", "Makeup");
        }

        if (categories.size() == 1) {
            return Arrays.asList("All", "Cleanser", "Cream", "Lotion", "Moisturizer", "Serum", "Sunscreen", "Makeup");
        }
        return categories;
    }

    public static List<Product> getProductsByCategory(String category) {
        if (category == null || category.equalsIgnoreCase("All") || category.trim().isEmpty()) {
            return getAllProducts();
        }

        String sql = "SELECT id, name, brand, category, image_url, description, budget_level, suitable_for_sensitive FROM products WHERE LOWER(category) = LOWER(?) ORDER BY id ASC";
        try (Connection conn = DatabaseConfig.getConnection()) {
            ensureDatabaseInitialized(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, category.trim());
                try (ResultSet rs = ps.executeQuery()) {
                    List<Product> products = new ArrayList<>();
                    while (rs.next()) {
                        products.add(mapProductRow(rs));
                    }
                    populateAttributesAndOffers(conn, products);
                    return products;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading products by category from database", e);
        }
    }

    public static List<Product> searchProducts(String query, String category) {
        boolean hasCategory = category != null && !category.equalsIgnoreCase("All") && !category.trim().isEmpty();
        boolean hasQuery = query != null && !query.trim().isEmpty();

        StringBuilder sql = new StringBuilder("SELECT id, name, brand, category, image_url, description, budget_level, suitable_for_sensitive FROM products WHERE 1=1");
        if (hasCategory) {
            sql.append(" AND LOWER(category) = LOWER(?)");
        }
        if (hasQuery) {
            sql.append(" AND (LOWER(name) LIKE ? OR LOWER(brand) LIKE ? OR LOWER(description) LIKE ? OR id IN (SELECT product_id FROM product_attributes WHERE kind = 'tag' AND LOWER(value) LIKE ?))");
        }
        sql.append(" ORDER BY id ASC");

        try (Connection conn = DatabaseConfig.getConnection()) {
            ensureDatabaseInitialized(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int paramIndex = 1;
                if (hasCategory) {
                    ps.setString(paramIndex++, category.trim());
                }
                if (hasQuery) {
                    String pattern = "%" + query.trim().toLowerCase() + "%";
                    ps.setString(paramIndex++, pattern);
                    ps.setString(paramIndex++, pattern);
                    ps.setString(paramIndex++, pattern);
                    ps.setString(paramIndex++, pattern);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    List<Product> products = new ArrayList<>();
                    while (rs.next()) {
                        products.add(mapProductRow(rs));
                    }
                    populateAttributesAndOffers(conn, products);
                    return products;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching products from database", e);
        }
    }

    private static Product mapProductRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(String.valueOf(rs.getInt("id")));
        p.setName(rs.getString("name"));
        p.setBrand(rs.getString("brand"));
        p.setCategory(rs.getString("category"));
        p.setImageUrl(rs.getString("image_url"));
        p.setDescription(rs.getString("description"));
        p.setBudgetLevel(rs.getString("budget_level"));
        p.setSuitableForSensitive(rs.getBoolean("suitable_for_sensitive"));
        p.setTags(new ArrayList<>());
        p.setSkinTypes(new ArrayList<>());
        p.setConcerns(new ArrayList<>());
        p.setAgeGroups(new ArrayList<>());
        p.setOffers(new ArrayList<>());
        return p;
    }

    private static void populateAttributesAndOffers(Connection conn, List<Product> products) throws SQLException {
        if (products == null || products.isEmpty()) {
            return;
        }

        Map<Integer, Product> productMap = new HashMap<>();
        for (Product p : products) {
            try {
                productMap.put(Integer.parseInt(p.getId()), p);
            } catch (NumberFormatException ignored) {
            }
        }

        String placeholders = String.join(",", Collections.nCopies(products.size(), "?"));

        // 1. Batch load product attributes
        String attrSql = "SELECT product_id, kind, value FROM product_attributes WHERE product_id IN (" + placeholders + ")";
        try (PreparedStatement ps = conn.prepareStatement(attrSql)) {
            int idx = 1;
            for (Product p : products) {
                ps.setInt(idx++, Integer.parseInt(p.getId()));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int prodId = rs.getInt("product_id");
                    String kind = rs.getString("kind");
                    String value = rs.getString("value");
                    Product prod = productMap.get(prodId);
                    if (prod != null && kind != null && value != null) {
                        switch (kind.toLowerCase()) {
                            case "tag":
                                prod.getTags().add(value);
                                break;
                            case "skin_type":
                                prod.getSkinTypes().add(value);
                                break;
                            case "concern":
                                prod.getConcerns().add(value);
                                break;
                            case "age_group":
                                prod.getAgeGroups().add(value);
                                break;
                        }
                    }
                }
            }
        }

        // 2. Batch load offers
        String offerSql = "SELECT product_id, site_name, price, url FROM offers WHERE product_id IN (" + placeholders + ") ORDER BY product_id, price ASC";
        try (PreparedStatement ps = conn.prepareStatement(offerSql)) {
            int idx = 1;
            for (Product p : products) {
                ps.setInt(idx++, Integer.parseInt(p.getId()));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int prodId = rs.getInt("product_id");
                    String siteName = rs.getString("site_name");
                    double price = rs.getDouble("price");
                    String url = rs.getString("url");
                    Product prod = productMap.get(prodId);
                    if (prod != null) {
                        prod.getOffers().add(new Offer(siteName, price, url));
                    }
                }
            }
        }
    }
}
