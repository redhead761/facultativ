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

<jsp:include page="../parts/admin_header.jsp"/>

<div class="col">
    <div class="d-grid gap-2 d-md-flex p-3 mb-2 justify-content-md-end" id="language" name="language">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/?language=en" role="button">En</a>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/?language=ua" role="button">UA</a>
    </div>
</div>

<c:if test="${message != null}">
    <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
        <strong>${message}!</strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="table-responsive col-lg-10 mx-auto p-4">
    <table class="table table-success table-striped caption-top table-bordered">
        <caption>
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuSort"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="admin.sort"/>
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=sort&cabinet_type=admin&sort_type=alphabet">Alphabetical</a>
                </li>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=sort&cabinet_type=admin&sort_type=reverse alphabet">Reverse
                    alphabetical</a></li>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=sort&cabinet_type=admin&sort_type=duration">Duration</a>
                </li>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=sort&cabinet_type=admin&sort_type=amount students">Amount
                    students</a></li>
            </ul>

            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuSelectByTeacher"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="admin.select.teacher"/>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="teacher" items="${teachers}">
                    <li><a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?action=select_courses&cabinet_type=admin&type=by_teacher&teacher_id=${teacher.id}">${teacher.name} ${teacher.surname}</a>
                    </li>
                </c:forEach>
            </ul>

            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuSelectByCategory"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="admin.select.category"/>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="category" items="${categories}">
                    <li><a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?action=select_courses&cabinet_type=admin&type=by_category&category_id=${category.id}">${category.title}</a>
                    </li>
                </c:forEach>
            </ul>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=show_course_form"
               role="button"><fmt:message key="admin.add.course"/></a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <fmt:message key="admin.table.course.name"/>
        </caption>
        <thead>
        <th scope="col"><fmt:message key="admin.table.course.title"/></th>
        <th scope="col"><fmt:message key="admin.table.course.duration"/></th>
        <th scope="col"><fmt:message key="admin.table.course.start.date"/></th>
        <th scope="col"><fmt:message key="admin.table.course.amount.student"/></th>
        <th scope="col"><fmt:message key="admin.table.course.category"/></th>
        <th scope="col"><fmt:message key="admin.table.course.status"/></th>
        <th scope="col"><fmt:message key="admin.table.course.teacher"/></th>
        <th scope="col"><fmt:message key="action"/></th>
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
                    <a href="${pageContext.request.contextPath}/controller?action=show_assign_page&course_id=<c:out value='${course.id}'/>">Assigned</a>
                </c:if>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/controller?action=show_course_form&course_id=${course.id}">Edit</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="${pageContext.request.contextPath}/controller?action=delete_course&course_id=<c:out value='${course.id}'/>">Delete</a>
            </td>
            </tbody>
        </c:forEach>
    </table>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>