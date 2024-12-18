<%@page import="Model.ClientHandlerBO"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="CSS/dashboardCSS.css">
</head>
<body>
	<h1>Welcome, <%= username %>!</h1>
	<a href="index.jsp">Logout</a>
	
	<h2>Create a New Task</h2>
	<form action="<%= request.getContextPath() %>/task" method="post" enctype="multipart/form-data">
	    <label for="type">Task Type:</label>
	    <select id="type" name="type">
	        <option value="WordtoPDF">Word to PDF</option>
	    </select>
	    
	    <label for="data">Input Data (DOCX/PDF):</label>
	    <input type="file" id="data" name="data" multiple required><br><br>
	    
	    <button type="submit">Submit Task</button>
	</form>


    <h2>Your Tasks</h2>
    <table border="1">
        <tr>
            <th>Task ID</th>
            <th>File Name</th>
            <th>Type</th>
            <th>Status</th>
            <th>Create At</th>
            <th>Complete At</th>
            <th>Result</th>
        </tr>
        <%
            // Lấy danh sách các tác vụ từ cơ sở dữ liệu
         	Integer userId = (Integer) session.getAttribute("userId");
        	List<Map<String, Object>> tasks = ClientHandlerBO.getInstance().getTasksByUserId(userId);
        	
        	for(Map<String, Object> task : tasks) {
        %>
        <tr>
            <td><%= task.get("id") %></td>
            <td><%= task.get("file_name") %></td>
            <td><%= task.get("type") %></td>
            <td><%= task.get("status") %></td>
            <td><%= task.get("created_at") %></td>
            <td><%= task.get("updated_at") %></td>
            <td>
                <% if ("completed".equals(task.get("status"))) { %>
                    <a href="result.jsp?taskId=<%= task.get("id") %>">View Result</a>
                <% } else { %>
                    <%= task.get("status") %>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>