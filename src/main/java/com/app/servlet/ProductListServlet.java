package com.app.servlet;

import com.app.data.ProductData;
import com.app.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Controller for browsing products, filtering by category, and searching.
 */
@WebServlet(name = "ProductListServlet", urlPatterns = {"/products"})
public class ProductListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String category = request.getParameter("category");
        String query = request.getParameter("query");

        if (category == null || category.trim().isEmpty()) {
            category = "All";
        }

        List<Product> products = ProductData.searchProducts(query, category);

        request.setAttribute("products", products);
        request.setAttribute("categories", ProductData.getCategories());
        request.setAttribute("selectedCategory", category);
        request.setAttribute("searchQuery", (query != null) ? query.trim() : "");

        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}
