<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="hr.fer.zemris.webapps.webapp_baza.poll.Poll" %>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<head></head>
		<title>Poll Index</title>
	</head>
	<body>
		<h1>Pick the poll you want:</h1>
		<p>Click on the link to choose desired poll!</p>
		<ol>
			<c:forEach var="p" items="${polls}">
				<li><a href="/webapp-baza/voting?pollID=${p.pollID}">${p.title}</a></li>
			</c:forEach>
		</ol>
	</body>
</html>