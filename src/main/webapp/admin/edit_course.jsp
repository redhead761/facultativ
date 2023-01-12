<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<c:if test="${sessionScope.message != null}">
    <div class="alert alert-warning alert-dismissible fade show col-lg-2" role="alert">
        <strong>${sessionScope.message}</strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<c:if test="${requestScope.course_id != null}">
    <section class="vh-60">
        <div class="container py-5 h-100">
            <div class="row justify-content-center align-items-center h-100">
                <div class="col-12 col-lg-9 col-xl-7">
                    <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                        <div class="card-body p-4 p-md-5">
                            <h3 class="mb-4 pb-2 pb-md-0 mb-md-5">Add Course Form</h3>
                            <form action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="action" value="update_course"/>
                                <input type="hidden" name="course_id" value="${requestScope.course_id}"/>

                                <div class="row">
                                    <div class="col-md-6 mb-4">

                                        <div class="form-floating">
                                            <input type="text" id="title" class="form-control form-control-lg"
                                                   value="${requestScope.course.title}"
                                                   pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9\\s\\-_,\\.:;()''\'\'#№]{1,30}"
                                                   title="Title must contain 1 to 3 characters" required
                                                   name="title"/>
                                            <label class="form-label" for="title">Title</label>
                                        </div>

                                    </div>

                                    <div class="col-md-6 mb-4">

                                        <div class="form-floating">
                                            <input type="number" id="duration" class="form-control form-control-lg"
                                                   name="duration"
                                                   value="${requestScope.course.duration}"
                                                   min="1" max="999"
                                                   title="Duration must contain a value from 1 to 999" required/>
                                            <label class="form-label" for="duration">Duration</label>
                                        </div>

                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-4 d-flex align-items-center">

                                        <div class="form-floating ">
                                            <input type="date" class="form-control form-control-lg" id="start_date"
                                                   name="start_date" value="${requestScope.course.startDate}"/>
                                            <label for="start_date" class="form-label">Start date</label>
                                        </div>

                                    </div>

                                    <div class="col-md-6 mb-4">

                                        <h6 class="mb-2 pb-1">Status: </h6>

                                        <c:forEach var="status" items="${sessionScope.statuses}">
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="status"
                                                       id="status"
                                                       value="${status.valueOf(status)}" checked/>
                                                <label class="form-check-label"
                                                       for="status">${status.valueOf(status)}</label>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-12">

                                        <select name="category" class="form-select form-select-lg mb-3" required>
                                            <option disabled selected value="">Select category</option>
                                            <c:forEach var="category" items="${requestScope.categories}">
                                                <option value="${category.id}">${category.title}</option>
                                            </c:forEach>
                                        </select>

                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-12">

                                        <div class="form-floating">
                                            <input type="text" id="description" class="form-control form-control-lg"
                                                   name="description"
                                                   value="${requestScope.course.description}"
                                                   pattern="^[\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={} ]{0,200}"
                                                   title="Description must contain 0 to 200 characters"/>
                                            <label class="form-label" for="description">Description</label>
                                        </div>

                                    </div>
                                </div>

                                <div class="mt-4 pt-2">
                                    <input class="btn btn-primary btn-lg" type="submit" value="Submit"/>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="/parts/footer.jsp"/>
    </section>
</c:if>

<jsp:include page="/parts/footer.jsp"/>
</body>
</html>
