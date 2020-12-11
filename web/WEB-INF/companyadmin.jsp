<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home nVentory</title>
        <style>
            table, th, td {
                border: 1px solid black;
            }

            .messages {
                color: blue;
                font-size: 15px;
            }
        </style>
    </head>
    <body>
        <h1>Home nVentory</h1>
        <div>
            <h2>Menu</h2>
            <a href="inventory">Inventory</a><br>
            <a href="admin">Admin</a><br>
            <a href="account">Account</a><br>
            <a href="login?log=logout">Logout</a><br>
        </div>

        <h2>Company Admin</h2>
        <div>
            <h3>Manage Users for ${company.companyName}</h3>

            <div class="messages">
                <c:choose>
                    <c:when test="${message eq 'add'}"><p>User added</p></c:when>
                    <c:when test="${message eq 'edit'}"><p>User updated</p></c:when>
                    <c:when test="${message eq 'delete'}"><p>User deleted</p></c:when>
                    <c:when test="${message eq 'fail'}">
                        <p>Error: Make sure all required fields are entered properly</p>
                        <p>User account cannot be deleted</p>
                    </c:when>
                </c:choose>
            </div>

            <table>
                <tr>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Delete</th>
                    <th>Edit</th>
                </tr>
                <c:forEach var="account" items="${users}">
                    <tr>
                        <td>${account.email}</td>
                        <td>${account.firstName}</td>
                        <td>${account.lastName}</td>
                        <td>
                            <form action="companyadmin" method="post">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="userEmail" value="${account.email}">
                                <input type="submit" value="Delete">
                            </form>
                        </td>
                        <td>
                            <form action="companyadmin" method="get">
                                <input type="hidden" name="updateEmail" value="${account.email}">
                                <input type="submit" value="Edit">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <c:if test="${updateUser == null}">
            <h3>Add User</h3>
            <form action="companyadmin" method="post">
                Email: <input type="text" name="userEmail"><br>
                First Name: <input type="text" name="firstname"><br>
                Last name: <input type="text" name="lastname"><br>
                Password: <input type="text" name="password"><br>
                <input hidden="hidden" name="action" value="add">
                <input type="submit" value="Save">
            </form>
        </c:if>

        <c:if test="${updateUser != null}">
            <h3>Edit User</h3>
            <form action="companyadmin" method="post">
                Email: <input type="text" name="userEmail" readonly value="${updateUser.email}"><br>
                First Name: <input type="text" name="firstname" value="${updateUser.firstName}"><br>
                Last name: <input type="text" name="lastname" value="${updateUser.lastName}"><br>
                Password: <input type="text" name="password" placeholder="Enter new password or leave blank"><br>
                Active: <select name="active">
                    <option value="true" 
                            <c:if test="${updateUser.active == true}">
                                selected
                            </c:if>>Active</option>
                    <option value="false"
                            <c:if test="${updateUser.active == false}">
                                selected
                            </c:if>
                            >Non-active</option>
                </select><br>
                <input hidden="hidden" name="action" value="edit">
                <input type="submit" value="Save">
            </form>
            <form action="companyadmin" method="get">
                <input type="submit" value="Cancel">
            </form>
        </c:if>
    </body>
</html>
