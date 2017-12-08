<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String bgcolor = (String) session.getAttribute("bgcolor");
	if(bgcolor == null) bgcolor = "white";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Report</title>
	</head>
	<body bgcolor=<%= bgcolor %>>
		<h1>OS Usage</h1>
		<p>Here are the results of OS usage in survey that we completed.</p>
		<img src="/webapp2/renderImage" alt="OS Usage">
	</body>
</html>