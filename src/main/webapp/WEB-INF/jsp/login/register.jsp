<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
	<h1>This is register page.</h1>
	<form action="register/check" method="post">
		Username:<input type="text" name="username"/><br/>
		Password:<input type="password" name="password"/><br />
		Email:<input type="text" name="email"/><br />
		<input type="submit" value="Submit" />
	</form>
	<a href="/IntelliBuy">Back</a>
	
</body>
</html>