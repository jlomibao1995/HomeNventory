<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory</title>
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
        <h2>Categories</h2>
        <div>
            <h3>Manage Categories</h3>

            <div class="messages">
                <c:choose>
                    <c:when test="${message eq 'add'}"><p>Category added.</p></c:when>
                    <c:when test="${message eq 'save'}"><p>Category updated.</p></c:when>
                    <c:when test="${message eq 'fail'}"><p>Error: Make sure all required fields are entered properly.</p></c:when>
                </c:choose>
            </div>

            <table>
                <tr>
                    <td>Category Id</td>
                    <td>Category Name</td>
                    <td>Edit Name</td>
                </tr>

                <c:forEach items="${categories}" var="category">
                    <tr>
                        <td>${category.categoryId}</td>
                        <td>${category.categoryName}</td>
                        <td>
                            <form action="categories" method="get">
                                <input type="hidden" name="editCategory" value="${category.categoryId}">
                                <input type="submit" value="Edit Name">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div>
            <c:if test="${category == null}">
                <h3>Add Category</h3>
                <form action="categories" method="post">
                    <input type="text" name="categoryName"><br>
                    <input type="hidden" name="action" value="add">
                    <input type="submit" value="Add Category">
                </form>
            </c:if>

            <c:if test="${category != null}">
                <h3>Edit Category Name</h3>
                <form action="categories" method="post">
                    <input type="text" name="categoryId" value="${category.categoryId}" readonly><br>
                    <input type="text" name="categoryName" value="${category.categoryName}"><br>
                    <input type="hidden" name="action" value="edit">
                    <input type="submit" value="Save">
                </form>
                <form action="categories" method="get">
                    <input type="submit" value="Cancel">
                </form>
            </c:if>
        </div>
    </body>
</html>
