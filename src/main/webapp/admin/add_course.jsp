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
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?action=manage_courses"><fmt:message
                        key="back.courses"/></a>
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

<section class="vh-60">
    <div class="container py-5 h-100">
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-12 col-lg-9 col-xl-7">
                <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                    <div class="card-body p-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5"><fmt:message key="add.course.form"/></h3>
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="action" value="add_course"/>

                            <div class="row">
                                <div class="col-md-6 mb-4">

                                    <div class="form-floating">
                                        <input type="text" id="title" class="form-control form-control-lg"
                                               value="${sessionScope.course.title}"
                                               pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9\\s\\-_,\\.:;()''\'\'#№]{1,30}"
                                               title="Title must contain 1 to 3 characters" required name="title"/>
                                        <label class="form-label" for="title"><fmt:message key="title"/></label>
                                    </div>

                                </div>

                                <div class="col-md-6 mb-4">

                                    <div class="form-floating">
                                        <input type="number" id="duration" class="form-control form-control-lg"
                                               name="duration"
                                               value="${sessionScope.course.duration}"
                                               min="1" max="999" title="Duration must contain a value from 1 to 999"
                                               required/>
                                        <label class="form-label" for="duration"><fmt:message key="duration"/></label>
                                    </div>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-4 d-flex align-items-center">

                                    <div class="form-floating ">
                                        <input type="date" class="form-control form-control-lg" id="start_date"
                                               name="start_date" value="${sessionScope.course.startDate}" required
                                               min="${nowFormatted}"/>
                                        <label for="start_date" class="form-label"><fmt:message
                                                key="start.date"/></label>
                                    </div>

                                </div>

                                <div class="col-md-6 mb-4">

                                    <h6 class="mb-2 pb-1"><fmt:message key="status"/>:</h6>

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
                                        <option disabled selected value=""><fmt:message key="select.category"/></option>
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
                                               value="${sessionScope.course.description}"
                                               pattern="^[\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={} ]{0,200}"
                                               title="Description must contain 0 to 200 characters"/>
                                        <label class="form-label" for="description"><fmt:message
                                                key="description"/></label>
                                    </div>

                                </div>
                            </div>

                            <div class="mt-4 pt-2">
                                <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="/parts/footer.jsp"/>
</section>
${sessionScope.remove("message")}
${sessionScope.remove("course")}
</body>
</html>
