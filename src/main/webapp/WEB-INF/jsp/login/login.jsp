<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="/IntelliBuy/resources/statics/js/require.js" data-main="/IntelliBuy/resources/statics/js/main.js"></script>
	<meta charset="UTF-8">
	<title>Login</title>
</head>
<body>
	<h1>This is login page.</h1>
	<form action="check" method="post" id="loginForm">
		Username:<input type="text" name="username" id="username"/><br />
		Password:<input type="password" name="password" id="password"/><br />
	</form>
	<button onclick="checkPassword()">Login</button>
	<script type="text/javascript">
		function checkPassword() {
			var getPassword;
			(function(username){
				var xmlhttp;
				xmlhttp = new XMLHttpRequest();
				var url = "getpassword?username=" + document.getElementById("username").value;
				xmlhttp.open("GET", url, false);
				xmlhttp.send();
				getPassword = xmlhttp.responseText;
			})(document.getElementById("username").value);
			require(["bcrypt"], function(bcrypt) {
	        	var password = document.getElementById("password").value;
	        	document.getElementById("password").value = "";
	        	if (bcrypt.compareSync(password, getPassword)) {
					setTimeout(document.getElementById("loginForm").submit(),0);
					return false;
	        	} else {
	        		document.getElementById("pwd_err_msg").innerHTML="Username or password invalid.";
	        		return false;
	        	}
			});
		}
		
		document.onkeydown = function(e){
			if ((e||event).keyCode === 13){
				checkPassword();
			}
		}
	</script>
	<div id="pwd_err_msg"></div>
	<c:if test="${err_msg != null }">
		<c:out value="${err_msg }"></c:out><br/>
	</c:if>
	<c:if test="${msg != null }">
		<c:out value="${msg }"></c:out><br/>
	</c:if>
	<a href="register">Register</a><br />
	<a href="list">Customer list</a><br />
	<a href="../">Back</a>

</body>
</html>