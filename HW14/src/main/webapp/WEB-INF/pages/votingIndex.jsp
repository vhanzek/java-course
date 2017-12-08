<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="hr.fer.zemris.webapps.webapp_baza.poll.PollOption" %>
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
		<h1>${poll.title}</h1>
		<p>${poll.message}</p>
		<ol>
			<c:forEach var="o" items="${options}">
				<li><a href="/webapp-baza/voting-results?id=${o.id}">${o.optionTitle}</a></li>
			</c:forEach>
		</ol>
	</body>
</html>