# IntelliBuy

## Env
  * JDK 1.8.0
  * Maven
  * Tomcat 9.0 and Servlet **3.1**
  * Eclipse JAVA EE IDE **Oxygen** or newer
  * MySql 8.0.11

## Feature
    Hello Intellibuy.
    Homepage: localhost:8080/IntelliBuy
    
    Show products. 
    Add product to cart. Save in cookie.
    Checkout to create order.
    Login.
    Register.
    Save login state in cookie.
    Add user role in session.
    
    Connect database.
    Configure your database connection in com.intellibuy.service.AuthDataSource.java
    There is a init button at IndexController. Initialization tables in database and create ADMIN user.
    Default admin: username:admin, password:123456.
    Use reflection to create tables.
    Save new registered user.
    When checkout, save order.
    Login as ADMIN, you can add now products, modify products, check all orders.
    
    Security strategy:
	1.  Front-end: Javascript web api SubtleCrypto.encrypt() to encrypt password.
	2.  Back-end: Bcrypt to convert encrypted password and store in database.
	3.  CSRF token.