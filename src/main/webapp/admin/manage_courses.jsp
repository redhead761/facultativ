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
    <link rel="stylesheet" href="../css/first.css">

    <title> Facultative </title>
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<jsp:include page="../parts/header.jsp"/>
<jsp:include page="../parts/admin_header.jsp"/>

<tags:notification value_message="${sessionScope.message}" value_error="${sessionScope.error}"></tags:notification>

<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <caption>
            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuSort"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="sort"/>
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_courses&sort_type=alphabet"><fmt:message
                        key="alphabetical"/></a>
                </li>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_courses&sort_type=reverse alphabet"><fmt:message
                        key="reverse.alphabetical"/></a></li>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_courses&sort_type=duration"><fmt:message
                        key="duration"/></a>
                </li>
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_courses&sort_type=amount students"><fmt:message
                        key="amount.students"/></a></li>
            </ul>

            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuSelectByTeacher"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="select.by.teacher"/>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="teacher" items="${requestScope.teachers}">
                    <li><a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?action=manage_courses&select_type=by_teacher&teacher_id=${teacher.id}&records_per_page=${requestScope.records_per_page}">${teacher.name} ${teacher.surname}</a>
                    </li>
                </c:forEach>
            </ul>

            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuSelectByCategory"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="select.by.category"/>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="category" items="${requestScope.categories}">
                    <li><a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?action=manage_courses&select_type=by_category&category_id=${category.id}&records_per_page=${requestScope.records_per_page}">${category.title}</a>
                    </li>
                </c:forEach>
            </ul>
            <a class="btn btn-outline-secondary"
               href="${pageContext.request.contextPath}/controller?action=show_add_course"
               role="button"><fmt:message key="add.course"/></a>
        </caption>
        <thead>
        <th scope="col"><fmt:message key="title"/></th>
        <th scope="col"><fmt:message key="duration"/></th>
        <th scope="col"><fmt:message key="start.date"/></th>
        <th scope="col"><fmt:message key="amount.students"/></th>
        <th scope="col"><fmt:message key="category"/></th>
        <th scope="col"><fmt:message key="status"/></th>
        <th scope="col"><fmt:message key="teacher"/></th>
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
                <c:if test="${course.getTeacher().getName() != null}">
                    ${course.getTeacher().getName()} ${course.getTeacher().getSurname()}
                </c:if>
                <c:if test="${course.getTeacher().getName() == null}">
                    <a href="${pageContext.request.contextPath}/controller?action=show_assign_page&course_id=<c:out value='${course.id}'/>"><fmt:message key="assign"/></a>
                </c:if>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/controller?action=show_edit_course&course_id=${course.id}"><fmt:message key="edit"/></a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="${pageContext.request.contextPath}/controller?action=delete_course&course_id=<c:out value='${course.id}'/>"><fmt:message key="delete"/></a>
            </td>
            </tbody>
        </c:forEach>
    </table>
    <div class="row  justify-content-md-end">
        <div class="col col-md-auto">
            <a><fmt:message key="rows.per.page"/>:</a>
        </div>
        <div class="col col-md-auto">
            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="records_per_page"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                ${requestScope.records_per_page}
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_courses&records_per_page=2&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}">2</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_courses&records_per_page=5&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}">5</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_courses&records_per_page=10&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}">10</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_courses&records_per_page=20&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}">20</a>
                </li>
            </ul>
        </div>
        <div class="col col-md-auto">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <c:if test="${requestScope.currentPage == 1}">
                        <li class="page-item disabled">
                            <span class="page-link"><fmt:message key="previous"/></span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.currentPage > 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_courses&page=${requestScope.currentPage-1}&records_per_page=${requestScope.records_per_page}&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}"><fmt:message key="previous"/></a>
                        </li>
                    </c:if>

                    <li class="page-item active" aria-current="page">
                        <span class="page-link">${requestScope.currentPage}</span>
                    </li>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage < 1}">
                        <li class="page-item disabled">
                            <span class="page-link">${requestScope.currentPage+1}</span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_courses&page=${requestScope.currentPage+1}&records_per_page=${requestScope.records_per_page}&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}">${requestScope.currentPage+1}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage < 2}">
                        <li class="page-item disabled">
                            <span class="page-link">${requestScope.currentPage+2}</span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 2}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_courses&page=${requestScope.currentPage+2}&records_per_page=${requestScope.records_per_page}&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}">${requestScope.currentPage+2}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage < 1}">
                        <li class="page-item disabled">
                            <span class="page-link"><fmt:message key="next"/></span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_courses&page=${requestScope.currentPage+1}&records_per_page=${requestScope.records_per_page}&sort_type=${requestScope.sort_type}&select_type=${requestScope.select_type}&teacher_id=${requestScope.teacher_id}&category_id=${requestScope.category_id}"><fmt:message key="next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>
<jsp:include page="/parts/footer.jsp"/>
${sessionScope.remove("message")}
</body>
</html>