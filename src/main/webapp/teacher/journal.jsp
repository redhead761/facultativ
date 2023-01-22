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
    <title> Facultative </title>
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<jsp:include page="../parts/header.jsp"/>
<div class="row">
    <div class="col">
        <jsp:include page="../parts/teacher_header.jsp"/>
    </div>
    <div class="col-auto">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?action=show_teacher_courses"><fmt:message
                        key="back.my.courses"/></a>
            </li>
        </ul>
    </div>
</div>
<tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>
<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <thead>
        <th scope="col"><fmt:message key="name"/></th>
        <th scope="col"><fmt:message key="surname"/></th>
        <th scope="col"><fmt:message key="status"/></th>
        <th scope="col"><fmt:message key="grade"/></th>
        <th scope="col"><fmt:message key="action"/></th>
        </thead>
        <c:forEach var="student" items="${requestScope.students}">
            <tbody>
            <td>${student.name}</td>
            <td>${student.surname}</td>
            <td>${student.isBlock()}</td>
            <td>${student.grade}</td>
            <td>
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="action" value="grade"/>
                    <input type="hidden" name="records_per_page" value="${requestScope.records_per_page}"/>
                    <input type="hidden" name="current_page" value="${requestScope.current_page}"/>
                    <input type="hidden" name="course_id" value="${param.course_id}"/>
                    <input type="hidden" name="student_id" value="${student.id}"/>
                    <fmt:message key="grade"/>: <label>
                    <input name="grade" type="number" min="1" max="100"/>
                </label>
                    <input type="submit" value="<fmt:message key="submit"/>"/>
                </form>
            </td>
            </tbody>
        </c:forEach>
    </table>
    <tags:pagination href="${pageContext.request.contextPath}/controller?action=show_journal&course_id=${param.course_id}"/>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>