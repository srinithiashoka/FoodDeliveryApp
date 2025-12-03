package com.foodapp.dao.impl;

import com.foodapp.dao.OrderItemDAO;
import com.foodapp.model.OrderItem;
import com.foodapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {

    @Override
    public boolean addOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_items (orderId, menuId, quantity, totalPrice) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getMenuId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getTotalPrice());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) item.setOrderItemId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean updateOrderItem(OrderItem item) {
        String sql = "UPDATE order_items SET orderId=?, menuId=?, quantity=?, totalPrice=? WHERE orderItemId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getMenuId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getTotalPrice());
            ps.setInt(5, item.getOrderItemId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean deleteOrderItem(int orderItemId) {
        String sql = "DELETE FROM order_items WHERE orderItemId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public OrderItem getOrderItemById(int orderItemId) {
        String sql = "SELECT * FROM order_items WHERE orderItemId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderItemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return extractOrderItem(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(extractOrderItem(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public List<OrderItem> getItemsByOrder(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE orderId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(extractOrderItem(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private OrderItem extractOrderItem(ResultSet rs) throws SQLException {
        OrderItem item = new OrderItem();
        item.setOrderItemId(rs.getInt("orderItemId"));
        item.setOrderId(rs.getInt("orderId"));
        item.setMenuId(rs.getInt("menuId"));
        item.setQuantity(rs.getInt("quantity"));
        item.setTotalPrice(rs.getDouble("totalPrice"));
        return item;
    }
    @Override
    public void saveOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_items (orderId, menuId, quantity, totalPrice) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getMenuId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getTotalPrice());
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
