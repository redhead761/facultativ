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
<jsp:include page="../parts/admin_header.jsp"/>

<tags:notification value_message="${sessionScope.message}" value_error="${sessionScope.error}"></tags:notification>

<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <thead>
        <th scope="col"><fmt:message key="name"/></th>
        <th scope="col"><fmt:message key="surname"/></th>
        <th scope="col"><fmt:message key="status"/></th>
        <th scope="col"><fmt:message key="action"/></th>
        </thead>
        <c:forEach var="student" items="${requestScope.students}">
            <tbody>
            <td>${student.name}</td>
            <td>${student.surname}</td>
            <td>${student.block}</td>
            <td>
                <div class="row">
                    <div class="col-md-auto">
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="action" value="update_block"/>
                            <input type="hidden" name="type" value="block"/>
                            <input type="hidden" name="student_id" value="${student.id}"/>
                            <input type="hidden" name="page" value="${requestScope.currentPage}"/>
                            <input type="hidden" name="records_per_page" value="${requestScope.records_per_page}"/>

                            <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message
                                    key="block"/></button>
                        </form>
                    </div>
                    <div class="col-md-auto">
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="action" value="update_block"/>
                            <input type="hidden" name="type" value="unblock"/>
                            <input type="hidden" name="student_id" value="${student.id}"/>
                            <input type="hidden" name="page" value="${requestScope.currentPage}"/>
                            <input type="hidden" name="records_per_page" value="${requestScope.records_per_page}"/>

                            <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message
                                    key="unblock"/></button>
                        </form>
                    </div>
                </div>
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
                       href="${pageContext.request.contextPath}/controller?action=manage_students&records_per_page=2">2</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_students&records_per_page=5">5</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_students&records_per_page=10">10</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_students&records_per_page=20">20</a>
                </li>
            </ul>
        </div>
        <div class="col col-md-auto">
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <c:if test="${requestScope.currentPage == 1}">
                        <li class="page-item disabled">
                            <span class="page-link"><fmt:message key="previous"/></span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.currentPage > 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_students&page=${requestScope.currentPage-1}&records_per_page=${requestScope.records_per_page}"><fmt:message
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
                                                 href="${pageContext.request.contextPath}/controller?action=manage_students&page=${requestScope.currentPage+1}&records_per_page=${requestScope.records_per_page}">${requestScope.currentPage+1}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage < 2}">
                        <li class="page-item disabled">
                            <span class="page-link">${requestScope.currentPage+2}</span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 2}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_students&page=${requestScope.currentPage+2}&records_per_page=${requestScope.records_per_page}">${requestScope.currentPage+2}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage < 1}">
                        <li class="page-item disabled">
                            <span class="page-link"><fmt:message key="next"/></span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_students&page=${requestScope.currentPage+1}&records_per_page=${requestScope.records_per_page}"><fmt:message
                                key="next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>
<jsp:include page="/parts/footer.jsp"/>
${sessionScope.remove("message")}
${sessionScope.remove("error")}
</body>
</html>