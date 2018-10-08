<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Show products</title>
</head>
<body>
	<h1>This is all exist products.</h1>
	<table border="1">
		<thead>
			<tr>
				<td>ID</td>
				<td>Name</td>
				<td>Price(cent)</td>
				<td>Stock</td>
				<td>Create time</td>
				<td>Last update time</td>
				<td></td>
				<td></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${products}" var="prod">
				<tr>
					<td><c:out value="${prod.id}"></c:out></td>
					<td><c:out value="${prod.name}" /></td>
					<td><c:out value="${prod.price}" /></td>
					<td><c:out value="${prod.stock}" /></td>
					<td><c:out value="${prod.createSince}"></c:out></td>
					<td><c:out value="${prod.updateSince}"></c:out></td>
					<td><form action="./modify/${prod.id}"><input type="submit" value="Modify"/></form></td>
					<td><form action="./delete/${prod.id}"><input type="submit" value="Delete"/></form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${err_msg != null }">
		<c:out value="${err_msg }"></c:out><br/>
	</c:if>
	<c:if test="${msg != null }">
		<c:out value="${msg }"></c:out><br/>
	</c:if>
	<a href=".">Back</a>
</body>
</html>