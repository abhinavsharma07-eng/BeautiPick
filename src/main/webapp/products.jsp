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
  <title>Browse Products &amp; Compare Prices - BeautiPick</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1.1">
</head>
<body>

  <%-- Shared Header --%>
  <%@ include file="/WEB-INF/fragments/header.jspf" %>

  <main class="page-main container">
    <div class="page-header">
      <div class="breadcrumb">
        <a href="${pageContext.request.contextPath}/home">Home</a> &rsaquo; <span>Browse Products</span>
      </div>
      <h1 class="page-title">Browse &amp; Compare Prices</h1>
      <p class="page-description">Find the lowest prices on top skincare essentials across major beauty retailers.</p>
    </div>

    <%-- Search & Category Filter Form --%>
    <form action="${pageContext.request.contextPath}/products" method="GET" class="filter-toolbar">
      <div class="search-box">
        <input type="text"
               name="query"
               class="search-input"
               placeholder="Search by product name, brand, or ingredients..."
               value="<c:out value="${searchQuery}"/>" />
        <button type="submit" class="btn btn-search">Search</button>
      </div>

      <div class="filter-group">
        <label for="categorySelect" class="filter-label">Category:</label>
        <select name="category" id="categorySelect" class="filter-select" onchange="this.form.submit()">
          <c:forEach var="cat" items="${categories}">
            <option value="<c:out value="${cat}"/>" ${selectedCategory eq cat ? 'selected' : ''}>
              <c:out value="${cat}"/>
            </option>
          </c:forEach>
        </select>
        <c:if test="${not empty searchQuery or (not empty selectedCategory and selectedCategory ne 'All')}">
          <a href="${pageContext.request.contextPath}/products" class="btn-clear-filter">Reset Filters</a>
        </c:if>
      </div>
    </form>

    <%-- Product Results Grid --%>
    <c:choose>
      <c:when test="${not empty products}">
        <div class="results-meta">
          <span>Showing <c:out value="${products.size()}"/> products</span>
        </div>
        <div class="product-grid">
          <c:forEach var="catalogProduct" items="${products}">
            <c:set var="cardProduct" value="${catalogProduct}" />
            <%@ include file="/WEB-INF/fragments/product-card.jspf" %>
          </c:forEach>
        </div>
      </c:when>
      <c:otherwise>
        <div class="empty-state">
          <div class="empty-icon">&#128269;</div>
          <h3>No products match your criteria</h3>
          <p>Try adjusting your search terms or selecting a different category.</p>
          <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">View All Products</a>
        </div>
      </c:otherwise>
    </c:choose>
  </main>

  <%-- Shared Footer --%>
  <%@ include file="/WEB-INF/fragments/footer.jspf" %>

  <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
