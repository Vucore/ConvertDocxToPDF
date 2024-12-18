<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>	
<link rel="stylesheet" href="CSS/loginCSS.css">
</head>
<body>
	 <h1>Login</h1>
    <form action="<%= request.getContextPath() %>/authenticate" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <button type="submit">Login</button>
    </form>
    <% 
    String error = request.getParameter("error");
    if (error != null) {
%>
        <p style="color: red;"><%= error %></p>
<% 
    }
%>
    
    <p>Don't have an account? <a href="register.jsp">Register here</a></p>
</body>
</html>