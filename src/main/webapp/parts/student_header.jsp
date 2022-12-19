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

<div class="container-fluid bg-secondary">
    <div class="row">
        <div class="col">
            <h2> Student data </h2>
            Name: ${sessionScope.user.name}<br>
            Surname: ${sessionScope.user.surname}<br>
            Email: ${sessionScope.user.email}<br>
        </div>
        <div class="col">
            <div class="d-grid gap-2 d-md-flex p-3 mb-2 justify-content-md-end">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=show_student_courses" role="button">My courses</a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=show_coming_soon_courses" role="button">My coming
                    soon</a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=show_progress_courses" role="button">My progress
                    </a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=show_completed_courses" role="button">My complete
                    </a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=log_out" role="button">Log out</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
