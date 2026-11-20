<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Your Personalized Recommendations - BeautiPick</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1.1">
</head>
<body>

  <%-- Shared Header --%>
  <%@ include file="/WEB-INF/fragments/header.jspf" %>

  <main class="page-main container">
    <div class="breadcrumb">
      <a href="${pageContext.request.contextPath}/home">Home</a> &rsaquo;
      <a href="${pageContext.request.contextPath}/quiz">Quiz</a> &rsaquo;
      <span>Your Results</span>
    </div>

    <%-- Hero Banner with Medical Disclaimer --%>
    <div class="results-header-banner">
      <div class="results-badge">&#10024; Tailored Skincare Matches</div>
      <h1 class="results-title">Your Personalized Recommendations</h1>
      <p class="results-subtitle">
        Scored against your skin type, primary concerns, and preferences to maximize routine compatibility.
      </p>

      <%-- Mandatory One-line medical disclaimer --%>
      <div class="medical-notice-bar">
        <span class="notice-icon">&#9888;</span>
        <span class="notice-text">
          <c:out value="${disclaimer}"/>
        </span>
      </div>
    </div>

    <%-- Summary of User's Quiz Profile --%>
    <c:if test="${not empty submission}">
      <div class="profile-summary-box">
        <h3 class="profile-summary-title">Your Profile Profile Summary:</h3>
        <div class="profile-tags-row">
          <span class="profile-tag"><strong>Skin Type:</strong> <c:out value="${submission.skinType}"/></span>
          <span class="profile-tag"><strong>Age:</strong> <c:out value="${submission.ageGroup}"/></span>
          <span class="profile-tag"><strong>Budget:</strong> <c:out value="${submission.budget}"/></span>
          <span class="profile-tag"><strong>Looking For:</strong> <c:out value="${submission.categoryWanted}"/></span>
          <c:if test="${not empty submission.concerns}">
            <span class="profile-tag"><strong>Concerns:</strong>
              <c:forEach var="c" items="${submission.concerns}" varStatus="cs">
                <c:out value="${c}"/><c:if test="${!cs.last}">, </c:if>
              </c:forEach>
            </span>
          </c:if>
        </div>
      </div>
    </c:if>

    <%-- Recommendations List or Empty State --%>
    <c:choose>
      <c:when test="${not empty recommendations}">
        <div class="results-grid-header">
          <h2>Top Ranked Matches (<c:out value="${recommendations.size()}"/> items found)</h2>
          <a href="${pageContext.request.contextPath}/quiz" class="btn btn-secondary-outline btn-sm">Retake Quiz</a>
        </div>

        <div class="product-grid">
          <c:forEach var="rec" items="${recommendations}">
            <%-- Uses the shared product-card fragment, which displays rec.product and rec.matchReason --%>
            <%@ include file="/WEB-INF/fragments/product-card.jspf" %>
          </c:forEach>
        </div>
      </c:when>

      <c:otherwise>
        <div class="empty-state empty-results-card">
          <div class="empty-icon">&#129300;</div>
          <h2>No matches, try different answers</h2>
          <p>
            None of our current products matched your exact filter combination.
            Try selecting a broader category like "Any" or modifying your concern selection.
          </p>
          <div class="empty-actions">
            <a href="${pageContext.request.contextPath}/quiz" class="btn btn-primary">Retake Quiz</a>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-secondary-outline">Browse All Products</a>
          </div>
        </div>
      </c:otherwise>
    </c:choose>
  </main>

  <%-- Shared Footer --%>
  <%@ include file="/WEB-INF/fragments/footer.jspf" %>

  <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
