<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>	
<%@ page import="hr.fer.zemris.webapps.webapp_baza.poll.PollOption" %>
<%@ page import="java.util.List"%>	 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	Long pollID = (Long) request.getAttribute("pollID");
%>
<!DOCTYPE html>
<html>
	<head>
		<style type="text/css">
			table.rez td {text-align:center;}
		</style>
	</head>
	<body>
		<h1>Voting results</h1>
		<p>Here are voting results.</p>
		<table border="1" class="rez">
			<thead><tr><th>Poll Option</th><th>Number Of Votes</th></tr></thead>
			<tbody>
				<c:forEach var="opt" items="${options}">
					<tr><td>${opt.optionTitle}</td><td>${opt.votesCount}</td></tr>
				</c:forEach>
			</tbody>
		</table>
		
		<h2>Graphical Display Of Results</h2>
		<img alt="Pie-chart" src="/webapp-baza/voting-graphics?pollID=<%=pollID%>" />
		
		<h2>Results in XLS format</h2>
		<p>Results in XLS format are available <a href="/webapp-baza/voting-xls?pollID=<%=pollID%>">here</a></p>
		
		<h2>Other</h2>
		<p>Winners of the voting:</p>
		<ul>
			<c:forEach var="w" items="${winners}">
				<li><a href="${w.optionLink}" target="_blank">${w.optionTitle}</a></li>
			</c:forEach>
		</ul>
	</body>
</html>