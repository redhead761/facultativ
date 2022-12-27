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

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">Facultative</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="index.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="courses.jsp">Courses</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="teachers.jsp">Teachers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="about_us.jsp">About us</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="contacts.jsp">Contacts</a>
                </li>
            </ul>

            <ul class="navbar-nav justify-content-end">
                <c:if test="${sessionScope.user == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="#"><span class="glyphicon glyphicon-user"></span>Sign Up</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#"><span class="glyphicon glyphicon-log-in"></span>Login</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=log_out"><span
                                class="glyphicon glyphicon-log-in"></span>My cabinet</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=log_out"><span
                                class="glyphicon glyphicon-log-in"></span>Logout</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
