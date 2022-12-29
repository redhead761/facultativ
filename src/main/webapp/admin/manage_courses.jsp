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
    <link rel="stylesheet" href="../css/first.css">

    <title> Facultative </title>
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<jsp:include page="../parts/header.jsp"/>
<jsp:include page="../parts/admin_header.jsp"/>

<c:if test="${sessionScope.message != null}">
    <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
        <strong>${sessionScope.message}!</strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <caption>
            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuSort"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="admin.sort"/>
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=sort&sort_type=alphabet">Alphabetical</a>
                </li>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=sort&sort_type=reverse alphabet">Reverse
                    alphabetical</a></li>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=sort&sort_type=duration">Duration</a>
                </li>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=sort&sort_type=amount students">Amount
                    students</a></li>
            </ul>

            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuSelectByTeacher"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="admin.select.teacher"/>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="teacher" items="${requestScope.teachers}">
                    <li><a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?action=select_courses&select_type=by_teacher&teacher_id=${teacher.id}">${teacher.name} ${teacher.surname}</a>
                    </li>
                </c:forEach>
            </ul>

            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuSelectByCategory"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="admin.select.category"/>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="category" items="${requestScope.categories}">
                    <li><a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?action=select_courses&select_type=by_category&category_id=${category.id}">${category.title}</a>
                    </li>
                </c:forEach>
            </ul>
            <a class="btn btn-outline-secondary"
               href="${pageContext.request.contextPath}/controller?action=show_add_course"
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
        <c:forEach var="course" items="${requestScope.courses}">
            <tbody>
            <td>${course.title}</td>
            <td><c:out value="${course.duration}"/></td>
            <td><c:out value="${course.startDate}"/></td>
            <td><c:out value="${course.amountStudents}"/></td>
            <td><c:out value="${course.getCategory().title}"/></td>
            <td><c:out value="${course.getStatus()}"/></td>
            <td>
                <c:if test="${course.getTeacher().getName() != null}">
                    <c:out value="${course.getTeacher().getName()} ${course.getTeacher().getSurname()}"/>
                </c:if>
                <c:if test="${course.getTeacher().getName() == null}">
                    <a href="${pageContext.request.contextPath}/controller?action=show_assign_page&course_id=<c:out value='${course.id}'/>">Assigned</a>
                </c:if>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/controller?action=show_edit_course&course_id=${course.id}">Edit</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="${pageContext.request.contextPath}/controller?action=delete_course&course_id=<c:out value='${course.id}'/>">Delete</a>
            </td>
            </tbody>
        </c:forEach>
    </table>
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <c:if test="${requestScope.currentPage == 1}">
                <li class="page-item disabled">
                    <span class="page-link">Previous</span>
                </li>
            </c:if>

            <c:if test="${requestScope.currentPage > 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=manage_courses&page=${requestScope.currentPage-1}&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}">Previous</a>
                </li>
            </c:if>

            <li class="page-item active" aria-current="page">
                <span class="page-link">${requestScope.currentPage}</span>
            </li>

            <c:if test="${requestScope.noOfCoursesPages - requestScope.currentPage < 1}">
                <li class="page-item disabled">
                    <span class="page-link">${requestScope.currentPage+1}</span>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfCoursesPages - requestScope.currentPage >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=manage_courses&page=${requestScope.currentPage+1}&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}">${requestScope.currentPage+1}</a>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfCoursesPages - requestScope.currentPage < 2}">
                <li class="page-item disabled">
                    <span class="page-link">${requestScope.currentPage+2}</span>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfCoursesPages - requestScope.currentPage >= 2}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=manage_courses&page=${requestScope.currentPage+2}&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}">${sessionScope.currentPage+2}</a>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfCoursesPages - requestScope.currentPage < 1}">
                <li class="page-item disabled">
                    <span class="page-link">Next</span>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfCoursesPages - requestScope.currentPage >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=manage_courses&page=${requestScope.currentPage+1}&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>