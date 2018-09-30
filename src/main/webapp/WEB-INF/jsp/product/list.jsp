<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product list</title>
</head>
<body>
	<h1>Product list:</h1>
	<table border="1">
		<thead>
			<tr>
				<td>Name</td>
				<td>Price</td>
				<td>Id</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${products}" var="prod">
				<tr>
					<td><a href="<c:url value="${prod.id}/detail"/>"><c:out value="${prod.name}" /></a></td>
					<td><c:out value="${prod.price}" /></td>
					<td><c:out value="${prod.id}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="/IntelliBuy">Back</a>
</body>
</html>