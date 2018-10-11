<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify product</title>
</head>
<body>
	<h1>This is modify page.</h1>
	<form action="./check" method="post">
		<table border="1">
			<thead>
				<tr>
					<td></td>
					<td>Old</td>
					<td>New</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>ID:</td>
					<td><c:out value="${product.id}"></c:out></td>
					<td><input type="text" name = "id" value="${product.id}" readonly="readonly"/></td>
				</tr>
				<tr>
					<td>Name:</td>
					<td><c:out value="${product.name}"></c:out></td>
					<td>
						<c:choose>
							<c:when test="${productNew != null}">
								<input type="text" name = "name" value="${productNew.name}"/>
							</c:when>
							<c:otherwise>
								<input type="text" name = "name" value="${product.name}"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>Price(cent):</td>
					<td><c:out value="${product.price}"></c:out></td>
					<td>
						<c:choose>
							<c:when test="${productNew != null}">
								<input type="text" name = "price" value="${productNew.price}"/>
							</c:when>
							<c:otherwise>
								<input type="text" name = "price" value="${product.price}"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>Stock:</td>
					<td><c:out value="${product.stock}"></c:out></td>
					<td>
						<c:choose>
							<c:when test="${productNew != null}">
								<input type="text" name = "stock" value="${productNew.stock}"/>
							</c:when>
							<c:otherwise>
								<input type="text" name = "stock" value="${product.stock}"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>Category code:</td>
					<td><c:out value="${product.categoryCode}"></c:out></td>
					<td>
						<c:choose>
							<c:when test="${productNew != null}">
								<input type="text" name = "categoryCode" value="${productNew.categoryCode}"/>
							</c:when>
							<c:otherwise>
								<input type="text" name = "categoryCode" value="${product.categoryCode}"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>Picture url:</td>
					<td><c:out value="${product.picUrl}"></c:out></td>
					<td>
						<c:choose>
							<c:when test="${productNew != null}">
								<input type="text" name = "picUrl" value="${productNew.picUrl}"/>
							</c:when>
							<c:otherwise>
								<input type="text" name = "picUrl" value="${product.picUrl}"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>Create time:</td>
					<td><c:out value="${product.createSince}"></c:out></td>
					<td>
					</td>
				</tr>
				<tr>
					<td>Last update time:</td>
					<td><c:out value="${product.updateSince}"></c:out></td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" value="Submit"/>
	</form>
	<c:if test="${err_msg != null }">
		<c:out value="${err_msg }"></c:out><br/>
	</c:if>
	<c:if test="${msg != null }">
		<c:out value="${msg }"></c:out><br/>
	</c:if>
	<a href="../">Back</a>
</body>
</html>