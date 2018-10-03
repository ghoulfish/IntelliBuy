<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product detail</title>
</head>
<body>
	<h1>This is product detail</h1>
	<p>img = This is a photo</p>
	<table border="1">
		<thead>
			<tr>
				<td>Name</td>
				<td>Product ID</td>
				<td>Price</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><c:out value="${product.name}" /></td>
				<td><c:out value="${product.productId}" /></td>
				<td><c:out value="${product.price}" /></td>
			</tr>
		</tbody>
	</table>
	<form action="../add" method="post">
		Amount:<input type="number" name="number" value="1"/><br />
		<input type="hidden" value="${product.productId}" name="id">
		<input type="hidden" value="${product.price}" name="price">
		<input type="hidden" value="${product.name}" name="name">
		<input type="submit" value="Add to cart"/><br />
	</form>
	<a href="/IntelliBuy/product/list">Back to product list</a><br />
	<a href="/IntelliBuy">Back</a>
</body>
</html>