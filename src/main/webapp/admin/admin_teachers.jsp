<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title> Facultative </title>
    </head>

    <body>
        <%@ include file="../parts/admin_header.jsp" %>
        <a href="admin/add_teacher.jsp">Add teacher</a><br><br>
        <a href="controller?action=manage_courses">Back</a><br><br>

        <div align="center">
            <table border="1" cellpadding="2">
                <caption><h2>All teachers in facultative</h2></caption>
                <tr>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Email</th>
                </tr>
                <c:forEach var="teacher" items="${teachers}">
                    <tr>
                        <td><c:out value="${teacher.name}" /></td>
                        <td><c:out value="${teacher.surname}" /></td>
                        <td><c:out value="${teacher.email}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    </body>
</html>