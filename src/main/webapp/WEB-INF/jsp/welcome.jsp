<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome to IntelliBuy</title>
</head>
<body>
	<h1>Hello, everyone!</h1>
	<a href="product/list">Product list</a><br />
	<c:choose>
		<c:when test="${role == 'GUEST'}">
			<a href="login/">Login</a><br />
		</c:when>
		<c:otherwise>
			<a href="login/welcome"><c:out value="Welcome, ${name}" ></c:out></a><br />
			<a href="logout">Logout</a><br/>
		</c:otherwise>
	</c:choose>
	<c:if test="${role == 'ADMIN' }">
		<a href="admin/">Admin page</a>
	</c:if>
	<c:if test="${isInit}">
		<p>Set isInit to false after initialization</p>
		<p>Please set remove /init and /destroy request mapping if no more need</p>
		<form action="./init" method="post">
			<input type="submit" value="Initialize all tables"/>
		</form>
		<form action="./destroy" method="post">
			<input type="submit" value="Drop all tables" />
		</form>
	</c:if>
</body>
</html>