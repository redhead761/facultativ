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
        <jsp:include page="../parts/admin_header.jsp"/>
    </div>
    <div class="col-auto">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?action=manage_courses"><fmt:message
                        key="back.courses"/></a>
            </li>
        </ul>
    </div>
</div>

<tags:notification value_message="${sessionScope.message}" value_error="${sessionScope.error}"></tags:notification>

<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <thead>
        <th scope="col"><fmt:message key="name"/></th>
        <th scope="col"><fmt:message key="surname"/></th>
        <th scope="col"><fmt:message key="email"/></th>
        <th scope="col"><fmt:message key="action"/></th>
        </thead>
        <c:forEach var="teacher" items="${requestScope.teachers}">
            <tbody>
            <td>${teacher.name}</td>
            <td>${teacher.surname}</td>
            <td>${teacher.email}</td>
            <td>
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="action" value="add_category"/>
                    <input type="hidden" name="teacher_id" value="${teacher.id}"/>
                    <input type="hidden" name="course_id" value="${requestScope.course_id}}"/>

                    <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message key="assign"/></button>

                </form>
<%--                <a href="${pageContext.request.contextPath}/controller?action=assign&teacher_id=${teacher.id}&course_id=${requestScope.course_id}"><fmt:message--%>
<%--                        key="assign"/></a>--%>
            </td>
            </tbody>
        </c:forEach>
    </table>
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <c:if test="${requestScope.currentPage == 1}">
                <li class="page-item disabled">
                    <span class="page-link"><fmt:message key="previous"/></span>
                </li>
            </c:if>

            <c:if test="${requestScope.currentPage > 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=show_assign_page&page=${requestScope.currentPage-1}&course_id=${requestScope.course_id}"><fmt:message
                        key="previous"/></a>
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
                                         href="${pageContext.request.contextPath}/controller?action=show_assign_page&page=${requestScope.currentPage+1}&course_id=${requestScope.course_id}">${requestScope.currentPage+1}</a>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfPages - requestScope.currentPage < 2}">
                <li class="page-item disabled">
                    <span class="page-link">${requestScope.currentPage+2}</span>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 2}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=show_assign_page&page=${requestScope.currentPage+2}&course_id=${requestScope.course_id}">${requestScope.currentPage+2}</a>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfPages - requestScope.currentPage < 1}">
                <li class="page-item disabled">
                    <span class="page-link"><fmt:message key="next"/></span>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=show_assign_page&page=${requestScope.currentPage+1}&course_id=${requestScope.course_id}"><fmt:message
                        key="next"/></a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
<jsp:include page="/parts/footer.jsp"/>
${sessionScope.remove("message")}
${sessionScope.remove("error")}
</body>
</html>