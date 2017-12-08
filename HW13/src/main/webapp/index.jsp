<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% 
	String bgcolor = (String) session.getAttribute("bgcolor");
	if(bgcolor == null) bgcolor = "white";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcome</title>
	</head>
	<body bgcolor="<%= bgcolor %>">
		<a href="/webapp2/colors.jsp">Background color chooser</a><br>
		<a href="/webapp2/trigonometric?a=0&b=90">Trigonometric functions</a><br>
		<a href="/webapp2/stories">Funny story</a><br>
		<a href="/webapp2/powers?a=1&b=100&n=3">Excel File Download</a><br>
		<a href="/webapp2/appinfo.jsp">Application Info</a><br>
		<a href="/webapp2/reportImage">OS Usage Chart</a><br>
		<a href="/webapp2/voting">Vote For Your Favorite Band</a><br>
	</body>
</html>
