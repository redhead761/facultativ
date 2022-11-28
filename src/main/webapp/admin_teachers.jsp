<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title> Facultative </title>
</head>

<body>

<%@ include file="parts/admin_header.jsp" %>
    <a href="admin.jsp">Back</a><br><br>

    <div align="center">
            <table border="1" cellpadding="3">
                <caption><h2>All courses in facultative</h2></caption>
                <tr>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="teacher" items="${teachers}">
                    <tr>
                        <td><c:out value="${teacher.name}" /></td>
                        <td><c:out value="${teacher.surname}" /></td>
                        <td>
                            <a href="controller?action=assign&teacher_id=<c:out value='${teacher.id}'/>">Assigned</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

</body>
</html>