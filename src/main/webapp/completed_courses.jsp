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
                    <th>Grade</th>
                </tr>
                <c:forEach var="course" items="${courses}">
                <tr>
                    <td>${course.getCourse().getTitle()}</td>
                    <td><c:out value="${course.getCourse().duration}" /></td>
                    <td><c:out value="${course.getCourse().startDate}" /></td>
                    <td><c:out value="${course.getCourse().amountStudents}" /></td>
                    <td><c:out value="${course.getCourse().getCategory().title}" /></td>
                    <td><c:out value="${course.getCourse().getStatus()}" /></td>
                    <td>
                        <c:if test="${course.getCourse().getTeacher() != null}">
                            <c:out value="${course.getCourse().getTeacher().getName()} ${course.getCourse().getTeacher().getSurname()}"/>
                        </c:if>
                        <c:if test="${course.getCourse().getTeacher() == null}">
                            <a href="controller?action=show_assign_page&course_id=<c:out value='${course.id}'/>">Assigned</a>
                        </c:if>
                    </td>
                    <td><c:out value="${course.getGrade()}" /></td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>