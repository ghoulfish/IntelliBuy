<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your orders</title>
</head>
<body>
	<h1>View your orders</h1>
	<ul>
		<c:forEach items="${orders}" var="order">
			<li>
				<table border="1">
					<thead>
						<tr>
							<td>ID</td>
							<td>Customer ID</td>
							<td>Create since</td>
							<td>Total discount</td>
							<td>Is paid?</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><c:out value="${order.id }"></c:out></td>
							<td><c:out value="${order.customerId }"></c:out></td>
							<td><c:out value="${order.createSince }"></c:out></td>
							<td><c:out value="${order.discount }"></c:out></td>
							<td><c:out value="${order.paid }"></c:out></td>
							<%-- <td><form action="./order/modify/${order.id}"><input type="submit" value="Modify"/></form></td>
							<td><form action="./order/delete/${order.id}"><input type="submit" value="Delete"/></form></td> --%>
						</tr>
					</tbody>
				</table>
				<table border="1">
					<thead>
						<tr>
							<td>Product ID</td>
							<td>Product Name</td>
							<td>Price(cent)</td>
							<td>number</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${order.products}" var="prod">
							<tr>
								<td><c:out value="${prod.productId}"></c:out></td>
								<td><c:out value="${prod.name}" /></td>
								<td><c:out value="${prod.price}" /></td>
								<td><c:out value="${prod.number}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</li>
		</c:forEach>
	</ul>
	<c:if test="${err_msg != null }">
		<c:out value="${err_msg }"></c:out><br/>
	</c:if>
	<c:if test="${msg != null }">
		<c:out value="${msg }"></c:out><br/>
	</c:if>
	<a href="welcome">Back</a><br/>
	<a href="/IntelliBuy/">Back to homepage</a>
</body>
</html>