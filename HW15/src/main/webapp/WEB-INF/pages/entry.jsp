<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>${entry.title}</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<header class="header">
		<c:if test="${sessionScope['current.user.id'] != null}">
			Currently logged user: <b><%=session.getAttribute("current.user.nick")%></b><br />
			<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a><br />
		</c:if>
		<a href="${pageContext.request.contextPath}/servleti/author/${entry.creator.nick}">
			Return
		</a>
	</header>
	<div class="container">
		<h2>${entry.title}</h2>
		<hr>
		<p class="entry">${entry.text}<p>
		<p class="smalltext">Created at: ${entry.createdAt}<br />
		<c:if test="${entry.lastModifiedAt != null}">Last modified at: ${entry.lastModifiedAt}</c:if>
		</p>
		<c:if test="${sessionScope['current.user.id'].equals(entry.creator.id)}">
			<p><a href="${pageContext.request.contextPath}/servleti/author/
						${sessionScope['current.user.nick']}/edit?id=${entry.id}"
				  class="button">Edit blog entry</a></p>
		</c:if>
	</div>
	<div class="container">
		<h3>Comments</h3>
		<hr>
		<c:forEach var="c" items="${entry.comments}">
			<p class="entry">${c.message}</p>
			<p class="smalltext"><b>${c.usersEMail}</b><br />${c.postedOn}</p>
			<hr>
		</c:forEach>
	</div>
	<form action="${pageContext.request.contextPath}/servleti/add_comment?EID=${entry.id}" method="post">
		<textarea name="comment" rows="7" cols="40" placeholder="Add comment here..."></textarea><br />
		<input type="submit" value="Add comment" />
	</form>
</body>
</html>