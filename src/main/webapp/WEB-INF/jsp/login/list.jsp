<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All customer info</title>
</head>
<body>
	<h1>This is all customer info</h1>
	<table border="1">
		<thead>
			<tr>
				<td>Id</td>
				<td>Name</td>
				<td>Username</td>
				<td>Password</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${customers}" var="cust">
				<tr>
					<td><c:out value="${cust.id}" /></td>
					<td><c:out value="${cust.name}" /></td>
					<td><c:out value="${cust.username}" /></td>
					<td><c:out value="${cust.password}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="/IntelliBuy">Back</a>

</body>
</html>