<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account</title>
    </head>
    <body>
        <div>
            <h1>Home nVentory</h1>
            <h2>Menu</h2>
            <a href="inventory">Inventory</a><br>
            <a href="admin">Admin</a><br>
            <a href="account">Account</a><br>
            <a href="login?log=logout">Logout</a><br>
        </div>

        <div>
            <c:if test="${edit == null}">
            <h2>Account Information</h2>
            <p>Name: ${user.firstName} ${user.lastName}</p>
            <p>Email: ${user.email}</p>
            <p>Role: ${user.role.roleName}</p>
            <a href="account?edit=edit">Edit Information</a>
        </c:if>
        </div>
        <c:if test="${result eq 'success'}">Account information updated.</c:if>

        <div>
            <c:if test="${edit eq 'edit'}">
                <form method="post" action="account">
                    Email: <input type="text" name="email" value="${user.email}" readonly><br>
                    First Name: <input type="text" name="firstname" value="${user.firstName}"><br>
                    Last Name: <input type="text" name="lastname" value="${user.lastName}"><br>
                    Password: <input type="text" name="password" placeholder="Enter new password or keep blank"><br>
                    Confirm Password: <input type="text" name="confirmPassword" placeholder="Enter password"><br>
                    Active: <select name="active">
                        <option value="${true}" 
                                <c:if test="${user.active == true}">
                                    selected
                                </c:if>>Active</option>
                        <option value="${false}"
                                <c:if test="${user.active == false}">
                                    selected
                                </c:if>
                                >Non-active</option>
                    </select><br>
                    <input type="submit" value="Save">
                </form>
                <form action="account" method="get">
                    <input type="submit" value="Cancel">
                </form>
            </c:if>
        </div>
        <c:if test="${result eq 'fail'}">Account information could not be updated.</c:if>
    </body>
</html>
