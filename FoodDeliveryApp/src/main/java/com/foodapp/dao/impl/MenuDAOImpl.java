package com.foodapp.dao.impl;

import com.foodapp.dao.MenuDAO;
import com.foodapp.model.Menu;
import com.foodapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    @Override
    public boolean addMenuItem(Menu menu) {
        String sql = "INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, imagePath) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, menu.getRestaurantId());
            ps.setString(2, menu.getItemName());
            ps.setString(3, menu.getDescription());
            ps.setDouble(4, menu.getPrice());
            ps.setBoolean(5, menu.isAvailable());
            ps.setString(6, menu.getImagePath());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean updateMenuItem(Menu menu) {
        String sql = "UPDATE menu SET restaurantId=?, itemName=?, description=?, price=?, isAvailable=?, imagePath=? WHERE menuId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, menu.getRestaurantId());
            ps.setString(2, menu.getItemName());
            ps.setString(3, menu.getDescription());
            ps.setDouble(4, menu.getPrice());
            ps.setBoolean(5, menu.isAvailable());
            ps.setString(6, menu.getImagePath());
            ps.setInt(7, menu.getMenuId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean deleteMenuItem(int menuId) {
        String sql = "DELETE FROM menu WHERE menuId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, menuId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public Menu getMenuItemById(int menuId) {
        String sql = "SELECT * FROM menu WHERE menuId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, menuId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return extractMenu(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Menu> getAllMenuItems() {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(extractMenu(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public List<Menu> getMenuByRestaurant(int restaurantId) {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu WHERE restaurantId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, restaurantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(extractMenu(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private Menu extractMenu(ResultSet rs) throws SQLException {
        Menu menu = new Menu();
        menu.setMenuId(rs.getInt("menuId"));
        menu.setRestaurantId(rs.getInt("restaurantId"));
        menu.setItemName(rs.getString("itemName"));
        menu.setDescription(rs.getString("description"));
        menu.setPrice(rs.getDouble("price"));
        menu.setAvailable(rs.getBoolean("isAvailable"));
        menu.setImagePath(rs.getString("imagePath"));
        return menu;
    }
}
