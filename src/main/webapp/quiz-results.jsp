<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Your Personalized Recommendations - BeautiPick</title>
  <%-- item 23: favicon --%>
  <link rel="icon" type="image/svg+xml" href="data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'><text y='.9em' font-size='90'>&#10047;</text></svg>">
  <%-- item 25: bump to v=1.2 --%>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1.2">
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

      <%-- Mandatory One-line medical disclaimer (item 9: border-radius + text-align left) --%>
      <div class="medical-notice-bar">
        <span class="notice-icon">&#9888;</span>
        <span class="notice-text">
          <c:out value="${disclaimer}"/>
        </span>
      </div>
    </div>

    <%-- Summary of User's Quiz Profile --%>
    <c:if test="${not empty submission}">
      <%--
        item 17: human-readable label mapping (display-only, raw values unchanged).
        Mapping is done here in JSP with c:choose. QuizData.java is NOT modified.
      --%>
      <c:set var="rawSkinType" value="${submission.skinType}" />
      <c:choose>
        <c:when test="${rawSkinType eq 'oily'}"><c:set var="labelSkinType" value="Oily" /></c:when>
        <c:when test="${rawSkinType eq 'dry'}"><c:set var="labelSkinType" value="Dry" /></c:when>
        <c:when test="${rawSkinType eq 'combination'}"><c:set var="labelSkinType" value="Combination" /></c:when>
        <c:when test="${rawSkinType eq 'sensitive'}"><c:set var="labelSkinType" value="Sensitive" /></c:when>
        <c:when test="${rawSkinType eq 'normal'}"><c:set var="labelSkinType" value="Normal" /></c:when>
        <c:otherwise><c:set var="labelSkinType" value="${rawSkinType}" /></c:otherwise>
      </c:choose>

      <c:set var="rawAge" value="${submission.ageGroup}" />
      <c:choose>
        <c:when test="${rawAge eq 'under-20'}"><c:set var="labelAge" value="Under 20" /></c:when>
        <c:when test="${rawAge eq '20-30'}"><c:set var="labelAge" value="20–30" /></c:when>
        <c:when test="${rawAge eq '30-40'}"><c:set var="labelAge" value="30–40" /></c:when>
        <c:when test="${rawAge eq '40-50'}"><c:set var="labelAge" value="40–50" /></c:when>
        <c:when test="${rawAge eq '50+'}"><c:set var="labelAge" value="50 &amp; above" /></c:when>
        <c:otherwise><c:set var="labelAge" value="${rawAge}" /></c:otherwise>
      </c:choose>

      <c:set var="rawBudget" value="${submission.budget}" />
      <c:choose>
        <c:when test="${rawBudget eq 'low'}"><c:set var="labelBudget" value="Budget-friendly" /></c:when>
        <c:when test="${rawBudget eq 'medium'}"><c:set var="labelBudget" value="Mid-range" /></c:when>
        <c:when test="${rawBudget eq 'high'}"><c:set var="labelBudget" value="Premium" /></c:when>
        <c:otherwise><c:set var="labelBudget" value="${rawBudget}" /></c:otherwise>
      </c:choose>

      <c:set var="rawCategory" value="${submission.categoryWanted}" />
      <c:choose>
        <c:when test="${rawCategory eq 'any'}"><c:set var="labelCategory" value="Any Category" /></c:when>
        <c:when test="${rawCategory eq 'cleanser'}"><c:set var="labelCategory" value="Cleanser" /></c:when>
        <c:when test="${rawCategory eq 'moisturizer'}"><c:set var="labelCategory" value="Moisturizer" /></c:when>
        <c:when test="${rawCategory eq 'serum'}"><c:set var="labelCategory" value="Serum" /></c:when>
        <c:when test="${rawCategory eq 'sunscreen'}"><c:set var="labelCategory" value="Sunscreen" /></c:when>
        <c:when test="${rawCategory eq 'makeup'}"><c:set var="labelCategory" value="Makeup" /></c:when>
        <c:otherwise><c:set var="labelCategory" value="${rawCategory}" /></c:otherwise>
      </c:choose>

      <%-- Concern label map --%>
      <c:set var="concernLabelMap" value="${{
        'dark spots': 'Dark Spots',
        'acne': 'Acne',
        'anti-aging': 'Anti-Aging',
        'hydration': 'Hydration',
        'redness': 'Redness',
        'pores': 'Pores',
        'brightening': 'Brightening'
      }}" />

      <div class="profile-summary-box">
        <%-- item 16: fixed "Profile Profile Summary:" → "Profile Summary" --%>
        <h3 class="profile-summary-title">Your Profile Summary</h3>
        <div class="profile-tags-row">
          <span class="profile-tag"><strong>Skin Type:</strong> <c:out value="${labelSkinType}"/></span>
          <span class="profile-tag"><strong>Age:</strong> <c:out value="${labelAge}"/></span>
          <span class="profile-tag"><strong>Budget:</strong> <c:out value="${labelBudget}"/></span>
          <span class="profile-tag"><strong>Looking For:</strong> <c:out value="${labelCategory}"/></span>
          <c:if test="${not empty submission.concerns}">
            <span class="profile-tag"><strong>Concerns:</strong>
              <c:forEach var="c" items="${submission.concerns}" varStatus="cs">
                <%-- item 17: map concern raw values to human labels --%>
                <c:choose>
                  <c:when test="${c eq 'dark spots'}">Dark Spots</c:when>
                  <c:when test="${c eq 'acne'}">Acne</c:when>
                  <c:when test="${c eq 'anti-aging'}">Anti-Aging</c:when>
                  <c:when test="${c eq 'hydration'}">Hydration</c:when>
                  <c:when test="${c eq 'redness'}">Redness</c:when>
                  <c:when test="${c eq 'pores'}">Pores</c:when>
                  <c:when test="${c eq 'brightening'}">Brightening</c:when>
                  <c:otherwise><c:out value="${c}"/></c:otherwise>
                </c:choose><c:if test="${!cs.last}">, </c:if>
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
