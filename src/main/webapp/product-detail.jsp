<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><c:out value="${product.name}"/> - Price Comparison | BeautiPick</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

  <%-- Shared Header --%>
  <%@ include file="/WEB-INF/fragments/header.jspf" %>

  <main class="page-main container">
    <div class="breadcrumb">
      <a href="${pageContext.request.contextPath}/home">Home</a> &rsaquo;
      <a href="${pageContext.request.contextPath}/products">Products</a> &rsaquo;
      <span><c:out value="${product.name}"/></span>
    </div>

    <div class="detail-container">
      <%-- Left: Product Showcase --%>
      <div class="detail-media">
        <div class="detail-image-box">
          <c:choose>
            <c:when test="${not empty product.imageUrl and (product.imageUrl.startsWith('http://') or product.imageUrl.startsWith('https://'))}">
              <img src="<c:out value="${product.imageUrl}"/>"
                   alt="<c:out value="${product.name}"/>"
                   class="detail-image" />
            </c:when>
            <c:otherwise>
              <img src="${pageContext.request.contextPath}/<c:out value="${not empty product.imageUrl ? product.imageUrl : 'images/placeholder.svg'}"/>"
                   alt="<c:out value="${product.name}"/>"
                   class="detail-image" />
            </c:otherwise>
          </c:choose>
          <span class="detail-category-badge"><c:out value="${product.category}"/></span>
        </div>
      </div>

      <%-- Right: Product Info & Price Comparison --%>
      <div class="detail-info">
        <span class="detail-brand"><c:out value="${product.brand}"/></span>
        <h1 class="detail-title"><c:out value="${product.name}"/></h1>

        <div class="detail-price-hero">
          <span class="detail-price-label">Best Available Price:</span>
          <span class="detail-price-val"><c:out value="${product.formattedLowestPrice}"/></span>
          <span class="badge-lowest-pill">Verified Deal</span>
        </div>

        <p class="detail-desc"><c:out value="${product.description}"/></p>

        <%-- Key Tags & Skin Suitability --%>
        <div class="detail-meta-group">
          <h4>Key Features &amp; Actives:</h4>
          <div class="tag-cloud">
            <c:forEach var="tag" items="${product.tags}">
              <span class="tag-item"><c:out value="${tag}"/></span>
            </c:forEach>
          </div>
        </div>

        <div class="detail-meta-group">
          <h4>Targeted Skin Types:</h4>
          <div class="skin-types-list">
            <c:forEach var="st" items="${product.skinTypes}">
              <span class="skin-type-tag"><c:out value="${st}"/></span>
            </c:forEach>
            <c:if test="${product.suitableForSensitive}">
              <span class="skin-type-tag sensitive-safe">&#10003; Sensitive Safe</span>
            </c:if>
          </div>
        </div>

        <%-- Retailer Price Comparison Table --%>
        <div class="price-comparison-section">
          <h2 class="comparison-title">Compare Prices Across Retailers</h2>
          <p class="comparison-subtitle">Offers are sorted by price ascending. Buy direct from the seller.</p>

          <div class="table-responsive">
            <table class="price-table">
              <thead>
                <tr>
                  <th scope="col">Site Name</th>
                  <th scope="col">Price</th>
                  <th scope="col">Status</th>
                  <th scope="col" class="text-right">Action</th>
                </tr>
              </thead>
              <tbody>
                <c:set var="sortedOffers" value="${product.sortedOffers}" />
                <c:forEach var="offer" items="${sortedOffers}" varStatus="status">
                  <tr class="${status.first ? 'lowest-price-row' : ''}">
                    <td class="cell-sitename">
                      <div class="site-branding">
                        <span class="site-icon">&#128722;</span>
                        <strong><c:out value="${offer.siteName}"/></strong>
                      </div>
                    </td>
                    <td class="cell-price">
                      <span class="table-price"><c:out value="${offer.formattedPrice}"/></span>
                    </td>
                    <td class="cell-badge">
                      <c:choose>
                        <c:when test="${status.first}">
                          <span class="badge-lowest-price">&#9733; Lowest Price</span>
                        </c:when>
                        <c:otherwise>
                          <span class="badge-in-stock">In Stock</span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td class="cell-action text-right">
                      <a href="<c:out value="${offer.url}"/>"
                         target="_blank"
                         rel="noopener noreferrer sponsored"
                         class="btn btn-primary ${status.first ? 'btn-highlight' : ''} btn-buy">
                        Buy Now &rsaquo;
                      </a>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
          <p class="table-notice">
            * Prices and availability are subject to change by each seller. Clicking 'Buy Now' transfers you directly to the verified merchant.
          </p>
        </div>
      </div>
    </div>
  </main>

  <%-- Shared Footer --%>
  <%@ include file="/WEB-INF/fragments/footer.jspf" %>

  <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
