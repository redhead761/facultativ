<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.language}">
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
<jsp:include page="/parts/header.jsp"/>

<div class="text-center">
    <h2><fmt:message key="all.course"/></h2>
    <hr>
</div>


<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <div class="row justify-content-between">
            <tags:param_cap action="courses"/>
            <div class="col-md-auto">
                <a class="btn btn-outline-secondary btn-sm mt-3"
                   href="${pageContext.request.contextPath}/controller?action=download_courses"
                   role="button"><fmt:message key="download.courses"/></a>
            </div>
        </div>
        <thead>
        <th scope="col"><fmt:message key="title"/></th>
        <th scope="col"><fmt:message key="duration"/></th>
        <th scope="col"><fmt:message key="start.date"/></th>
        <th scope="col"><fmt:message key="amount.students"/></th>
        <th scope="col"><fmt:message key="category"/></th>
        <th scope="col"><fmt:message key="status"/></th>
        <th scope="col"><fmt:message key="teacher"/></th>
        </thead>
        <c:forEach var="course" items="${requestScope.courses}">
            <tbody>
            <td>${course.title}</td>
            <td>${course.duration}</td>
            <td>${course.startDate}</td>
            <td>${course.amountStudents}</td>
            <td>${course.getCategory().title}</td>
            <td><fmt:message key="${course.getStatus()}"/></td>
            <td>${course.getTeacher().getName()} ${course.getTeacher().getSurname()}</td>
            </tbody>
        </c:forEach>
    </table>
    <tags:pagination
            href="${pageContext.request.contextPath}/controller?action=courses&sort=${param.sort}&order=${param.order}&select_by_category=${param.select_by_category}&select_by_teacher=${param.select_by_teacher}"/>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>