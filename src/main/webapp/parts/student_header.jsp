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
           href="${pageContext.request.contextPath}/student/student_profile.jsp"><fmt:message key="profile"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link active" aria-current="page"
           href="${pageContext.request.contextPath}/controller?action=show_all_courses"><fmt:message key="all.courses"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?action=show_student_courses&type=all"
           role="button"><fmt:message key="my.courses"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?action=show_student_courses&type=coming_soon"
           role="button"><fmt:message key="coming.soon"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?action=show_student_courses&type=in_progress"
           role="button"><fmt:message key="in.progress"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?action=show_student_courses&type=completed"
           role="button"><fmt:message key="complete"/></a>
    </li>
</ul>
</body>
</html>
