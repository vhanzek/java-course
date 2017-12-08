<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<header class="header">
		<a href="${pageContext.request.contextPath}/servleti/main">Return</a>
	</header>
	<div class="container">
		<h2>Register</h2>
		<form action="registration" method="post">			
			<c:if test="${user.hasError('fn')}">
				<div class="error"><c:out value="${user.getErrorMessage('fn')}"/></div>
			</c:if>
			<input type="text" name="firstName" value='<c:out value="${user.firstName}"/>' placeholder="First Name" /><br />
			
			<c:if test="${user.hasError('ln')}">
				<div class="error"><c:out value="${user.getErrorMessage('ln')}"/></div>
			</c:if>
			<input type="text" name="lastName" value='<c:out value="${user.lastName}"/>' placeholder="Last Name" /><br />
			
			<c:if test="${user.hasError('email')}">
				<div class="error"><c:out value="${user.getErrorMessage('email')}"/></div>
			</c:if>
			<input type="email" name="email" value='<c:out value="${user.email}"/>'placeholder="Email" /><br />
			
			<c:if test="${user.hasError('nick')}">
				<div class="error"><c:out value="${user.getErrorMessage('nick')}"/></div>
			</c:if>
			<input type="text" name="nick" value='<c:out value="${user.nick}"/>' placeholder="Username" /><br />
			
			<c:if test="${user.hasError('pass')}">
				<div class="error"><c:out value="${user.getErrorMessage('pass')}"/></div>
			</c:if>
			<input type="password" name="password" placeholder="Password" /><br />
			
			<input type="submit" name="submit" value="Register" />
		</form>
	</div>
</body>
</html>

