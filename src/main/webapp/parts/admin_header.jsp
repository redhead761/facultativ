<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>


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
            <h2> <fmt:message key="admin.title"/></h2>
            <fmt:message key="name"/>: ${user.name}<br>
            <fmt:message key="surname"/>: ${user.surname}<br>
            <fmt:message key="email"/>: ${user.email}<br>
        </div>
        <div class="col">
            <div class="d-grid gap-2 d-md-flex p-3 mb-2 justify-content-md-end">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=manage_courses" role="button"><fmt:message key="manage.courses"/></a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=manage_categories" role="button"><fmt:message key="manage.categories"/></a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=manage_teachers" role="button"><fmt:message key="manage.teachers"/></a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=manage_students" role="button"><fmt:message key="manage.students"/></a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=log_out" role="button"><fmt:message key="auth.logout"/></a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
