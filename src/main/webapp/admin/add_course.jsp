<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>
<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="nowFormatted"/>

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
<div class="row">
    <div class="col">
        <jsp:include page="../parts/admin_header.jsp"/>
    </div>
    <div class="col-auto">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=manage_courses">Back to
                    courses</a>
            </li>
        </ul>
    </div>
</div>

<div align="center">
    <h2>Please fill in the fields</h2>

    <c:if test="${sessionScope.message != null}">
        <div class="alert alert-warning alert-dismissible fade show col-lg-2" role="alert">
            <strong>${sessionScope.message}</strong>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="action" value="add_course"/>
        <div class="form-floating mt-4 mb-3 col-lg-3">
            <input class="form-control" name="title" id="floatingInputTitle" placeholder="title"
                   value="${sessionScope.title}"
                   pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9\\s\\-_,\\.:;()''\'\'#№]{1,30}"
                   title="Title must contain 1 to 3 characters" required>
            <label for="floatingInputTitle">Title</label>
        </div>
        <div class="form-floating mt-4 mb-3 col-lg-3">
            <input class="form-control" name="duration" id="floatingInputDuration" placeholder="duration"
                   value="${sessionScope.duration}"
                   pattern="^[0-9]{0,3}" title="Duration must contain a value from 1 to 999" required>
            <label for="floatingInputDuration">Duration</label>
        </div>
        <div class="form-floating mt-4 mb-3 col-lg-3">
            <input class="form-control" type="date" name="start_date" id="floatingInputDate" placeholder="date"
                   value="${sessionScope.start_date}" required min="${nowFormatted}">
            <label for="floatingInputDate">Start date</label>
        </div>
        <div class="form-floating mt-4 mb-3 col-lg-3">
            <input class="form-control" name="description" id="floatingInputDescription" placeholder="description"
                   value="${sessionScope.description}"
                   pattern="^[\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={} ]{0,200}"
                   title="Description must contain 0 to 200 characters">
            <label for="floatingInputDescription">Description</label>
        </div>
        <div class="mt-2 col-lg-3">
            <select name="category" class="form-select form-select-lg mb-3" required>
                <option disabled selected value="">Select category</option>
                <c:forEach var="category" items="${sessionScope.categories}">
                    <option value="${category.id}">${category.title}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mt-2 col-lg-3">
            <select name="status" class="form-select form-select-lg mb-3" required>
                <option disabled selected value="">Select status</option>
                <c:forEach var="status" items="${sessionScope.statuses}">
                    <option>${status.valueOf(status)}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<jsp:include page="/parts/footer.jsp"/>
</body>
</html>
