<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<link rel="stylesheet" href="CSS/loginCSS.css">
</head>
<body>
    <h1>Register</h1>
    <form action="<%= request.getContextPath() %>/register" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>

		<label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <button type="submit">Register</button>
    </form>
    <% 
    String error = " ";
    error = request.getParameter("error");
    if ("true".equals(error)) {
	%>
        <p style="color: green;">Account created successfully!</p>
	<% 
    }
    else if ("false".equals(error)){
	%>
		<p style="color: red;">Username or email already exists</p>
	<% 
    }
	%>
    <p>Already have an account? <a href="login.jsp">Login here</a></p>
</body>
</html>
