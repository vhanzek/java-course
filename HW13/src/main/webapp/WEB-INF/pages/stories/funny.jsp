<%@ page import="java.util.Random" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	String bgcolor = (String) session.getAttribute("bgcolor");
	if(bgcolor == null) bgcolor = "white";
	
	int[] rgb = (int[]) request.getAttribute("rgb");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Funny stories</title>
</head>
<body bgcolor=<%= bgcolor %>>
	<font color="rgb(${rgb[0]},${rgb[1]},${rgb[2]})">
		The class teacher asks students to name an animal that begins with an “E”. One boy says, “Elephant.”<br>
		Then the teacher asks for an animal that begins with a “T”. The same boy says, “Two elephants.”<br>
		The teacher sends the boy out of the class for bad behavior. After that she asks for an animal beginning with “M”.<br>
		The boy shouts from the other side of the wall: “Maybe an elephant!”
	</font>
</body>
</html>
