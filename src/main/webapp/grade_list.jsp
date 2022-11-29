<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title> Facultative </title>
</head>

<body>


    <a href="admin.jsp">Back</a><br><br>

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
                                <form action="controller" method="post">
                                   <input type="hidden" name="action" value="grade" />
                                   <input type="hidden" name="course_id" value="${course_id}" />
                                   Grade:   <input name="grade"/><br>
                                   <input type="submit" value="enter"/>
                                </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

</body>
</html>