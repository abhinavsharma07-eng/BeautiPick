package com.app;

import com.app.model.QuizSubmission;
import com.app.model.RecommendationResult;
import com.app.service.RecommendationService;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecommendationServiceTest extends TestCase {

    private RecommendationService service;

    protected void setUp() throws Exception {
        super.setUp();
        service = new RecommendationService();
    }

    public void testRecommendationsForOilyAcneSkin() {
        QuizSubmission sub = new QuizSubmission(
            "oily",
            Arrays.asList("acne"),
            "20-29",
            "no",
            "medium",
            "any"
        );

        List<RecommendationResult> results = service.getRecommendations(sub);
        assertFalse("Should return recommendations", results.isEmpty());

        RecommendationResult top = results.get(0);
        assertTrue("Top match should have positive score", top.getScore() > 0);
        assertNotNull("Should have match reason", top.getMatchReason());
        assertTrue("Reason should contain match text", top.getMatchReason().toLowerCase().contains("matches you"));
    }

    public void testCategoryFilteringInRecommendation() {
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

    public void testEmptySubmission() {
        List<RecommendationResult> results = service.getRecommendations(null);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
