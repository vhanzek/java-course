<%@ page import="hr.fer.zemris.java.servlets.voting.VotingServlet.MapEntry, java.util.Map" 
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%! @SuppressWarnings("unchecked") %>
<% 
	String bgcolor = (String) session.getAttribute("bgcolor");
	if(bgcolor == null) bgcolor = "white";
	
	Map<String, MapEntry> bands = 
		(Map<String, MapEntry>) application.getAttribute("bands");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Voting Index</title>
	</head>
	<body bgcolor=<%= bgcolor %>>
		<h1>Vote for your favorite band:</h1>
		<p>Which band is your favorite? Click on the link to vote!</p>
		<ol>
			<c:forEach var="b" items="${bands.values()}">
				<li><a href="voting-vote?id=${b.id}">${b.name}</a></li>
			</c:forEach>
		</ol>
	</body>
</html>