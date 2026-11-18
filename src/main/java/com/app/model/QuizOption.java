package com.app.model;

import java.io.Serializable;

/**
 * An option in a quiz question.
 */
public class QuizOption implements Serializable {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
