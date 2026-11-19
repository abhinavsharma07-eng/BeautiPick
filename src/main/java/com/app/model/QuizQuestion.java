package com.app.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single question in the skincare quiz.
 */
@Getter
@Setter
public class QuizQuestion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String subtitle;
    private boolean multiSelect;
    private List<QuizOption> options = new ArrayList<>();

    public QuizQuestion() {
    }

    public QuizQuestion(String id, String title, String subtitle, boolean multiSelect, List<QuizOption> options) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.multiSelect = multiSelect;
        if (options != null) {
            this.options = new ArrayList<>(options);
        }
    }
}
