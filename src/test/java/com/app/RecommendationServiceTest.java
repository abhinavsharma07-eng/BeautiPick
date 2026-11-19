package com.app;

import com.app.model.QuizSubmission;
import com.app.model.RecommendationResult;
import com.app.service.RecommendationService;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest {

    private RecommendationService service;

    @BeforeEach
    void setUp() {
        service = new RecommendationService();
    }

    @Test
    void testRecommendationsForOilyAcneSkin() {
        QuizSubmission sub = new QuizSubmission(
            "oily",
            Arrays.asList("acne"),
            "20-29",
            "no",
            "medium",
            "any"
        );

        List<RecommendationResult> results = service.getRecommendations(sub);
        assertFalse(results.isEmpty(), "Should return recommendations");

        RecommendationResult top = results.get(0);
        assertTrue(top.getScore() > 0, "Top match should have positive score");
        assertNotNull(top.getMatchReason(), "Should have match reason");
        assertTrue(top.getMatchReason().toLowerCase().contains("matches you"), "Reason should contain match text");
    }

    @Test
    void testCategoryFilteringInRecommendation() {
        QuizSubmission sub = new QuizSubmission(
            "dry",
            Arrays.asList("dryness"),
            "30-39",
            "yes",
            "medium",
            "cleanser"
        );

        List<RecommendationResult> results = service.getRecommendations(sub);
        assertFalse(results.isEmpty());
        for (RecommendationResult res : results) {
            assertEquals("Cleanser", res.getProduct().getCategory());
        }
    }

    @Test
    void testEmptySubmission() {
        List<RecommendationResult> results = service.getRecommendations(null);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
