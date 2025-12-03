package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.Restaurant;

public interface RestaurantDAO {
    boolean addRestaurant(Restaurant restaurant);
    boolean updateRestaurant(Restaurant restaurant);
    boolean deleteRestaurant(int restaurantId);
    Restaurant getRestaurantById(int restaurantId);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getActiveRestaurants();

    // ✅ New method for search
    List<Restaurant> searchRestaurants(String location, String name);
}
