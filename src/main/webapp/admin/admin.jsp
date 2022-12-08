<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title> Facultative </title>
</head>

<body>
<h2> Admin data </h2>
<hr>
Name: ${user.name}<br>
Surname: ${user.surname}<br>
Email: ${user.email}<br>
<%@ include file="../parts/admin_header.jsp" %>
<a href="controller?action=show_course_form">Add course</a><br><br>

<form action="controller">
    <input type="hidden" name="action" value="sort"/>
    <input type="hidden" name="cabinet_type" value="admin"/>
    <select name="sort_type" class="form-select" aria-label="Default select example">
        <option selected>Sort</option>
        <option>alphabet</option>
        <option>reverse alphabet</option>
        <option>duration</option>
        <option>amount students</option>
    </select>
    <input type="submit" value="enter"/>
</form>

<form action="controller">
    <input type="hidden" name="action" value="select_courses"/>
    <input type="hidden" name="type" value="by_teacher"/>
    <input type="hidden" name="cabinet_type" value="admin"/>
    <select name="teacher_id" class="form-select" aria-label="Default select example">
        <option selected>Select from teacher</option>
        <c:forEach var="teacher" items="${teachers}">
            <option value="${teacher.id}">${teacher.name} ${teacher.surname} </option>
        </c:forEach>
    </select>
    <input type="submit" value="enter"/>
</form>

<form action="controller">
    <input type="hidden" name="action" value="select_courses"/>
    <input type="hidden" name="type" value="by_category"/>
    <input type="hidden" name="cabinet_type" value="admin"/>
    <select name="category_id" class="form-select" aria-label="Default select example">
        <option selected>Select from category</option>
        <c:forEach var="category" items="${categories}">
            <option value="${category.id}">${category.title}</option>
        </c:forEach>
    </select>
    <input type="submit" value="enter"/>
</form>

<div class="table-responsive">
    <table class="table table-success table-striped caption-top table-bordered ">
        <caption>
            <h2 class="display-6 text-center">All courses in facultative</h2>
        </caption>
        ${message}
        <thead>
        <th scope="col">Title</th>
        <th scope="col">Duration</th>
        <th scope="col">Start date</th>
        <th scope="col">Students on course</th>
        <th scope="col">Category</th>
        <th scope="col">Status</th>
        <th scope="col">Teacher</th>
        <th scope="col">Action</th>
        </thead>
        <c:forEach var="course" items="${courses}">
            <tbody>
            <td>${course.title}</td>
            <td><c:out value="${course.duration}"/></td>
            <td><c:out value="${course.startDate}"/></td>
            <td><c:out value="${course.amountStudents}"/></td>
            <td><c:out value="${course.getCategory().title}"/></td>
            <td><c:out value="${course.getStatus()}"/></td>
            <td>
                <c:if test="${course.getTeacher() != null}">
                    <c:out value="${course.getTeacher().getName()} ${course.getTeacher().getSurname()}"/>
                </c:if>
                <c:if test="${course.getTeacher() == null}">
                    <a href="controller?action=show_assign_page&course_id=<c:out value='${course.id}'/>">Assigned</a>
                </c:if>
            </td>
            <td>
                <a href="controller?action=show_course_form&course_id=${course.id}">Edit</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="controller?action=delete_course&course_id=<c:out value='${course.id}'/>">Delete</a>
            </td>
            </tbody>
        </c:forEach>
    </table>
</div>
</body>
</html>