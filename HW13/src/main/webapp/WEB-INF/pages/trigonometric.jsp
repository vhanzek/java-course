<%@ page import="java.util.List, hr.fer.zemris.java.servlets.TrigonometricServlet.Calculation" 
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%! @SuppressWarnings("unchecked") %>
<% 
	String bgcolor = (String) session.getAttribute("bgcolor");
	if(bgcolor == null) bgcolor = "white";
	
	List<Calculation> trigCalc = 
		(List<Calculation>) request.getAttribute("trigCalculations");
%>
<html>
	<head>
		<title>Trigonometric</title>
	</head>
	<body bgcolor=<%= bgcolor %>>
		<table border="1">
			<tr>
				<th>Value</th>
				<th>Sin</th>
				<th>Cos</th>
			</tr>
			<% for(Calculation c : trigCalc){ %>
				<tr align="center">
					<td><%= c.getValue() %></td>
					<td><%= c.getSinValue() %></td>
					<td><%= c.getCosValue() %></td>
				</tr>
			<% } %>
		</table>
	</body>
</html>