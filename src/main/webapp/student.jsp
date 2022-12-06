<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title> Student cabinet </title>
    </head>

    <body>
        <h2> Student data </h2><hr>
        Name: ${user.name}<br>
        Surname: ${user.surname}<br>
        Email: ${user.email}<br>
        <a href="controller?action=show_student_courses">My courses</a><br><br>
                <a href="controller?action=log_out">Log out</a><br><br>

        <form action="controller">
            <input type="hidden" name="action" value="sort" />
            <input type="hidden" name="cabinet_type" value="student" />
            Sort: <select name="sort_type">
                <option>alphabet</option>
                <option>reverse alphabet</option>
                <option>duration</option>
                <option>amount students</option>
                </select>
                <input type="submit" value="enter"/>
        </form>

        <form action="controller">
            <input type="hidden" name="action" value="select_courses" />
            <input type="hidden" name="type" value="by_teacher" />
            <input type="hidden" name="cabinet_type" value="student" />
            Select from teacher: <select name="teacher_id">
                <c:forEach var="teacher" items="${teachers}">
                <option value="${teacher.id}">${teacher.name} ${teacher.surname} </option>
                </c:forEach>
                </select>
                <input type="submit" value="enter"/>
        </form>

        <form action="controller">
                <input type="hidden" name="action" value="select_courses" />
                <input type="hidden" name="type" value="by_category" />
                <input type="hidden" name="cabinet_type" value="student" />
                Select from category: <select name="category_id">
                    <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.title}</option>
                    </c:forEach>
                    </select>
                    <input type="submit" value="enter"/>
        </form>

        <div align="center">
            <table border="1" cellpadding="8">
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
                    <th>Action</th>
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
                    <td>
                        <a href="controller?action=enroll&course_id=${course.id}">Enroll</a>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>