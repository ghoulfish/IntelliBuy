<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check your order</title>
</head>
<body>
	<h1>This is the order page.</h1>
	<h3><c:out value="Your order ID is ${order.id}"></c:out></h3>
	<table>
		<thead>
			<tr>
				<td>Name</td>
				<td>Number</td>
				<td>Price</td>
			</tr>
		</thead>
		<c:forEach items="${order.products}" var="prod">
			<tbody>
				<tr>
					<td><c:out value="${prod.name}"></c:out></td>
					<td><c:out value="${prod.number}"></c:out></td>
					<td><c:out value="${prod.price / 100 * prod.number}"></c:out></td>
				</tr>		
			</tbody>
		</c:forEach>
	</table>
	<a href="pay/${order.id}">Pay Now!</a><br/>
	<a href="cart">Back to cart</a>
</body>
</html>