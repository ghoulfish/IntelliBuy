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
				<td>Stock</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${products}" var="prod">
				<tr>
					<td><a href="<c:url value="detail/${prod.id}"/>"><c:out value="${prod.name}" /></a></td>
					<td><c:out value="${prod.price/100}" /></td>
					<td><c:out value="${prod.stock}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="cart">Your cart</a><br />
	<a href="/IntelliBuy">Back</a>
</body>
</html>