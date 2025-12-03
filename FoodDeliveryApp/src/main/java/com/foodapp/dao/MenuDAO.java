package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.Menu;

public interface MenuDAO {
    boolean addMenuItem(Menu menu);
    boolean updateMenuItem(Menu menu);
    boolean deleteMenuItem(int menuId);
    Menu getMenuItemById(int menuId);
    List<Menu> getAllMenuItems();
    List<Menu> getMenuByRestaurant(int restaurantId);
}
