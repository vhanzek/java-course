<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% 
	String bgcolor = (String) session.getAttribute("bgcolor");
	if(bgcolor == null) bgcolor = "white";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Set Color</title>
	</head>
	<body bgcolor=<%= bgcolor %>>
		<a href="/webapp2/setcolor?color=white">WHITE</a><br>
		<a href="/webapp2/setcolor?color=red">RED</a><br>
		<a href="/webapp2/setcolor?color=green">GREEN</a><br>
		<a href="/webapp2/setcolor?color=cyan">CYAN</a>
	</body>
</html>