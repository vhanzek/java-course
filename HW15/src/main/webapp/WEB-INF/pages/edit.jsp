<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Edit Blog Entry</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<header class="header">
		<c:if test="${sessionScope['current.user.id'] != null}">
			Currently logged user: <b><%=session.getAttribute("current.user.nick")%></b><br />
			<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a><br />
		</c:if>
		<a href="${pageContext.request.contextPath}/servleti/author/${sessionScope['current.user.nick']}">
			Return
		</a>
	</header>
	<div class="container">
		<h2>Edit blog entry</h2>
		<form action="${pageContext.request.contextPath}/servleti/edit?EID=${entry.id}" 
			  method="post">
			<input type="text" name="title" value="${entry.title}" placeholder="Title" size="66"/><br />
			<textarea name="text" rows="10" cols="60" placeholder="Enter text here...">${entry.text}</textarea><br />
			<input type="submit" value="Save changes" />
		</form>
	</div>

</body>
</html>