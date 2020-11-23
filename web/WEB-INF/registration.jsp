<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <h1>Registration</h1>
        <form action="register" method="post">
            <label>Email:</label> <input type="text" name="email"><br>
            <label>First Name:</label> <input type="text" name="firstname"><br>
            <label>Last name:</label> <input type="text" name="lastname"><br>
            <label>Password:</label> <input type="text" name="password"><br>
            <label><input type="submit" value="Register"></label>
        </form>
        <a href="login">Login</a>
        <c:choose>
            <c:when test="${success == true}">
                <p>User added successfully</p>
            </c:when>
            <c:when test="${success == false}">
                <p>Encountered Error: Make sure all requirements are entered.</p>
            </c:when>
        </c:choose>
    </body>
</html>
