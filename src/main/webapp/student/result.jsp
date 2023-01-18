<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<jsp:include page="../parts/header.jsp"/>
<div class="row">
    <div class="col">
        <jsp:include page="../parts/student_header.jsp"/>
    </div>
    <div class="col-auto">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?action=show_student_courses&type=completed"><fmt:message
                        key="back.completed"/></a>
            </li>
        </ul>
    </div>
</div>

<fmt:message key="grade"/>: ${requestScope.grade}<br>
<a class="btn btn-outline-secondary justify-content-end"
   href="${pageContext.request.contextPath}/controller?action=certificate&type=download&course_id=${requestScope.course_id}&grade=${requestScope.grade}"
   role="button">Download certificate</a>
<a class="btn btn-outline-secondary justify-content-end"
   href="${pageContext.request.contextPath}/controller?action=certificate&type=send&course_id=${requestScope.course_id}&grade=${requestScope.grade}"
   role="button">Send certificate</a>

<jsp:include page="/parts/footer.jsp"/>
</body>
</html>