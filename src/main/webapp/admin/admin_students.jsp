<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title> Facultative </title>
    </head>

    <body>

        <%@ include file="../parts/admin_header.jsp" %>
        <a href="controller?action=manage_courses">Back</a><br><br>

        <div align="center">
            <table border="1" cellpadding="4">
            <caption><h2>All students in facultative</h2></caption>
            <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <c:forEach var="student" items="${students}">
                <tr>
                    <td><c:out value="${student.name}" /></td>
                    <td><c:out value="${student.surname}" /></td>
                    <td><c:out value="${student.isBlock()}" /></td>
                    <td>
                        <a href="controller?action=block&student_id=<c:out value='${student.id}'/>">Block</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="controller?action=unblock&student_id=<c:out value='${student.id}'/>">Unblock</a>
                    </td>
                </tr>
            </c:forEach>
            </table>
        </div>

    </body>
</html>