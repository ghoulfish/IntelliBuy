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
			<table id="cart_table">
				<thead>
					<tr>
						<td>Name</td>
						<td>Number</td>
						<td>Price</td>
						<td></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${products}" var="prod">
						<tr>
							<td><c:out value="${prod.name}"></c:out></td>
							<td><c:out value="${prod.number}"></c:out></td>
							<td><c:out value="${prod.number * prod.price / 100}"></c:out></td>
							<td><form action='<c:url value="delete/${prod.productId}"></c:url>' method="post">
								<input type="submit" value="Delete"/>
							</form></td>
						</tr>		
					</c:forEach>
					<tr>
						<td>Sum</td>
						<td></td>
						<td id="sum_price" ></td>
					</tr>
				</tbody>
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
	
	<script>
		var sum = 0;
		var table = document.getElementById("cart_table");
		for (var i = 1; i < table.rows.length; i++){
			sum += Number(table.rows[i].cells[2].innerHTML);
		}
		document.getElementById("sum_price").innerHTML=sum;
	</script>
</body>
</html>