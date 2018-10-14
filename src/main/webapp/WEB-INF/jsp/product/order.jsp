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
	<table id="order_table">
		<thead>
			<tr>
				<td>Name</td>
				<td>Number</td>
				<td>Price</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${order.products}" var="prod">
				<tr>
					<td><c:out value="${prod.name}"></c:out></td>
					<td><c:out value="${prod.number}"></c:out></td>
					<td><c:out value="${prod.number * prod.price / 100}"></c:out></td>
				</tr>		
			</c:forEach>
			<tr>
				<td>Sum</td>
				<td></td>
				<td id="sum_price" ></td>
			</tr>
		</tbody>
	</table>
	<a href="pay/${order.id}">Pay Now!</a><br/>
	<a href="cart">Back to cart</a>
	
	<script>
		var sum = 0;
		var table = document.getElementById("order_table");
		for (var i = 1; i < table.rows.length; i++){
			sum += Number(table.rows[i].cells[2].innerHTML);
		}
		document.getElementById("sum_price").innerHTML=sum;
	</script>
</body>
</html>