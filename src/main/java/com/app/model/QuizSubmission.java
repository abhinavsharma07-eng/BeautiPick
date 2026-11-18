package com.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the user's validated answers to the skincare quiz.
 */
public class QuizSubmission implements Serializable {
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

    public String getSkinType() {
        return skinType;
    }

    public void setSkinType(String skinType) {
        this.skinType = skinType;
    }

    public List<String> getConcerns() {
        return concerns;
    }

    public void setConcerns(List<String> concerns) {
        this.concerns = concerns;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getSensitiveAllergies() {
        return sensitiveAllergies;
    }

    public void setSensitiveAllergies(String sensitiveAllergies) {
        this.sensitiveAllergies = sensitiveAllergies;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getCategoryWanted() {
        return categoryWanted;
    }

    public void setCategoryWanted(String categoryWanted) {
        this.categoryWanted = categoryWanted;
    }
}
