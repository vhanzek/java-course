<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Blog - ${author.nick}</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<header class="header">
		<c:if test="${sessionScope['current.user.id'] != null}">
			Currently logged user: <b><%=session.getAttribute("current.user.nick")%></b><br />
			<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a><br />
		</c:if>
		<a href="${pageContext.request.contextPath}/servleti/main">Return</a>
	</header>
	<div class="container">
	<h2>Blog Entries</h2>
	<ol>
		<c:forEach var="be" items="${entries}">
			<li>
				<a href="${pageContext.request.contextPath}/servleti/author/${author.nick}/${be.id}">
					<b>${be.title}</b>	
				</a>
				<div class="smalltext">Created at: <i>${be.createdAt}</i></div>
				<p class="smalltext">
				<c:if test="${sessionScope['current.user.id'].equals(author.id)}">
					<a href="${pageContext.request.contextPath}/servleti/author/${author.nick}/edit?id=${be.id}"
					   class="button">Edit blog entry</a>
				</c:if>
				</p>
			</li>
		</c:forEach>
	</ol>
	</div>
	<c:if test="${sessionScope['current.user.id'].equals(author.id)}">
		<a href="${pageContext.request.contextPath}/servleti/author/${author.nick}/new"
		   class="button">Add new blog entry</a>
	</c:if>
</body>
</html>