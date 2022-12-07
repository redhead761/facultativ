<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title> Facultative </title>
    </head>

    <body>

        <%@ include file="../parts/admin_header.jsp" %><br><br>
        <a href="controller?action=show_category_form">Add category</a><br><br>
        <a href="controller?action=manage_courses">Back</a><br><br>

        <div align="center">
        <table border="1" cellpadding="3">
            <caption><h2>All categories in facultative</h2></caption>
            ${message}
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
                    <a href="controller?action=show_category_form&category_id=${category.id}">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="controller?action=delete_category&category_id=${category.id}">Delete</a>
                </td>
            </tr>
            </c:forEach>
        </table>
        </div>
    </body>
</html>