<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=manage_courses">Back to
                    courses</a>
            </li>
        </ul>
    </div>
</div>

<c:if test="${message != null}">
    <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
        <strong>${message}!</strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <caption>
            All teachers in facultative
        </caption>
        <thead>
        <th scope="col">Name</th>
        <th scope="col">Surname</th>
        <th scope="col">Email</th>
        <th scope="col">Action</th>
        </thead>
        <c:forEach var="teacher" items="${requestScope.teachers}">
            <tbody>
            <td>${teacher.name}</td>
            <td>${teacher.surname}</td>
            <td>${teacher.email}</td>
            <td>
                <a href="${pageContext.request.contextPath}/controller?action=assign&teacher_id=${teacher.id}&course_id=${requestScope.course_id}">Assigned</a>
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
                                         href="${pageContext.request.contextPath}/controller?action=show_assign_page&page=${requestScope.currentPage-1}&course_id=${requestScope.course_id}">Previous</a>
                </li>
            </c:if>

            <li class="page-item active" aria-current="page">
                <span class="page-link">${requestScope.currentPage}</span>
            </li>

            <c:if test="${requestScope.noOfTeachersPages - requestScope.currentPage < 1}">
                <li class="page-item disabled">
                    <span class="page-link">${requestScope.currentPage+1}</span>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfTeachersPages - requestScope.currentPage >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=show_assign_page&page=${requestScope.currentPage+1}&course_id=${requestScope.course_id}">${requestScope.currentPage+1}</a>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfTeachersPages - requestScope.currentPage < 2}">
                <li class="page-item disabled">
                    <span class="page-link">${requestScope.currentPage+2}</span>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfTeachersPages - requestScope.currentPage >= 2}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=show_assign_page&page=${requestScope.currentPage+2}&course_id=${requestScope.course_id}">${requestScope.currentPage+2}</a>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfTeachersPages - requestScope.currentPage < 1}">
                <li class="page-item disabled">
                    <span class="page-link">Next</span>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfTeachersPages - requestScope.currentPage >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=show_assign_page&page=${requestScope.currentPage+1}&course_id=${requestScope.course_id}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>