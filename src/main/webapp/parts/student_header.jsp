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
    <title> Facultative </title>
</head>
<body>

<ul class="nav">
    <li class="nav-item">
        <a class="nav-link active" aria-current="page"
           href="${pageContext.request.contextPath}/student/student_profile.jsp">Profile</a>
    </li>
    <li class="nav-item">
        <a class="nav-link active" aria-current="page"
           href="${pageContext.request.contextPath}/controller?action=show_all_courses">All courses</a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?action=show_student_courses&type=all"
           role="button">My courses</a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?action=show_student_courses&type=coming_soon"
           role="button">My courses coming soon</a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?action=show_student_courses&type=in_progress"
           role="button">My courses in progress</a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?action=show_student_courses&type=completed"
           role="button">My courses completed</a>
    </li>
</ul>
</body>
</html>
