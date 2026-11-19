package com.app.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Stores a recommended product with its relevance score and human-readable explanation.
 */
@Getter
@Setter
public class RecommendationResult implements Serializable {
    @Serial
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
}
