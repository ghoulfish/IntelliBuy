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
	<table>
		<thead>
			<tr>
				<td>Name</td>
				<td>ID</td>
				<td>Price</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><c:out value="${product.name}" /></td>
				<td><c:out value="${product.id}" /></td>
				<td><c:out value="${product.price}" /></td>
			</tr>
		</tbody>
	</table>
	<input type="button" value="Add to cart" onclick="location.href='/IntelliBuy'" /><br />
	<a href="/IntelliBuy/product/list">Back to product list</a><br />
	<a href="/IntelliBuy">Back</a>
</body>
</html>