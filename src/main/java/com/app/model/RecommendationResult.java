package com.app.model;

import java.io.Serializable;

/**
 * Stores a recommended product with its relevance score and human-readable explanation.
 */
public class RecommendationResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private Product product;
    private int score;
    private String matchReason;

    public RecommendationResult() {
    }

    public RecommendationResult(Product product, int score, String matchReason) {
        this.product = product;
        this.score = score;
        this.matchReason = matchReason;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMatchReason() {
        return matchReason;
    }

    public void setMatchReason(String matchReason) {
        this.matchReason = matchReason;
    }
}
