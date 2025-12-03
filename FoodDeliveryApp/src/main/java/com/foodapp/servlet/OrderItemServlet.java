package com.foodapp.servlet;

import com.foodapp.dao.OrderItemDAO;
import com.foodapp.dao.impl.OrderItemDAOImpl;
import com.foodapp.model.OrderItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/orderItem")
public class OrderItemServlet extends HttpServlet {
    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            addItem(request, response);
        } else if ("update".equalsIgnoreCase(action)) {
            updateItem(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            deleteItem(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            listItems(request, response);
        } else if ("byOrder".equalsIgnoreCase(action)) {
            listItemsByOrder(request, response);
        }
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OrderItem item = new OrderItem();
        item.setOrderId(Integer.parseInt(request.getParameter("orderId")));
        item.setMenuId(Integer.parseInt(request.getParameter("menuId")));
        item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        item.setTotalPrice(Double.parseDouble(request.getParameter("totalPrice")));

        boolean success = orderItemDAO.addOrderItem(item);
        response.getWriter().println(success ? "Order item added" : "Failed to add order item");
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OrderItem item = new OrderItem();
        item.setOrderItemId(Integer.parseInt(request.getParameter("orderItemId")));
        item.setOrderId(Integer.parseInt(request.getParameter("orderId")));
        item.setMenuId(Integer.parseInt(request.getParameter("menuId")));
        item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        item.setTotalPrice(Double.parseDouble(request.getParameter("totalPrice")));

        boolean success = orderItemDAO.updateOrderItem(item);
        response.getWriter().println(success ? "Order item updated" : "Failed to update order item");
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("orderItemId"));
        boolean success = orderItemDAO.deleteOrderItem(id);
        response.getWriter().println(success ? "Order item deleted" : "Failed to delete order item");
    }

    private void listItems(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<OrderItem> list = orderItemDAO.getAllOrderItems();
        response.getWriter().println(list);
    }

    private void listItemsByOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        List<OrderItem> list = orderItemDAO.getItemsByOrder(orderId);
        response.getWriter().println(list);
    }
}
