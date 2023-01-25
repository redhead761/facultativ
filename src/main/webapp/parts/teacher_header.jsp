<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>


<html>
<head>
    <title> Facultative </title>
</head>
<body>

<ul class="nav">
    <li class="nav-item">
        <a class="nav-link active" aria-current="page"
           href="${pageContext.request.contextPath}/teacher/teacher_profile.jsp"><fmt:message key="profile"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link active" aria-current="page"
           href="${pageContext.request.contextPath}/teacher/edit_teacher_profile.jsp"><fmt:message
                key="edit.profile"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link active" aria-current="page"
           href="${pageContext.request.contextPath}/change_password.jsp"><fmt:message
                key="change.password"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?action=show_teacher_courses&user_id=${sessionScope.user.id}"
           role="button"><fmt:message key="my.courses"/></a>
    </li>
</ul>
</body>
</html>
