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
<a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=manage_courses" role="button"><fmt:message key="back"/></a>


<div class="table-responsive col-lg-10 mx-auto p-4">
    <table class="table table-success table-striped caption-top table-bordered">
        <caption>
            <fmt:message key="admin.table.student.name"/>
        </caption>
        <thead>
        <th scope="col"><fmt:message key="name"/></th>
        <th scope="col"><fmt:message key="surname"/></th>
        <th scope="col"><fmt:message key="admin.table.student.status"/></th>
        <th scope="col"><fmt:message key="action"/></th>
        </thead>
        <c:forEach var="student" items="${students}">
            <tbody>
            <td>${student.name}</td>
            <td>${student.surname}</td>
            <td>${student.isBlock()}</td>
            <td>
                <a href="${pageContext.request.contextPath}/controller?action=block&student_id=${student.id}">Block</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="${pageContext.request.contextPath}/controller?action=unblock&student_id=${student.id}">Unblock</a>
            </td>
            </tbody>
        </c:forEach>
    </table>
</div>
<jsp:include page="/parts/footer.jsp"/>

</body>
</html>