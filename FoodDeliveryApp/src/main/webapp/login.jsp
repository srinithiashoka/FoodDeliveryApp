<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String error = request.getParameter("error");
    String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
        <link rel="icon" href="<%= request.getContextPath() %>/images/fh.png">
    
    <title>Login - FoodHub</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="css/home.css">
    <link rel="stylesheet" href="css/auth.css">
</head>
<body>

<div class="page-wrapper">

    <!-- HEADER -->
    <header>
        <div class="container">
            <div class="navbar">
                <div class="logo">
                    <i class="fa-solid fa-motorcycle scooter-icon"></i>
                    <span>FoodHub</span>
                </div>
                <ul class="nav-menu">
                    <li><a href="home.jsp">Home</a></li>
                    <li><a href="#">Restaurants</a></li>
                    <li><a href="#">Offers</a></li>
                    <li><a href="#">Contact</a></li>
                </ul>
                <div class="nav-actions">
                    <button class="btn btn-login" onclick="location.href='login.jsp'">Login</button>
                    <button class="btn btn-signup" onclick="location.href='signup.jsp'">Sign Up</button>
                    <div class="cart-icon-wrapper">
                        <a href="cart.jsp" title="My Cart">
                            <i class="fas fa-shopping-cart cart-icon"></i>
                        </a>
                        <span class="cart-badge"><%= session.getAttribute("cartCount") != null ? session.getAttribute("cartCount") : 0 %></span>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <!-- MAIN CONTENT -->
    <main class="main-wrapper">
        <div class="container auth-container">
            <h2>Login to FoodHub</h2>

            <% if(error != null){ %>
                <p class="auth-message"><%= error %></p>
            <% } %>
            <% if(msg != null){ %>
                <p class="auth-message auth-success"><%= msg %></p>
            <% } %>

            <form action="user" method="post">
                <input type="hidden" name="action" value="login">
                <input type="text" name="username" placeholder="Username" required>
                <input type="password" name="password" placeholder="Password" required>
                <button type="submit">Login</button>
            </form>

            <p>Don't have an account? <a href="signup.jsp">Sign Up</a></p>
        </div>
    </main>

    <!-- FOOTER -->
    <footer>
        <div class="container">
            <p>© 2025 FoodHub | All Rights Reserved | Made with ❤️</p>
        </div>
    </footer>

</div>

</body>
</html>
