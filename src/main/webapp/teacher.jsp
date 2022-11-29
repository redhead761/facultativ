<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>

<head>
    <title> Teacher cabinet </title>
</head>

<body>
    <h2> Teacher data </h2>
    <hr>
    Name: ${user.name}<br>
    Surname: ${user.surname}<br>
    Email: ${user.email}<br>

    <a href="controller?action=log_out">Log out</a>

        <div align="center">
                <table border="1" cellpadding="8">
                    <caption><h2>All my courses in facultative</h2></caption>
                    <tr>
                        <th>Title</th>
                        <th>Duration</th>
                        <th>Start date</th>
                        <th>Students on course</th>
                        <th>Category</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    <c:forEach var="course" items="${coursesTeacher}">
                        <tr>
                            <td><c:out value="${course.title}" /></td>
                            <td><c:out value="${course.duration}" /></td>
                            <td><c:out value="${course.startDate}" /></td>
                            <td><c:out value="${course.amountStudents}" /></td>
                            <td><c:out value="${course.getCategory().title}" /></td>
                            <td><c:out value="${course.getStatus()}" /></td>
                            <td>
                                <a href="controller?action=show_grade_list&course_id=<c:out value='${course.id}'/>">Grade</a>

                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
</body>

</html>