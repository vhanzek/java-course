<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% 
	String bgcolor = (String) session.getAttribute("bgcolor");
	if(bgcolor == null) bgcolor = "white";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Error</title>
	</head>
	<body bgcolor=<%= bgcolor %>>
		<h1>ERROR</h1>
		<p>An error has occurred. Parameters are not valid.</p>
	</body>
</html>