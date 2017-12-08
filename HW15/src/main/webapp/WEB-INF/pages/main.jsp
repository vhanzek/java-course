<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Login</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container">
		<h2>Login</h2>
		<form action="${pageContext.request.contextPath}/servleti/login" method="post">
			<div class="error">${errorMessage}</div>
			<input type="text" name="nick" value='<c:out value="${nick}"/>' placeholder="Username" /><br />
			<input type="password" name="password" placeholder="Password" /><br />
			<input type="submit" value="Login" />
		</form>
		<div class="smalltext">
			<p>
				Don't have an account? Register 
				<a href="${pageContext.request.contextPath}/servleti/register">here</a>.
			</p>
		</div>
	</div>
	<div class="container">
		<h2>List of registered authors</h2>
		<ul>
			<c:forEach var="u" items="${users}">
				<li>
					${u.lastName}, ${u.firstName} - 
					<a href="${pageContext.request.contextPath}/servleti/author/${u.nick}">${u.nick}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>

