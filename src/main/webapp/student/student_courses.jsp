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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<jsp:include page="../parts/header.jsp"/>
<jsp:include page="../parts/student_header.jsp"/>

<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <caption>
            All courses in facultative
        </caption>
        ${sessionScope.message}
        <thead>
        <th scope="col">Title</th>
        <th scope="col">Duration</th>
        <th scope="col">Start date</th>
        <th scope="col">Students on course</th>
        <th scope="col">Category</th>
        <th scope="col">Status</th>
        <th scope="col">Teacher</th>
        </thead>
        <c:forEach var="course" items="${requestScope.courses}">
            <tbody>
            <td>${course.title}</td>
            <td><c:out value="${course.duration}"/></td>
            <td><c:out value="${course.startDate}"/></td>
            <td><c:out value="${course.amountStudents}"/></td>
            <td><c:out value="${course.getCategory().title}"/></td>
            <td><c:out value="${course.getStatus()}"/></td>
            <td>
                <c:out value="${course.getTeacher().getName()} ${course.getTeacher().getSurname()}"/>
            </td>
            </tbody>
        </c:forEach>
    </table>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>