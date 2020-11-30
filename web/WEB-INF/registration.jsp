<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <c:if test="${activated != null}">
            <h1>User Activation</h1>
            <c:choose>
                <c:when test="${activated}"><p>User has been successfully activated. Please login to access the app.</p></c:when>
                <c:otherwise><p>Something went wrong. User has not been activated.</p></c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${activated == null}">
            <h1>Registration</h1>
            <form action="register" method="post">
                <label>Email:</label> <input type="text" name="email"><br>
                <label>First Name:</label> <input type="text" name="firstname"><br>
                <label>Last name:</label> <input type="text" name="lastname"><br>
                <label>Password:</label> <input type="text" name="password"><br>
                <label><input type="submit" value="Register"></label>
            </form>
            <c:choose>
                <c:when test="${success == true}">
                    <p>User added successfully.If the address you entered is valid, you will receive an email very soon.
                        Please check your email for your password.</p>
                    </c:when>
                    <c:when test="${success == false}">
                    <p>Encountered Error: Make sure all requirements are entered.</p>
                </c:when>
            </c:choose>
        </c:if>
        <a href="login">Login</a>
    </body>
</html>
