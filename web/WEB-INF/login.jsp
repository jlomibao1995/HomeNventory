<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form method="post" action="login">
            Email: <input type="text" name="email" value="${email}"> <br>
            Password: <input type="password" name="password" value="${password}"><br>
            <input type="submit" values="Login">
        </form>
        <a href="register">Register</a>
        <a href="reset">Forgot password</a>
        <c:if test="${message != null}">
            <p>${message}</p>
        </c:if>
    </body>
</html>
