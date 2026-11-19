package com.app.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the user's validated answers to the skincare quiz.
 */
@Setter
@Getter
public class QuizSubmission implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String skinType;
    private List<String> concerns = new ArrayList<>();
    private String ageGroup;
    private String sensitiveAllergies; // "yes" or "no"
    private String budget;             // "low", "medium", "high"
    private String categoryWanted;     // "cleanser", "moisturizer", "serum", "sunscreen", "makeup", "any"

    public QuizSubmission() {
    }

    public QuizSubmission(String skinType, List<String> concerns, String ageGroup,
                          String sensitiveAllergies, String budget, String categoryWanted) {
        this.skinType = skinType;
        if (concerns != null) {
            this.concerns = new ArrayList<>(concerns);
        }
        this.ageGroup = ageGroup;
        this.sensitiveAllergies = sensitiveAllergies;
        this.budget = budget;
        this.categoryWanted = categoryWanted;
    }
}
