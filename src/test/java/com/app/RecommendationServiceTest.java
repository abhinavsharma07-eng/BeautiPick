package com.app;

import com.app.data.DatabaseConfig;
import com.app.model.QuizSubmission;
import com.app.model.RecommendationResult;
import com.app.service.RecommendationService;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest {

    private RecommendationService service;

    @BeforeEach
    void setUp() {
        service = new RecommendationService();
    }

    @Test
    void testEmptySubmission() {
        List<RecommendationResult> results = service.getRecommendations(null);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void testLiveDatabaseRecommendationsForAcneConcern() {
        Assumptions.assumeTrue(DatabaseConfig.isConfigured(),
            "Skipping live database test: DB_URL, DB_USER, or DB_PASSWORD environment variables are not set");

        QuizSubmission sub = new QuizSubmission(
            "oily",
            Collections.singletonList("acne"),
            "20-29",
            "no",
            "medium",
            "any"
        );

        List<RecommendationResult> results = service.getRecommendations(sub);
        assertNotNull(results);
        assertFalse(results.isEmpty(), "Should return matching products for acne from database");

        RecommendationResult top = results.get(0);
        assertTrue(top.getScore() > 0, "Top match should have positive score");
        assertNotNull(top.getMatchReason(), "Match reason should be populated");
    }

    @Test
    void testLiveDatabaseCategoryFiltering() {
        Assumptions.assumeTrue(DatabaseConfig.isConfigured(),
            "Skipping live database test: DB_URL, DB_USER, or DB_PASSWORD environment variables are not set");

        QuizSubmission sub = new QuizSubmission(
            "dry",
            Collections.singletonList("dryness"),
            "30-39",
            "yes",
            "medium",
            "serum"
        );

        List<RecommendationResult> results = service.getRecommendations(sub);
        assertNotNull(results);
        for (RecommendationResult res : results) {
            assertEquals("Serum", res.getProduct().getCategory());
        }
    }
}
