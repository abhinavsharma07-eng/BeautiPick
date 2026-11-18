package com.app.service;

import com.app.data.ProductData;
import com.app.model.Product;
import com.app.model.QuizSubmission;
import com.app.model.RecommendationResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Encapsulates the recommendation engine scoring algorithm.
 */
public class RecommendationService {

    public static final String MEDICAL_DISCLAIMER =
        "These are general suggestions, not medical advice. See a dermatologist for skin conditions.";

    public List<RecommendationResult> getRecommendations(QuizSubmission submission) {
        List<RecommendationResult> results = new ArrayList<>();
        if (submission == null) {
            return results;
        }

        List<Product> allProducts = ProductData.getAllProducts();
        String skinType = (submission.getSkinType() != null) ? submission.getSkinType().trim().toLowerCase(Locale.ROOT) : "";
        List<String> concerns = (submission.getConcerns() != null) ? submission.getConcerns() : new ArrayList<>();
        String budget = (submission.getBudget() != null) ? submission.getBudget().trim().toLowerCase(Locale.ROOT) : "";
        String catWanted = (submission.getCategoryWanted() != null) ? submission.getCategoryWanted().trim().toLowerCase(Locale.ROOT) : "any";
        String allergies = (submission.getSensitiveAllergies() != null) ? submission.getSensitiveAllergies().trim().toLowerCase(Locale.ROOT) : "no";
        String ageGroup = (submission.getAgeGroup() != null) ? submission.getAgeGroup().trim().toLowerCase(Locale.ROOT) : "";

        for (Product product : allProducts) {
            // If user specified a distinct category (other than "any"), exclude other categories
            if (!catWanted.isEmpty() && !catWanted.equalsIgnoreCase("any")) {
                if (!product.getCategory().equalsIgnoreCase(catWanted)) {
                    continue;
                }
            }

            int score = 0;
            List<String> matchedReasons = new ArrayList<>();

            // 1. Skin Type Match
            if (!skinType.isEmpty() && product.getSkinTypes() != null) {
                boolean matchesSkin = product.getSkinTypes().stream()
                    .anyMatch(st -> st.equalsIgnoreCase(skinType));
                if (matchesSkin) {
                    score += 3;
                    matchedReasons.add("ideal for " + skinType + " skin");
                }
            }

            // 2. Concerns Match
            List<String> matchedConcerns = new ArrayList<>();
            if (product.getConcerns() != null) {
                for (String c : concerns) {
                    String clean = c.trim().toLowerCase(Locale.ROOT);
                    if (product.getConcerns().stream().anyMatch(pc -> pc.equalsIgnoreCase(clean))) {
                        matchedConcerns.add(clean);
                        score += 2;
                    }
                }
            }
            if (!matchedConcerns.isEmpty()) {
                matchedReasons.add("targets " + String.join(" & ", matchedConcerns));
            }

            // 3. Category Match (if specific)
            if (!catWanted.isEmpty() && !catWanted.equalsIgnoreCase("any") && product.getCategory().equalsIgnoreCase(catWanted)) {
                score += 3;
                matchedReasons.add("matches your requested " + product.getCategory().toLowerCase(Locale.ROOT) + " routine");
            }

            // 4. Budget Match
            if (!budget.isEmpty() && product.getBudgetLevel() != null && product.getBudgetLevel().equalsIgnoreCase(budget)) {
                score += 2;
                matchedReasons.add("fits your " + budget + " budget tier");
            }

            // 5. Sensitivity & Allergy Alignment
            if ("yes".equalsIgnoreCase(allergies)) {
                if (product.isSuitableForSensitive()) {
                    score += 2;
                    matchedReasons.add("hypoallergenic & gentle for sensitive skin");
                } else {
                    score -= 3;
                }
            }

            // 6. Age Group Match
            if (!ageGroup.isEmpty() && product.getAgeGroups() != null) {
                boolean matchesAge = product.getAgeGroups().stream().anyMatch(ag -> ag.equalsIgnoreCase(ageGroup));
                if (matchesAge) {
                    score += 1;
                }
            }

            // Only recommend if there is a positive match score
            if (score > 0) {
                String reasonLine;
                if (!matchedReasons.isEmpty()) {
                    reasonLine = "Why this matches you: " + capitalizeFirst(String.join(", ", matchedReasons)) + ".";
                } else {
                    reasonLine = "Why this matches you: Quality formula compatible with your personal profile.";
                }
                results.add(new RecommendationResult(product, score, reasonLine));
            }
        }

        // Sort descending by score; if tied, sort ascending by lowest price
        results.sort(Comparator.comparingInt(RecommendationResult::getScore).reversed()
            .thenComparingDouble(r -> r.getProduct().getLowestPrice()));

        return results;
    }

    private String capitalizeFirst(String text) {
        if (text == null || text.isEmpty()) return text;
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}
