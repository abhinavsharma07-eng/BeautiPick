package com.app;

import com.app.data.QuizData;
import com.app.model.QuizQuestion;
import junit.framework.TestCase;

import java.util.List;

public class QuizDataTest extends TestCase {

    public void testQuestionsCount() {
        List<QuizQuestion> questions = QuizData.getQuestions();
        assertEquals(6, questions.size());
    }

    public void testValidations() {
        assertTrue(QuizData.isValidSkinType("oily"));
        assertTrue(QuizData.isValidSkinType("dry"));
        assertTrue(QuizData.isValidSkinType("combination"));
        assertTrue(QuizData.isValidSkinType("normal"));
        assertTrue(QuizData.isValidSkinType("sensitive"));
        assertFalse(QuizData.isValidSkinType("unknown-type"));

        assertTrue(QuizData.isValidConcern("acne"));
        assertTrue(QuizData.isValidConcern("wrinkles"));
        assertFalse(QuizData.isValidConcern("invalid-concern"));

        assertTrue(QuizData.isValidBudget("low"));
        assertTrue(QuizData.isValidBudget("medium"));
        assertTrue(QuizData.isValidBudget("high"));
        assertFalse(QuizData.isValidBudget("super-expensive"));

        assertTrue(QuizData.isValidAllergy("yes"));
        assertTrue(QuizData.isValidAllergy("no"));
        assertFalse(QuizData.isValidAllergy("maybe"));

        assertTrue(QuizData.isValidCategoryWanted("any"));
        assertTrue(QuizData.isValidCategoryWanted("cleanser"));
        assertTrue(QuizData.isValidCategoryWanted("serum"));
        assertFalse(QuizData.isValidCategoryWanted("shoes"));
    }
}
