<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add product successfully</title>
</head>
<body>
	<h1>This is after add product page</h1>
	<p>img = This is a photo</p>
	<table border="1">
		<thead>
			<tr>
				<td>Name</td>
				<td>Product ID</td>
				<td>Price</td>
				<td>Number</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><c:out value="${product.name}" /></td>
				<td><c:out value="${product.productId}" /></td>
				<td><c:out value="${product.price}" /></td>
				<td><c:out value="${product.number }"></c:out>
			</tr>
		</tbody>
	</table>
	<a href="cart">Go to checkout</a><br />
	<a href="list">Back to product list</a><br />
</body>
</html>