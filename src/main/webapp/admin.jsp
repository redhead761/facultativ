<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>

<head>
    <title> Student cabinet </title>
</head>

<body>
    <h2> Admin data </h2>
    <hr>
    Name: ${user.name}<br>
    Surname: ${user.surname}<br>
    Email: ${user.email}<br>
    <a href="controller?action=add_category">Add category</a><br>
    <a href="controller?action=add_course">Add course</a><br>
    <a href="controller?action=add teacher>">Add teacher</a><br>

    <div align="center">
            <table border="1" cellpadding="7">
                <caption><h2>All courses in facultative</h2></caption>
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
                        <td><c:out value="${course.title}" /></td>
                        <td><c:out value="${course.duration}" /></td>
                        <td><c:out value="${course.startDate}" /></td>
                        <td><c:out value="${course.amountStudents}" /></td>
                        <td><c:out value="${course.getCategory().title}" /></td>
                        <td><c:out value="${course.getStatus()}" /></td>
                        <td><c:out value="${course.getTeacher().getName()} ${course.getTeacher().getSurname()}" /></td>
                        <td>
                            <a href="controller?action=enroll&courseId=<c:out value='${course.id}'/>&userId=<c:out value='${user.id}'/>">Enroll</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    <form action="controller">
        <input type="hidden" name="action" value="sort" />
        Sort: <select name="sort_type">
            <option>alphabet</option>
            <option>reverse alphabet</option>
            <option>duration</option>
            <option>amount students</option>
            </select>  <input type="submit" value="enter"/>
            </form>

        <form action="controller">
               <input type="hidden" name="role" value="STUDENT" />
               <input type="hidden" name="action" value="choosecategory" />
               Choose category:<input name="category"/>
               <button type="submit" value="enter"/>Enter</button>
        </form>
    <a href="auth.jsp">Log out</a>
</body>

</html>