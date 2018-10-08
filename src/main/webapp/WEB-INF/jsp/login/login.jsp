<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<h1>This is login page.</h1>
	<form action="check" method="post">
		Username:<input type="text" name="username"/><br />
		Password:<input type="password" name="password"/><br />
		<input type="submit" value="Login"/><br/>
	</form>
	<c:if test="${err_msg != null }">
		<c:out value="${err_msg }"></c:out><br/>
	</c:if>
	<c:if test="${msg != null }">
		<c:out value="${msg }"></c:out><br/>
	</c:if>
	<a href="register">Register</a><br />
	<a href="list">Customer list</a><br />
	<a href="../">Back</a>

</body>
</html>