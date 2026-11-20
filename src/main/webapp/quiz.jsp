<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty questions}">
  <c:set var="questions" value="<%= com.app.data.QuizData.getQuestions() %>" scope="request" />
</c:if>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Skin Care Quiz &amp; Recommendations - BeautiPick</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1.1">
</head>
<body>

  <%-- Shared Header --%>
  <%@ include file="/WEB-INF/fragments/header.jspf" %>

  <main class="page-main container">
    <div class="quiz-container">
      <div class="quiz-header-banner">
        <span class="quiz-pill-badge">&#10024; Personalized Routine Engine</span>
        <h1 class="quiz-title">Find Your Perfect Skincare Match</h1>
        <p class="quiz-subtitle">Answer 6 quick questions to receive science-backed product recommendations scored specifically for your skin.</p>
      </div>

      <%-- Validation errors alert --%>
      <c:if test="${not empty validationErrors}">
        <div class="alert-box alert-danger">
          <strong>Please address the following before submitting:</strong>
          <ul>
            <c:forEach var="err" items="${validationErrors}">
              <li><c:out value="${err}"/></li>
            </c:forEach>
          </ul>
        </div>
      </c:if>

      <%-- Multi-step Progress Bar --%>
      <div class="quiz-progress-wrap">
        <div class="progress-info">
          <span id="stepCounter" class="step-counter">Step 1 of <c:out value="${questions.size()}"/></span>
          <span id="stepPercent" class="step-percent">16% Complete</span>
        </div>
        <div class="progress-bar-track">
          <div id="progressBarFill" class="progress-bar-fill" style="width: 16.66%;"></div>
        </div>
      </div>

      <%-- Quiz Form --%>
      <form id="quizForm" action="${pageContext.request.contextPath}/quiz" method="POST" class="quiz-card-form">
        <c:forEach var="q" items="${questions}" varStatus="status">
          <div class="quiz-step ${status.first ? 'active-step' : ''}" data-step="<c:out value="${status.index + 1}"/>">
            <div class="step-question-header">
              <span class="question-number">Question <c:out value="${status.index + 1}"/></span>
              <h2 class="question-title"><c:out value="${q.title}"/></h2>
              <c:if test="${not empty q.subtitle}">
                <p class="question-subtitle"><c:out value="${q.subtitle}"/></p>
              </c:if>
            </div>

            <div class="options-grid ${q.multiSelect ? 'multi-select' : 'single-select'}">
              <c:forEach var="opt" items="${q.options}">
                <label class="option-card">
                  <c:choose>
                    <c:when test="${q.multiSelect}">
                      <input type="checkbox"
                             name="<c:out value="${q.id}"/>"
                             value="<c:out value="${opt.value}"/>"
                             class="quiz-input checkbox-input" />
                    </c:when>
                    <c:otherwise>
                      <input type="radio"
                             name="<c:out value="${q.id}"/>"
                             value="<c:out value="${opt.value}"/>"
                             class="quiz-input radio-input" />
                    </c:otherwise>
                  </c:choose>
                  <div class="option-indicator">
                    <span class="check-icon">&#10003;</span>
                  </div>
                  <div class="option-content">
                    <span class="option-label"><c:out value="${opt.label}"/></span>
                    <c:if test="${not empty opt.description}">
                      <span class="option-desc"><c:out value="${opt.description}"/></span>
                    </c:if>
                  </div>
                </label>
              </c:forEach>
            </div>

            <%-- Step Navigation Buttons --%>
            <div class="step-nav-bar">
              <c:if test="${not status.first}">
                <button type="button" class="btn btn-step-prev" onclick="goToPrevStep()">
                  &larr; Back
                </button>
              </c:if>
              <c:if test="${status.first}">
                <div></div> <%-- Spacer to keep Next on the right --%>
              </c:if>

              <c:choose>
                <c:when test="${status.last}">
                  <button type="submit" class="btn btn-primary btn-submit-quiz">
                    Get Recommendations &rsaquo;
                  </button>
                </c:when>
                <c:otherwise>
                  <button type="button" class="btn btn-primary btn-step-next" onclick="goToNextStep()">
                    Next Question &rarr;
                  </button>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </c:forEach>
      </form>

      <div class="quiz-disclaimer-card">
        <span class="info-icon">&#9432;</span>
        <p><strong>Notice:</strong> These are general suggestions, not medical advice. See a dermatologist for skin conditions.</p>
      </div>
    </div>
  </main>

  <%-- Shared Footer --%>
  <%@ include file="/WEB-INF/fragments/footer.jspf" %>

  <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
