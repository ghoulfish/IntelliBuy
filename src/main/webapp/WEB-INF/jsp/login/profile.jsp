<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update your profile</title>
</head>
<body>
	<h1>This is an update profile page</h1>
	<form>
		<table>
			<thead>
				<tr>
					<td></td>
					<td>Old</td>
					<td>New</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Your name:</td>
					<td><c:out value="${customer.name}"></c:out></td>
					<td><input type="text" name="name"/></td>
				</tr>
				<tr>
					<td>Your phone:</td>
					<td><c:out value="${customer.phone}"></c:out></td>
					<td><input type="text" name="phone"/></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>