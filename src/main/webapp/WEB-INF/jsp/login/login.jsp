<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<h1>This is login page.</h1>
	<form action="login/check" method="post">
		User:    <input type="text" name="username"/><br />
		Password:<input type="password" name="password"/><br />
		<input type="submit" value="Login"/><br/>
	</form>
	<a href="login/register">Register</a><br />
	<a href="login/list">Customer list</a><br />
	<a href="/IntelliBuy">Back</a>
	

</body>
</html>