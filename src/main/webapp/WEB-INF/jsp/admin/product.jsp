<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Show products</title>
	<script type="text/javascript" src="/IntelliBuy/resources/statics/js/require.js" data-main="/IntelliBuy/resources/statics/js/main.js"></script>
	
</head>
<body>
	<h1>This is all exist products.</h1>
	<form action="search">
		Search:<input type="text" name="search_prod_name"/>
		<input type="submit" value="Search"/>
	</form>
	<table border="1" id="prod_list">
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
				<tr style="display: none">
					<td><c:out value="${prod.id}"></c:out></td>
					<td><c:out value="${prod.name}" /></td>
					<td><c:out value="${prod.price}" /></td>
					<td><c:out value="${prod.stock}" /></td>
					<td><c:out value="${prod.createSince}"></c:out></td>
					<td><c:out value="${prod.updateSince}"></c:out></td>
					<td><form action="modify/${prod.id}"><input type="submit" value="Modify"/></form></td>
					<td><form action="delete/${prod.id}"><input type="submit" value="Delete"/></form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a onclick="previous()">Previous</a>
	<a onclick="next()">Next</a>
	<input type="text" id="to_page_no"/>
	<button onclick="toPage()">Go</button><br/>
	<c:if test="${err_msg != null }">
		<c:out value="${err_msg }"></c:out><br/>
	</c:if>
	<c:if test="${msg != null }">
		<c:out value="${msg }"></c:out><br/>
	</c:if>
	<a href="../">Back</a>
	<script type="text/javascript">
		var page;
		function display(pageNo){
			require(["table-split-page"], function(table){
				if (table.toPage(pageNo, document.getElementById("prod_list"))){
					page=pageNo;
				}
			})
		}
		function toPage() {
			display(document.getElementById("to_page_no").value);
		}
		function next(){
			display(page+1);
		}
		function previous(){
			display(page-1);
		}
		display(1);
		
	</script>
</body>
</html>