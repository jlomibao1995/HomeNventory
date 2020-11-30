<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset password</title>
    </head>
    <body>
        <h1>Reset password</h1>
        <p>Please enter your email address to reset your password.</p>
        <form method="post" action="reset">
            Email Address: <input type="text" name="email"><br>
            <input type="hidden" name="action" value="requestreset">
            <input type="submit" value="Submit">
        </form>
        <c:choose>
            <c:when test="${action eq 'requestreset'}"><p>If the address you entered is valid, you will receive an email very soon.
                    Please check your email for your password.</p></c:when>
            <c:when test="${action eq 'fail'}"><p>Error: Make sure all fields are entered correctly.</p></c:when>
            <c:otherwise></c:otherwise>
        </c:choose>
        <a href="login">Login</a>
    </body>
</html>
