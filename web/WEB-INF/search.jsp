<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>App Inventory</title>
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
            <h2>Admin Menu</h2>
            <a href="admin">Manage Users</a><br>
            <a href="categories">Manage Categories</a><br>
            <a href="search">App Inventory</a>
        </div>
        <h2>Search Inventory</h2>
        <form action="search" method="post">
            <input type="search" name="q">
            <input type="submit" value="Search">
        </form>
        <h2>Items</h2>
        
        <c:if test="${emptyList != null}"><p class="messages">No items for search result</p></c:if>
        
        <c:if test="${emptyList == null}">
            <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>Owner</th>
            </tr>
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>${item.itemId}</td>
                    <td>${item.itemName}</td>
                    <td>${item.price}</td>
                    <td>${item.owner.email}</td>
                </tr>
            </c:forEach>
        </table>
        </c:if>
    </body>
</html>
