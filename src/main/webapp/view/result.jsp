<%@page import="Model.ClientHandlerBO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page session="true" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    int taskId = Integer.parseInt(request.getParameter("taskId"));
    Integer userId = (Integer) session.getAttribute("userId");
    String resultRelativePath = null;
	
    
    resultRelativePath = ClientHandlerBO.getInstance().getResultRelativePathTask(taskId, userId);
    if(resultRelativePath == null)
    	response.sendRedirect("dashboard.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task Result</title>
<link rel="stylesheet" href="CSS/resultCSS.css">
</head>
<body>
	<h1>Task Result</h1>
	<div class="content">
	    <p>Result for Task ID: <%= taskId %></p>
	    <%
	        if (resultRelativePath != null) {
	    %>
	        <p>Your converted file is ready:</p>
	      <%
	    String filePath = request.getContextPath() + "/" + resultRelativePath;
		%>
		<a href="<%= filePath %>" download>Download File</a>
	    <%
	        } else {
	    %>
	        <p>No result available for this task.</p>
	    <%
	        }
	    %>
	    <a href="dashboard.jsp">Back to Dashboard</a>
	</div>
</body>
</html>