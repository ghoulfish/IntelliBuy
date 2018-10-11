<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script type="text/javascript" src="/IntelliBuy/resources/statics/js/require.js" data-main="/IntelliBuy/resources/statics/js/main.js"></script>
	<script type="text/javascript" src="/IntelliBuy/resources/statics/js/sha-256.js"></script>
	<link rel="stylesheet" type="text/css" href="/IntelliBuy/resources/statics/css/registerForm.css"/>
	<title>Register</title>
	
</head>
<body>
	<h1>This is register page.</h1>
	<form action="register/check" method="post" id="registerForm" onsubmit="return alarm();">
		Username:<input type="text" name="username"/><br/>
		Password:<input type="password" name="password" id="password"/><br />
		Email:<input type="text" name="email"/><br />
		<input type="submit" value="Submit" onclick="checkPassword()"/>
	</form>
	<div id="message">
	  <h3>Password must contain the following:</h3>
	  <p id="letter" class="invalid">A <b>lowercase</b> letter</p>
	  <p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
	  <p id="number" class="invalid">A <b>number</b></p>
	  <p id="length" class="invalid">Minimum <b>8 characters</b></p>
	</div>
	<p><c:out value="${err_msg}"></c:out></p>
	<a href="/IntelliBuy">Back</a>
	<script type="text/javascript">
		var myInput = document.getElementById("password");
		var letter = document.getElementById("letter");
		var capital = document.getElementById("capital");
		var number = document.getElementById("number");
		var length = document.getElementById("length");
	
		// When the user clicks on the password field, show the message box
		/* myInput.onfocus = function() {
		  document.getElementById("message").style.display = "block";
		}
	
		// When the user clicks outside of the password field, hide the message box
		myInput.onblur = function() {
		  document.getElementById("message").style.display = "none";
		} */
	
		// When the user starts to type something inside the password field
		myInput.onkeyup = function() {
		  // Validate lowercase letters
		  var lowerCaseLetters = /[a-z]/g;
		  if(myInput.value.match(lowerCaseLetters)) { 
		    letter.classList.remove("invalid");
		    letter.classList.add("valid");
		  } else {
		    letter.classList.remove("valid");
		    letter.classList.add("invalid");
		}
	
		  // Validate capital letters
		  var upperCaseLetters = /[A-Z]/g;
		  if(myInput.value.match(upperCaseLetters)) { 
		    capital.classList.remove("invalid");
		    capital.classList.add("valid");
		  } else {
		    capital.classList.remove("valid");
		    capital.classList.add("invalid");
		  }
	
		  // Validate numbers
		  var numbers = /[0-9]/g;
		  if(myInput.value.match(numbers)) { 
		    number.classList.remove("invalid");
		    number.classList.add("valid");
		  } else {
		    number.classList.remove("valid");
		    number.classList.add("invalid");
		  }
	
		  // Validate length
		  if(myInput.value.length >= 8) {
		    length.classList.remove("invalid");
		    length.classList.add("valid");
		  } else {
		    length.classList.remove("valid");
		    length.classList.add("invalid");
		  }
		}
		function checkPassword() {
			if (letter.classList.value === "invalid" || capital.classList.value === "invalid" 
					|| number.classList.value === "invalid" || length.classList.value === "invalid") {
				return false;
			} else {
				encryptPassword();
				return true;
			}
		}
		function encryptPassword() {
			require(["bcrypt"], function(bcrypt) {
	        	var password = document.getElementById("password").value;
		        var salt = bcrypt.genSaltSync(10);
		        hash = bcrypt.hashSync(password, salt);
				document.getElementById("password").value = hash;
			});
	    }
		function alarm() {
			return false;
		}
		//var registerForm = document.getElementById("registerForm");
		//registerForm.addEventListener("submit", checkPassword, false);
	</script>
	<!-- <button onclick=encryptPassword()>Button</button> -->
</body>
</html>