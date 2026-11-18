package com.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single question in the skincare quiz.
 */
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public List<QuizOption> getOptions() {
        return options;
    }

    public void setOptions(List<QuizOption> options) {
        this.options = options;
    }
}
