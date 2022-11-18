<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title> Facultative </title>
</head>

<body>
    <h2>Facultative</h2>
    <hr>
    Courses facultative<br>

    <c:forEach var="course" items="${requestScope.courses}">
    ${course.toString()}<br>
    </c:forEach>
    <td><a href="start?action=sortaz">Sort by A...Z</a></td> |
    <td><a href="start?action=sortza">Sort by Z...A</a></td> |
    <td><a href="start?action=sortduration">Sort by duration</a></td>


</body>

</html>
