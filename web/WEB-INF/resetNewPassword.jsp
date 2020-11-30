<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
    </head>
    <body>
        <h1>Enter a new password</h1>
        <form method="post" aciton="reset">
            <input type="text" name="newpassword"><br>
            <input type="hidden" name="uuid" value="${uuid}">
            <input type="hidden" name="action" value="setpassword">
            <input type="submit" value="submit">
        </form>
        <c:choose>
            <c:when test="${action eq 'setpassword'}"><p>Password was successfully changed. <a href="login">Login</a></p></c:when>
            <c:when test="${action eq 'fail'}"><p>Error: Make sure all fields are entered correctly.</p></c:when>
            <c:otherwise></c:otherwise>
        </c:choose>
    </body>
</html>
