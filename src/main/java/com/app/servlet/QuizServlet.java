package com.app.servlet;

import com.app.data.QuizData;
import com.app.model.QuizSubmission;
import com.app.model.RecommendationResult;
import com.app.service.RecommendationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller for taking the skincare quiz and generating scored recommendations.
 */
@WebServlet(name = "QuizServlet", urlPatterns = {"/quiz"})
public class QuizServlet extends HttpServlet {

    private final RecommendationService recommendationService = new RecommendationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("questions", QuizData.getQuestions());
        request.getRequestDispatcher("/quiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String skinType = request.getParameter("skinType");
        String[] concernsArray = request.getParameterValues("concerns");
        String ageGroup = request.getParameter("ageGroup");
        String sensitiveAllergies = request.getParameter("sensitiveAllergies");
        String budget = request.getParameter("budget");
        String categoryWanted = request.getParameter("categoryWanted");

        List<String> concerns = (concernsArray != null) ? Arrays.asList(concernsArray) : new ArrayList<>();

        // Server-side validation
        List<String> validationErrors = new ArrayList<>();

        if (!QuizData.isValidSkinType(skinType)) {
            validationErrors.add("Please select a valid skin type.");
        }

        if (!QuizData.isValidAgeGroup(ageGroup)) {
            validationErrors.add("Please select a valid age group.");
        }

        if (!QuizData.isValidAllergy(sensitiveAllergies)) {
            validationErrors.add("Please specify whether you have sensitive skin or allergies.");
        }

        if (!QuizData.isValidBudget(budget)) {
            validationErrors.add("Please select your target budget tier.");
        }

        if (!QuizData.isValidCategoryWanted(categoryWanted)) {
            validationErrors.add("Please select a preferred product category or 'Any'.");
        }

        for (String c : concerns) {
            if (!QuizData.isValidConcern(c)) {
                validationErrors.add("One or more selected skin concerns is unrecognized.");
                break;
            }
        }

        if (!validationErrors.isEmpty()) {
            request.setAttribute("validationErrors", validationErrors);
            request.setAttribute("submittedSkinType", skinType);
            request.setAttribute("submittedConcerns", concerns);
            request.setAttribute("submittedAgeGroup", ageGroup);
            request.setAttribute("submittedSensitiveAllergies", sensitiveAllergies);
            request.setAttribute("submittedBudget", budget);
            request.setAttribute("submittedCategoryWanted", categoryWanted);
            request.setAttribute("questions", QuizData.getQuestions());
            request.getRequestDispatcher("/quiz.jsp").forward(request, response);
            return;
        }

        QuizSubmission submission = new QuizSubmission(
            skinType,
            concerns,
            ageGroup,
            sensitiveAllergies,
            budget,
            categoryWanted
        );

        List<RecommendationResult> recommendations = recommendationService.getRecommendations(submission);

        request.setAttribute("submission", submission);
        request.setAttribute("recommendations", recommendations);
        request.setAttribute("disclaimer", RecommendationService.MEDICAL_DISCLAIMER);

        request.getRequestDispatcher("/quiz-results.jsp").forward(request, response);
    }
}
