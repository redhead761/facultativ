<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title> Teacher cabinet </title>
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<jsp:include page="../parts/header.jsp"/>
<jsp:include page="../parts/teacher_header.jsp"/>

<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <thead>
        <th scope="col"><fmt:message key="title"/></th>
        <th scope="col"><fmt:message key="duration"/></th>
        <th scope="col"><fmt:message key="start.date"/></th>
        <th scope="col"><fmt:message key="amount.students"/></th>
        <th scope="col"><fmt:message key="category"/></th>
        <th scope="col"><fmt:message key="status"/></th>
        <th scope="col"><fmt:message key="action"/></th>
        </thead>
        <c:forEach var="course" items="${requestScope.courses}">
            <tbody>
            <td>${course.title}</td>
            <td>${course.duration}</td>
            <td>${course.startDate}</td>
            <td>${course.amountStudents}</td>
            <td>${course.getCategory().title}</td>
            <td>${course.getStatus()}</td>
            <td>
                <c:if test="${course.getStatus() eq 'COMPLETED'}">
                    <a href="${pageContext.request.contextPath}/controller?action=show_journal&course_id=${course.id}"><fmt:message
                            key="journal"/></a>
                </c:if>
            </td>
            </tbody>
        </c:forEach>
    </table>
    <tags:pagination href="${pageContext.request.contextPath}/controller?action=show_teacher_courses"/>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>