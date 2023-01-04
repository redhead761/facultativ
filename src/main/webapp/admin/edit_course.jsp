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

    <c:if test="${requestScope.course_id != null}">
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="action" value="update_course"/>
            <input type="hidden" name="course_id" value="${requestScope.course_id}"/>
            <div class="form-floating mt-4 mb-3 col-lg-3">
                <input class="form-control" name="title" id="floatingInputTitle" placeholder="title"
                       value="${requestScope.course.title}"
                       pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9\\s\\-_,\\.:;()''\'\'#№]{1,30}"
                       title="Title must contain 1 to 3 characters" required>
                <label for="floatingInputTitle">Title</label>
            </div>
            <div class="form-floating mt-4 mb-3 col-lg-3">
                <input class="form-control" name="duration" id="floatingInputDuration" placeholder="duration"
                       value="${requestScope.course.duration}"
                       pattern="^[0-9]{0,3}" title="Duration must contain a value from 1 to 999" required>
                <label for="floatingInputDuration">Duration</label>
            </div>
            <div class="form-floating mt-4 mb-3 col-lg-3">
                <input class="form-control" type="date" name="start_date" id="floatingInputDate" placeholder="date"
                       value="${requestScope.course.startDate}">
                <label for="floatingInputDate">Start date</label>
            </div>
            <div class="form-floating mt-4 mb-3 col-lg-3">
                <input class="form-control" name="description" id="floatingInputDescription" placeholder="description"
                       value="${requestScope.course.description}"
                       pattern="^[\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={} ]{0,200}"
                       title="Description must contain 0 to 200 characters">
                <label for="floatingInputDescription">Description</label>
            </div>
            <div class="mt-2 col-lg-3">
                <select name="category" class="form-select form-select-lg mb-3" required>
                    <option selected value="${requestScope.course.category.id}">${requestScope.course.category.title}</option>
                    <c:forEach var="category" items="${requestScope.categories}">
                        <option value="${category.id}">${category.title}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mt-2 col-lg-3">
                <select name="status" class="form-select form-select-lg mb-3" required>
                    <option selected value="${requestScope.course.status.valueOf(requestScope.course.status)}">${requestScope.course.status.valueOf(requestScope.course.status)}</option>
                    <c:forEach var="status" items="${sessionScope.statuses}">
                        <option>${status.valueOf(status)}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mt-2 col-lg-3">
                <select name="teacher" class="form-select form-select-lg mb-3" required>
                    <option selected value="${requestScope.course.teacher.id}">${requestScope.course.teacher.name} ${requestScope.course.teacher.surname}</option>
                    <c:forEach var="teacher" items="${requestScope.teachers}">
                        <option value="${teacher.id}">${teacher.name} ${teacher.surname}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </c:if>
</div>

<jsp:include page="/parts/footer.jsp"/>
</body>
</html>
