<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title> Facultative </title>
</head>

<body>
<%@ include file="parts/admin_header.jsp" %><br><br>
    <h2>Category page</h2>
    ${error}
    <form action="controller" method="post">
       <input type="hidden" name="action" value="add_course" />
       Title:   <input name="title"/><br>
       Duration: <input name="duration"/><br>
       Start date: <input type="date" name"startDate"/><br>
       Description: <input name="description" /><br>
       <input type="submit" value="enter"/>
    </form>
    <a href="controller?action=admin_courses">Back</a><br>

<div align="center">
            <table border="1" cellpadding="2">
                <caption><h2>All categories in facultative</h2></caption>
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                </tr>
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td><c:out value="${category.title}" /></td>
                        <td><c:out value="${category.description}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
</body>
</html>