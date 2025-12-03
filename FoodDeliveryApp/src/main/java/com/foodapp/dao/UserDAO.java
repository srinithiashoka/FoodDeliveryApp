package com.foodapp.dao;

import java.util.List;
import com.foodapp.model.User;

public interface UserDAO {
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int userId);
    User getUserById(int userId);
    List<User> getAllUsers();
    User login(String username, String password);

    // NEW: fetch user by username only
    User getUserByUsername(String username);
}
