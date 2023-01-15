<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=show_teacher_courses"><fmt:message key="back.my.courses"/></a>
            </li>
        </ul>
    </div>
</div>

<c:if test="${sessionScope.message != null}">
    <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
        <strong>${sessionScope.message}!</strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
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
                    <input type="hidden" name="page" value="${requestScope.currentPage}"/>
                    <input type="hidden" name="course_id" value="${requestScope.course_id}"/>
                    <input type="hidden" name="student_id" value="${student.id}"/>
                    <fmt:message key="grade"/>: <input name="grade" type="number" min="0" max="20"/>
                    <input type="submit" value="<fmt:message key="submit"/>"/>
                </form>
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
                       href="${pageContext.request.contextPath}/controller?action=show_journal&records_per_page=2&course_id=${requestScope.course_id}">2</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=show_journal&records_per_page=5&course_id=${requestScope.course_id}">5</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=show_journal&records_per_page=10&course_id=${requestScope.course_id}">10</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=show_journal&records_per_page=20&course_id=${requestScope.course_id}">20</a>
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
                                                 href="${pageContext.request.contextPath}/controller?action=show_journal&page=${requestScope.currentPage-1}&records_per_page=${requestScope.records_per_page}&course_id=${requestScope.course_id}"><fmt:message key="previous"/></a>
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
                                                 href="${pageContext.request.contextPath}/controller?action=show_journal&page=${requestScope.currentPage+1}&records_per_page=${requestScope.records_per_page}&course_id=${requestScope.course_id}">${requestScope.currentPage+1}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage < 2}">
                        <li class="page-item disabled">
                            <span class="page-link">${requestScope.currentPage+2}</span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 2}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=show_journal&page=${requestScope.currentPage+2}&records_per_page=${requestScope.records_per_page}&course_id=${requestScope.course_id}">${requestScope.currentPage+2}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage < 1}">
                        <li class="page-item disabled">
                            <span class="page-link"><fmt:message key="next"/></span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=show_journal&page=${requestScope.currentPage+1}&records_per_page=${requestScope.records_per_page}&course_id=${requestScope.course_id}"><fmt:message key="next"/></a>
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