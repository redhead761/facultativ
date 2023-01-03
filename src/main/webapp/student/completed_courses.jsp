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
<jsp:include page="../parts/student_header.jsp"/>

<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <caption>
            All courses in facultative
        </caption>
        ${sessionScope.message}
        <thead>
        <th scope="col">Title</th>
        <th scope="col">Duration</th>
        <th scope="col">Start date</th>
        <th scope="col">Students on course</th>
        <th scope="col">Category</th>
        <th scope="col">Status</th>
        <th scope="col">Teacher</th>
        <th scope="col">Grade</th>
        </thead>
        <c:forEach var="course" items="${requestScope.courses}">
            <tbody>
            <td>${course.getCourse().getTitle()}</td>
            <td><c:out value="${course.getCourse().duration}"/></td>
            <td><c:out value="${course.getCourse().startDate}"/></td>
            <td><c:out value="${course.getCourse().amountStudents}"/></td>
            <td><c:out value="${course.getCourse().getCategory().title}"/></td>
            <td><c:out value="${course.getCourse().getStatus()}"/></td>
            <td>
                <c:out value="${course.getCourse().getTeacher().getName()} ${course.getCourse().getTeacher().getSurname()}"/>
            </td>
            <td><c:out value="${course.getGrade()}"/></td>
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
                                         href="${pageContext.request.contextPath}/controller?action=show_completed_courses&page=${requestScope.currentPage-1}">Previous</a>
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
                                         href="${pageContext.request.contextPath}/controller?action=show_completed_courses&page=${requestScope.currentPage+1}">${requestScope.currentPage+1}</a>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfCoursesPages - requestScope.currentPage < 2}">
                <li class="page-item disabled">
                    <span class="page-link">${requestScope.currentPage+2}</span>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfCoursesPages - requestScope.currentPage >= 2}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=show_completed_courses&page=${requestScope.currentPage+2}">${requestScope.currentPage+2}</a>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfCoursesPages - requestScope.currentPage < 1}">
                <li class="page-item disabled">
                    <span class="page-link">Next</span>
                </li>
            </c:if>

            <c:if test="${requestScope.noOfCoursesPages - requestScope.currentPage >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=show_completed_courses&page=${requestScope.currentPage+1}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>