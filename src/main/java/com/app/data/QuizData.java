package com.app.data;

// EDIT HERE: Quiz questions and options definitions. Update questions, labels, or available answers below.

import com.app.model.QuizOption;
import com.app.model.QuizQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuizData {

    private static final List<QuizQuestion> QUESTIONS = new ArrayList<>();

    private static final Set<String> VALID_SKIN_TYPES = new HashSet<>(
        Arrays.asList("oily", "dry", "combination", "normal", "sensitive")
    );

    private static final Set<String> VALID_CONCERNS = new HashSet<>(
        Arrays.asList("acne", "dark spots", "wrinkles", "dullness", "dryness", "redness")
    );

    private static final Set<String> VALID_AGE_GROUPS = new HashSet<>(
        Arrays.asList("under-20", "20-29", "30-39", "40-49", "50+")
    );

    private static final Set<String> VALID_ALLERGIES = new HashSet<>(
        Arrays.asList("yes", "no")
    );

    private static final Set<String> VALID_BUDGETS = new HashSet<>(
        Arrays.asList("low", "medium", "high")
    );

    private static final Set<String> VALID_CATEGORIES = new HashSet<>(
        Arrays.asList("cleanser", "moisturizer", "serum", "sunscreen", "makeup", "any")
    );

    static {
        // Question 1: Skin Type
        QUESTIONS.add(new QuizQuestion(
            "skinType",
            "What is your skin type?",
            "Select the description that best fits how your skin feels throughout the day.",
            false,
            Arrays.asList(
                new QuizOption("oily", "Oily", "Excess shine, visible pores, prone to breakouts"),
                new QuizOption("dry", "Dry", "Tight, flaky, rough or lacks natural moisture"),
                new QuizOption("combination", "Combination", "Oily T-zone (forehead, nose, chin) and normal/dry cheeks"),
                new QuizOption("normal", "Normal", "Well-balanced, neither overly oily nor dry"),
                new QuizOption("sensitive", "Sensitive", "Easily irritated, prone to stinging or redness")
            )
        ));

        // Question 2: Main Concern (multi-select)
        QUESTIONS.add(new QuizQuestion(
            "concerns",
            "What are your main skin concerns?",
            "Choose all that apply so we can target active ingredients effectively.",
            true,
            Arrays.asList(
                new QuizOption("acne", "Acne & Blemishes", "Blackheads, whiteheads, or active breakouts"),
                new QuizOption("dark spots", "Dark Spots & Uneven Tone", "Hyperpigmentation or post-breakout marks"),
                new QuizOption("wrinkles", "Fine Lines & Wrinkles", "Loss of firmness, elasticity, or smile lines"),
                new QuizOption("dullness", "Dullness & Lack of Glow", "Tired-looking skin needing radiance"),
                new QuizOption("dryness", "Dehydration & Dryness", "Rough texture and barrier damage"),
                new QuizOption("redness", "Redness & Irritation", "Flushing, rosacea symptoms, or sensitivity")
            )
        ));

        // Question 3: Age Group
        QUESTIONS.add(new QuizQuestion(
            "ageGroup",
            "Which age group are you in?",
            "Skin renewal rates and cellular collagen change across different stages of life.",
            false,
            Arrays.asList(
                new QuizOption("under-20", "Under 20", "Focus on gentle balancing and breakout control"),
                new QuizOption("20-29", "20 - 29", "Prevention, hydration, and environmental defense"),
                new QuizOption("30-39", "30 - 39", "First signs of aging, firmness, and barrier repair"),
                new QuizOption("40-49", "40 - 49", "Deep cellular regeneration and targeted treatments"),
                new QuizOption("50+", "50+", "Intense nourishment, lipid replenishment, and density")
            )
        ));

        // Question 4: Allergies or Sensitivities
        QUESTIONS.add(new QuizQuestion(
            "sensitiveAllergies",
            "Do you have sensitive skin or known ingredient allergies?",
            "We will prioritize hypoallergenic and fragrance-free formulations.",
            false,
            Arrays.asList(
                new QuizOption("yes", "Yes, sensitive / allergy-prone", "I react easily to strong fragrances or harsh actives"),
                new QuizOption("no", "No known sensitivities", "My skin generally tolerates standard skincare formulations well")
            )
        ));

        // Question 5: Budget
        QUESTIONS.add(new QuizQuestion(
            "budget",
            "What is your target budget level?",
            "We match products with verified retailer deals across all price tiers.",
            false,
            Arrays.asList(
                new QuizOption("low", "Budget Friendly (Under $20)", "High-value, accessible staples"),
                new QuizOption("medium", "Mid-Range ($20 - $35)", "Proven derm-grade active formulations"),
                new QuizOption("high", "Premium / Luxury ($35+)", "High-concentration clinical & luxury botanicals")
            )
        ));

        // Question 6: Category Wanted
        QUESTIONS.add(new QuizQuestion(
            "categoryWanted",
            "What type of product are you looking for right now?",
            "Choose a specific category or select 'Any' for a comprehensive routine match.",
            false,
            Arrays.asList(
                new QuizOption("any", "Any Category", "Show me the highest-scoring recommendations across all steps"),
                new QuizOption("cleanser", "Cleanser", "Daily facial wash or clarifying cleanser"),
                new QuizOption("moisturizer", "Moisturizer", "Day/night hydrating cream, gel, or lotion"),
                new QuizOption("serum", "Serum", "Targeted active treatment or antioxidant booster"),
                new QuizOption("sunscreen", "Sunscreen", "Daily broad-spectrum UV protection"),
                new QuizOption("makeup", "Makeup", "Skin-caring complexion & hybrid cosmetics")
            )
        ));
    }

    public static List<QuizQuestion> getQuestions() {
        return Collections.unmodifiableList(QUESTIONS);
    }

    public static boolean isValidSkinType(String value) {
        return value != null && VALID_SKIN_TYPES.contains(value.trim().toLowerCase());
    }

    public static boolean isValidConcern(String value) {
        return value != null && VALID_CONCERNS.contains(value.trim().toLowerCase());
    }

    public static boolean isValidAgeGroup(String value) {
        return value != null && VALID_AGE_GROUPS.contains(value.trim().toLowerCase());
    }

    public static boolean isValidAllergy(String value) {
        return value != null && VALID_ALLERGIES.contains(value.trim().toLowerCase());
    }

    public static boolean isValidBudget(String value) {
        return value != null && VALID_BUDGETS.contains(value.trim().toLowerCase());
    }

    public static boolean isValidCategoryWanted(String value) {
        return value != null && VALID_CATEGORIES.contains(value.trim().toLowerCase());
    }
}
