package com.app.servlet;

import com.app.data.ProductData;
import com.app.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller for product detail view and retailer price comparison table.
 */
@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/product"})
public class ProductDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Product product = ProductData.getProductById(id);

        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/products?error=notfound");
            return;
        }

        request.setAttribute("product", product);
        request.getRequestDispatcher("/product-detail.jsp").forward(request, response);
    }
}
