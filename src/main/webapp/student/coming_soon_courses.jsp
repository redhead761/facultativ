<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title> Student cabinet </title>
    </head>

    <body>
        <a href="controller?action=show_coming_soon_courses">COMING SOON COURSES</a><br><br>
        <a href="controller?action=show_progress_courses">IN PROGRESS COURSES</a><br><br>
        <a href="controller?action=show_completed_courses">COMPLETED COURSE</a><br><br>
        <a href="controller?action=show_student_courses">Back</a><br><br>




        <div align="center">
            <table border="1" cellpadding="7">
                <caption><h2>All courses in facultative</h2></caption>
                ${message}
                <tr>
                    <th>Title</th>
                    <th>Duration</th>
                    <th>Start date</th>
                    <th>Students on course</th>
                    <th>Category</th>
                    <th>Status</th>
                    <th>Teacher</th>
                </tr>
                <c:forEach var="course" items="${courses}">
                <tr>
                    <td>${course.title}</td>
                    <td><c:out value="${course.duration}" /></td>
                    <td><c:out value="${course.startDate}" /></td>
                    <td><c:out value="${course.amountStudents}" /></td>
                    <td><c:out value="${course.getCategory().title}" /></td>
                    <td><c:out value="${course.getStatus()}" /></td>
                    <td>
                        <c:if test="${course.getTeacher() != null}">
                            <c:out value="${course.getTeacher().getName()} ${course.getTeacher().getSurname()}"/>
                        </c:if>
                        <c:if test="${course.getTeacher() == null}">
                            <a href="controller?action=show_assign_page&course_id=<c:out value='${course.id}'/>">Assigned</a>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>