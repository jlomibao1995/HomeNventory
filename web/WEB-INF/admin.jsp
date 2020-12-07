<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory</title>
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
        <h2>Admin</h2>
        <div>
            <h3>Manage Users</h3>
            <table>
                <tr>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th>Company</th>
                    <th>Delete</th>
                    <th>Edit</th>
                    <th>Manage Roles</th>
                </tr>
                <c:forEach var="account" items="${users}">
                    <tr>
                        <td>${account.email}</td>
                        <td>${account.firstName}</td>
                        <td>${account.lastName}</td>
                        <td>${account.role.roleName}</td>
                        <td>${account.company.companyName}</td>

                        <td>
                            <form action="admin" method="post">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="userEmail" value="${account.email}">
                                <input type="submit" value="Delete">
                            </form>
                        </td>

                        <td>
                            <form action="admin" method="get">
                                <input type="hidden" name="updateEmail" value="${account.email}">
                                <input type="submit" value="Edit">
                            </form>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${account.role.roleId == 1}">
                                    <form action="admin" method="post">
                                        <input type="hidden" name="action" value="regularuser">
                                        <input type="hidden" name="roleUser" value="${account.email}">
                                        <input type="submit" value="Demote to Regular User"><br>
                                    </form>
                                    <form action="admin" method="post">
                                        <input type="hidden" name="action" value="companyadmin">
                                        <input type="hidden" name="roleUser" value="${account.email}">
                                        <input type="submit" value="Demote to Company Admin"><br>
                                    </form>
                                </c:when>
                                <c:when test="${account.role.roleId == 3}">
                                    <form action="admin" method="post">
                                        <input type="hidden" name="action" value="systemadmin">
                                        <input type="hidden" name="roleUser" value="${account.email}">
                                        <input type="submit" value="Promote to System Admin"><br>
                                    </form>
                                    <form action="admin" method="post">
                                        <input type="hidden" name="action" value="regularuser">
                                        <input type="hidden" name="roleUser" value="${account.email}">
                                        <input type="submit" value="Demote to Regular User"><br>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="admin" method="post">
                                        <input type="hidden" name="action" value="systemadmin">
                                        <input type="hidden" name="roleUser" value="${account.email}">
                                        <input type="submit" value="Promote to System Admin"><br>
                                    </form>
                                        <form action="admin" method="post">
                                            <input type="hidden" name="action" value="companyadmin">
                                        <input type="hidden" name="roleUser" value="${account.email}">
                                        <input type="submit" value="Promote to Company Admin"><br>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <c:if test="${updateUser == null && setCompany == null}">
            <h3>Add User</h3>
            <form action="admin" method="post">
                Email: <input type="text" name="userEmail"><br>
                First Name: <input type="text" name="firstname"><br>
                Last name: <input type="text" name="lastname"><br>
                Password: <input type="text" name="password"><br>
                Company: <select name="companyId">
                    <c:forEach items="${companies}" var="company">
                        <option value="${company.companyId}">${company.companyName}</option>
                    </c:forEach>
                </select><br>
                <input hidden="hidden" name="action" value="add">
                <input type="submit" value="Save">
            </form>
        </c:if>

        <c:if test="${updateUser != null}">
            <h3>Edit User</h3>
            <form action="admin" method="post">
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
            <form action="admin" method="get">
                <input type="submit" value="Cancel">
            </form>
        </c:if>

        <c:choose>
            <c:when test="${message eq 'add'}"><p>User added.</p></c:when>
            <c:when test="${message eq 'edit'}"><p>User updated.</p></c:when>
            <c:when test="${message eq 'delete'}"><p>User deleted.</p></c:when>
            <c:when test="${message eq 'systemadmin'}"><p>User has been promoted to system admin.</p></c:when>
            <c:when test="${message eq 'regularuser'}"><p>User has been demoted to regular user.</p></c:when>
            <c:when test="${message eq 'companyadmin'}"><p>User role has been changed to company administrator.</p></c:when>
            <c:when test="${message eq 'fail'}"><p>Error: Make sure all required fields are entered properly.</p></c:when>
        </c:choose>
    </body>
</html>
