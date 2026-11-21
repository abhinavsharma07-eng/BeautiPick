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
  <%-- item 23: favicon using ✿ SVG data URI --%>
  <link rel="icon" type="image/svg+xml" href="data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'><text y='.9em' font-size='90'>&#10047;</text></svg>">
  <%-- item 25: bump CSS version to 1.2 --%>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1.2">
</head>
<body>

  <%-- Shared Header with top-left Get Recommendations button --%>
  <%@ include file="/WEB-INF/fragments/header.jspf" %>

  <main>
    <%-- Hero Section --%>
    <section class="hero-section">
      <div class="container hero-content">
        <span class="hero-badge">Smart Skincare Shopping</span>
        <%-- item 15: shortened hero-headline (≤60 chars); rest moved to hero-subtext --%>
        <h1 class="hero-headline">
          Your best skin, at the best price.
        </h1>
        <p class="hero-subtext">
          Discover proven formulas with genuine price comparisons across top beauty retailers.
          Compare prices or get a custom routine curated for your skin profile — updated daily.
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
          <%-- item 15: removed contradictory "Real-time pricing" claim; use only "Updated daily" --%>
          <p class="section-subtitle">Prices from verified beauty stockists — updated daily.</p>
        </div>
        <a href="${pageContext.request.contextPath}/products" class="section-header-link">
          View All Products &rarr;
        </a>
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
        <c:forEach var="showcaseProduct" items="${products}" end="7">
          <c:set var="cardProduct" value="${showcaseProduct}" />
          <%@ include file="/WEB-INF/fragments/product-card.jspf" %>
        </c:forEach>
      </div>
      <%-- item 14: empty-category message (shown via JS when filter returns 0 cards) --%>
      <p id="emptyCategoryMsg" class="empty-category-msg" role="status" aria-live="polite">
        No products in this category yet.
      </p>

      <%-- item 24: removed inline styles — all styles live in CSS classes --%>
      <div class="showcase-cta">
        <a href="${pageContext.request.contextPath}/products"
           class="btn btn-primary btn-large btn-browse-all">
          Browse All Products &rsaquo;
        </a>
      </div>
    </section>
  </main>

  <%-- Shared Footer --%>
  <%@ include file="/WEB-INF/fragments/footer.jspf" %>

  <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
