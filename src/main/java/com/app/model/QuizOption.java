package com.app.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * An option in a quiz question.
 */
@Data
public class QuizOption implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String value;
    private String label;
    private String description;

    public QuizOption() {
    }

    public QuizOption(String value, String label) {
        this(value, label, "");
    }

    public QuizOption(String value, String label, String description) {
        this.value = value;
        this.label = label;
        this.description = description;
    }

}
