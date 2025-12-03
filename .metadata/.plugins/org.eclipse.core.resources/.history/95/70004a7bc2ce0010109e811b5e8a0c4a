package com.foodapp.servlet;

import com.foodapp.dao.RestaurantDAO;
import com.foodapp.dao.impl.RestaurantDAOImpl;
import com.foodapp.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/restaurant")
public class RestaurantServlet extends HttpServlet {
    private RestaurantDAO restaurantDAO = new RestaurantDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            addRestaurant(request, response);
        } else if ("update".equalsIgnoreCase(action)) {
            updateRestaurant(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            deleteRestaurant(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            listRestaurants(request, response);
        } else if ("active".equalsIgnoreCase(action)) {
            listActiveRestaurants(request, response);
        }
    }

    private void addRestaurant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Restaurant r = new Restaurant();
        r.setName(request.getParameter("name"));
        r.setCuisineType(request.getParameter("cuisineType"));
        r.setDeliveryTime(request.getParameter("deliveryTime"));
        r.setAddress(request.getParameter("address"));
        r.setAdminUserId(Integer.parseInt(request.getParameter("adminUserId")));

        boolean success = restaurantDAO.addRestaurant(r);
        response.getWriter().println(success ? "Restaurant added" : "Failed to add restaurant");
    }

    private void updateRestaurant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Restaurant r = new Restaurant();
        r.setRestaurantId(Integer.parseInt(request.getParameter("restaurantId")));
        r.setName(request.getParameter("name"));
        r.setCuisineType(request.getParameter("cuisineType"));
        r.setDeliveryTime(request.getParameter("deliveryTime"));
        r.setAddress(request.getParameter("address"));
        r.setAdminUserId(Integer.parseInt(request.getParameter("adminUserId")));

        boolean success = restaurantDAO.updateRestaurant(r);
        response.getWriter().println(success ? "Restaurant updated" : "Failed to update restaurant");
    }

    private void deleteRestaurant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("restaurantId"));
        boolean success = restaurantDAO.deleteRestaurant(id);
        response.getWriter().println(success ? "Restaurant deleted" : "Failed to delete restaurant");
    }

    private void listRestaurants(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Restaurant> list = restaurantDAO.getAllRestaurants();
        response.getWriter().println(list);
    }

    private void listActiveRestaurants(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Restaurant> list = restaurantDAO.getActiveRestaurants();
        response.getWriter().println(list);
    }
}
