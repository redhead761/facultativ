<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

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
<tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>

<fmt:message key="grade"/>: ${requestScope.grade}<br>
<div class="row">
    <div class="col-md-auto">
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="action" value="certificate"/>
            <input type="hidden" name="type" value="download"/>
            <input type="hidden" name="course_id" value="${param.course_id}"/>
            <input type="hidden" name="grade" value="${requestScope.grade}"/>

            <button type="submit" class="btn btn-outline-secondary btn-lg"><fmt:message
                    key="download.certificate"/></button>
        </form>
    </div>
    <div class="col-md-auto">
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="action" value="certificate"/>
            <input type="hidden" name="type" value="send"/>
            <input type="hidden" name="course_id" value="${param.course_id}"/>
            <input type="hidden" name="grade" value="${requestScope.grade}"/>

            <button type="submit" class="btn btn-outline-secondary btn-lg"><fmt:message
                    key="send.certificate"/></button>
        </form>
    </div>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>