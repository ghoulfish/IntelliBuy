<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add product</title>
</head>
<body>
	<h1>This is add product page.</h1>
	<form action="add/check" method="post">
		<!-- Name:<input type="text" name="name" /><br/>
		Price:<input type="number" name="price"/><br/> -->
		<table>
			<thead>
				<tr>
					<td></td>
					<td>New</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Name:</td>
					<td><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td>Price (cents):</td>
					<td><input type="number" name="price"/></td>
				</tr>
				<tr>
					<td>Stock:</td>
					<td><input type="number" name="stock" value="0"/></td>
				</tr>
				<tr>
					<td>Category code:</td>
					<td><input type="number" name="categoryCode" value="0"/></td>
				</tr>
				<tr>
					<td>Picture url:</td>
					<td><input type="text" name="picUrl"/></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" value="Submit"/><br/>
	</form>	
	<c:if test="${err_msg != null }">
		<c:out value="${err_msg }"></c:out><br/>
	</c:if>
	<c:if test="${msg != null }">
		<c:out value="${msg }"></c:out><br/>
	</c:if>
	<a href="./">Back</a>
	
</body>
</html>