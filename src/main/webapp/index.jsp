<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty products}">
  <c:set var="products" value="<%= com.app.data.ProductData.getAllProducts() %>" scope="request" />
</c:if>
<c:if test="${empty categories}">
  <c:set var="categories" value="<%= com.app.data.ProductData.getCategories() %>" scope="request" />
</c:if>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>BeautiPick - Skincare Price Comparison &amp; Recommendations</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

  <%-- Shared Header with top-left Get Recommendations button --%>
  <%@ include file="/WEB-INF/fragments/header.jspf" %>

  <main>
    <%-- Hero Section --%>
    <section class="hero-section">
      <div class="container hero-content">
        <span class="hero-badge">Smart Skincare Shopping</span>
        <h1 class="hero-headline">
          Discover your best skin with proven formulas and genuine price comparisons across top beauty retailers.
        </h1>
        <p class="hero-subtext">
          Compare real-time prices across trusted sites or get a custom routine curated for your skin profile.
        </p>
        <div class="hero-actions">
          <a href="${pageContext.request.contextPath}/products" class="btn btn-primary btn-large">
            Browse &amp; Buy Products
          </a>
          <a href="${pageContext.request.contextPath}/quiz" class="btn btn-secondary-outline btn-large">
            Take Skin Quiz
          </a>
        </div>
      </div>
    </section>

    <%-- Available Products Showcase --%>
    <section class="showcase-section container">
      <div class="section-header">
        <div>
          <h2 class="section-title">Available Products</h2>
          <p class="section-subtitle">Real-time pricing from verified beauty stockists. Updated daily.</p>
        </div>
      </div>

      <%-- Category Filter Chips --%>
      <div class="category-chips-bar" id="categoryChips">
        <c:forEach var="cat" items="${categories}">
          <button type="button"
                  class="chip ${cat eq 'All' ? 'active' : ''}"
                  data-filter="<c:out value="${cat}"/>">
            <c:out value="${cat}"/>
          </button>
        </c:forEach>
      </div>

      <%-- Product Grid --%>
      <div class="product-grid" id="productGrid">
        <c:forEach var="product" items="${products}">
          <%@ include file="/WEB-INF/fragments/product-card.jspf" %>
        </c:forEach>
      </div>
    </section>
  </main>

  <%-- Shared Footer --%>
  <%@ include file="/WEB-INF/fragments/footer.jspf" %>

  <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
