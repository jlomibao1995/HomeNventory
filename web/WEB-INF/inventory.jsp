<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory</title>
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
            <h2>Inventory for ${user.firstName} ${user.lastName}</h2>
            <table class="blueTable">
                <thead>
                    <tr>
                        <th>Category</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Delete</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="3">
                            <div class="links"><a href="#">&laquo;</a> <a class="active" href="#">1</a> <a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">&raquo;</a></div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <c:forEach var="item" items="${user.itemList}">
                        <tr>
                            <td>${item.category.categoryName}</td>
                            <td>${item.itemName}</td>
                            <td><fmt:formatNumber value="${item.price}" type="currency" /></td>
                            <td>
                                <form action="inventory" method="post">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="itemid" value="${item.itemId}">
                                    <input type="submit" value="Delete">
                                </form>
                            </td>
                            <td>
                                <form action="inventory" method="get">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="itemedit" value="${item.itemId}">
                                    <input type="submit" value="Edit">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div>
            <c:choose>
                <c:when test="${itemedit == null}"><h2>Add Item</h2></c:when>
                <c:when test="${itemedit != null}"><h2>Edit</h2></c:when>
            </c:choose>
            <form action="inventory" method="post">
                Category:<select name="categoryId">
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.categoryId}"
                                <c:if test="${itemedit.category.categoryId == category.categoryId}">selected</c:if>
                                >${category.categoryName}</option>
                    </c:forEach>
                </select><br>
                Name: <input type="text" name="itemname" value="${itemedit.itemName}"><br>
                Price:<input type="text" name="price" value="${itemedit.price}"><br>
                <c:choose>
                    <c:when test="${itemedit == null}"><input type="hidden" name="action" value="add"></c:when>
                    <c:when test="${itemedit != null}">
                        <input type="hidden" name="action" value="save">
                        <input type="hidden" name="itemid" value="${itemedit.itemId}">
                    </c:when>
                </c:choose>
                <input type="submit" value="Save">
            </form>
            <c:choose>
                <c:when test="${message eq 'add'}"><p>Item added.</p></c:when>
                <c:when test="${message eq 'save'}"><p>Item updated.</p></c:when>
                <c:when test="${message eq 'delete'}"><p>Item deleted.</p></c:when>
                <c:when test="${message eq 'fail'}"><p>Error: Make sure all required fields are entered properly.</p></c:when>
            </c:choose>
        </div>
    </body>
</html>
