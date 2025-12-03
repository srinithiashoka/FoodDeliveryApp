package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.Cart;
import com.foodapp.model.CartItem;


public interface CartDAO {
    boolean addOrUpdateCart(int userId, int menuId, int quantity, int restaurantId);
    boolean removeFromCart(int userId, int menuId);
    boolean updateCart(Cart cart);
    boolean deleteCartItem(int cartId);
    List<CartItem> getCartByUser(int userId);
    boolean clearCartByUserId(int userId);
    boolean itemExists(int userId, int menuId);
    int getCartItemCount(int userId);
    Integer getRestaurantIdInCart(int userId);
}
