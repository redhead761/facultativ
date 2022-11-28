<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title> Facultative </title>
</head>

<body>

<%@ include file="parts/admin_header.jsp" %><br><br>
    <a href="category_form.jsp">Add category</a><br><br>
    <a href="admin.jsp">Back</a><br><br>

<div align="center">
            <table border="1" cellpadding="3">
                <caption><h2>All categories in facultative</h2></caption>
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td><c:out value="${category.title}" /></td>
                        <td><c:out value="${category.description}" /></td>
                        <td>
                            <a href="controller?action=edit_category&category_id=<c:out value='${category.id}'/>">Edit   </a>
                            <a href="controller?action=delete_category&category_id=<c:out value='${category.id}'/>">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
</body>
</html>