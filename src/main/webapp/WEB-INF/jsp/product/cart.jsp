<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shopping cart</title>
</head>
<body>
	<h1>This is your shopping cart.</h1>
	<c:choose>
		<c:when test="${products != null }">
			<table>
				<thead>
					<tr>
						<td>Name</td>
						<td>Number</td>
						<td>Price</td>
						<td></td>
					</tr>
				</thead>
				<c:forEach items="${products}" var="prod">
					<tbody>
						<tr>
							<td><c:out value="${prod.name}"></c:out></td>
							<td><c:out value="${prod.number}"></c:out></td>
							<td><c:out value="${prod.price * prod.number}"></c:out></td>
							<td><form action='<c:url value="delete/${prod.productId}"></c:url>' method="post">
								<input type="submit" value="Delete"/>
							</form></td>
						</tr>		
					</tbody>
				</c:forEach>
			</table>
			<form action="order">
				<input type="submit" value="Checkout" />
			</form>
		</c:when>
		<c:otherwise>
			<p>Your cart is empty.</p>
		</c:otherwise>
	</c:choose>
	<a href="../">Back</a><br />

</body>
</html>