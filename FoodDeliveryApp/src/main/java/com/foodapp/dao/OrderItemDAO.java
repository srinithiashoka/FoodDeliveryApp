package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.OrderItem;

public interface OrderItemDAO {
    boolean addOrderItem(OrderItem item);
    boolean updateOrderItem(OrderItem item);
    boolean deleteOrderItem(int orderItemId);
    OrderItem getOrderItemById(int orderItemId);
    List<OrderItem> getAllOrderItems();
    List<OrderItem> getItemsByOrder(int orderId);
    void saveOrderItem(OrderItem item);

}
