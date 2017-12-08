<%@ page import="hr.fer.zemris.java.servlets.voting.VotingServlet.MapEntry, java.util.Map, java.util.List" 
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%! @SuppressWarnings("unchecked") %>
<% 
	String bgcolor = (String) session.getAttribute("bgcolor");
	if(bgcolor == null) bgcolor = "white";
	
	Map<String, Integer> votingResults = 
		(Map<String, Integer>) request.getAttribute("votingResults");
	Map<String, MapEntry> bands = 
		(Map<String, MapEntry>) application.getAttribute("bands");
	List<MapEntry> winners =
		(List<MapEntry>) request.getAttribute("winners");
%>
<!DOCTYPE html>
<html>
	<head>
		<style type="text/css">
			table.rez td {text-align:center;}
		</style>
	</head>
	<body bgcolor=<%= bgcolor %>>
		<h1>Voting results</h1>
		<p>Here are voting results.</p>
		<table border="1" class="rez">
			<thead><tr><th>Band</th><th>Number Of Votes</th></tr></thead>
			<tbody>
				<c:forEach var="entry" items="${votingResults}">
					<tr><td>${entry.key}</td><td>${entry.value}</td></tr>
				</c:forEach>
			</tbody>
		</table>
		
		<h2>Graphical Display Of Results</h2>
		<img alt="Pie-chart" src="/webapp2/voting-graphics" />
		
		<h2>Results in XLS format</h2>
		<p>Results in XLS format are available <a href="/webapp2/voting-xls">here</a></p>
		
		<h2>Other</h2>
		<p>Winner bands song example:</p>
		<ul>
			<c:forEach var="w" items="${winners}">
				<li><a href="${w.link}" target="_blank">${w.name}</a></li>
			</c:forEach>
		</ul>
	</body>
</html>