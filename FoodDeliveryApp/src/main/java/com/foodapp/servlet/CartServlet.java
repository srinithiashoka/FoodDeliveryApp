package com.foodapp.servlet;

import com.foodapp.dao.CartDAO;
import com.foodapp.dao.MenuDAO;
import com.foodapp.dao.impl.CartDAOImpl;
import com.foodapp.dao.impl.MenuDAOImpl;
import com.foodapp.model.Menu;
import com.foodapp.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private final CartDAO cartDAO = new CartDAOImpl();
    private final MenuDAO menuDAO = new MenuDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"User not logged in\"}");
            return;
        }

        String action = request.getParameter("action");
        String menuIdStr = request.getParameter("menuId");
        String quantityStr = request.getParameter("quantity");
        int userId = user.getUserId();

        // -----------------------------
        // View cart count (for badge)
        // -----------------------------
        if ("viewcount".equalsIgnoreCase(action)) {
            int cartCount = cartDAO.getCartItemCount(userId);
            response.getWriter().write("{\"count\":" + cartCount + "}");
            return;
        }

        // -----------------------------
        // Clear cart action
        // -----------------------------
        if ("clear".equalsIgnoreCase(action)) {
            boolean cleared = cartDAO.clearCartByUserId(userId);
            int cartCount = cartDAO.getCartItemCount(userId);
            response.getWriter().write("{\"status\":\"success\",\"message\":\"Cart cleared\",\"count\":" + cartCount + "}");
            return;
        }

        // -----------------------------
        // Validate required parameters
        // -----------------------------
        if (action == null) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Action is required\"}");
            return;
        }

        int menuId = 0;
        int quantity = 1; // default

        // Safe parsing for menuId
        try {
            if (menuIdStr != null && !menuIdStr.trim().isEmpty()) {
                menuId = Integer.parseInt(menuIdStr.trim());
            }
        } catch (NumberFormatException e) {
            menuId = 0;
        }

        // Safe parsing for quantity
        try {
            if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                quantity = Integer.parseInt(quantityStr.trim());
            }
        } catch (NumberFormatException e) {
            quantity = 1;
        }

        if (menuId <= 0) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Invalid menu item\"}");
            return;
        }

        // Fetch menu item
        Menu menu = menuDAO.getMenuItemById(menuId);
        if (menu == null) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Menu item not found\"}");
            return;
        }

        int restaurantId = menu.getRestaurantId();
        Integer currentCartRestaurant = cartDAO.getRestaurantIdInCart(userId);

        // If cart has items from a different restaurant, clear it
        if (currentCartRestaurant != null && !currentCartRestaurant.equals(restaurantId)) {
            cartDAO.clearCartByUserId(userId);
        }

        boolean success = false;
        String message = "";

        switch (action.toLowerCase()) {
            case "add":
                if (quantity <= 0) {
                    success = cartDAO.removeFromCart(userId, menuId);
                    message = success ? "Item removed" : "Item not found in cart";
                } else {
                    success = cartDAO.addOrUpdateCart(userId, menuId, quantity, restaurantId);
                    message = success ? "Item added/updated" : "Failed to update cart";
                }
                break;

            case "remove":
                success = cartDAO.removeFromCart(userId, menuId);
                message = success ? "Item removed" : "Item not found in cart";
                break;

            default:
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Unknown action\"}");
                return;
        }

        int cartCount = cartDAO.getCartItemCount(userId);
        response.getWriter().write("{\"status\":\"" + (success ? "success" : "error") + "\",\"message\":\"" + message + "\",\"count\":" + cartCount + "}");
    }
}
