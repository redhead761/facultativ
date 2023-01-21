<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.language}">
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
<jsp:include page="/parts/header.jsp"/>

<div class="text-center">
    <h2><fmt:message key="all.course"/></h2>
    <hr>
</div>
<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <caption>
            <form action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="action" value="courses"/>
                <input type="hidden" name="records_per_page" value="${requestScope.records_per_page}"/>


                <select name="sort" class="form-select form-select-lg mb-3">
                    <option disabled selected value=""><fmt:message key="sort"/></option>
                    <option value="course.title"><fmt:message key="alphabetical"/></option>
                    <option value="course.duration"><fmt:message key="duration"/></option>
                    <option value="course.amount_students"><fmt:message key="amount.students"/></option>
                </select>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="DESC" id="flexCheckDefault"
                           name="order">
                    <label class="form-check-label" for="flexCheckDefault">
                        <fmt:message key="reverse"/>
                    </label>
                </div>

                <select name="select_by_teacher" class="form-select form-select-lg mb-3">
                    <option disabled selected value=""><fmt:message key="select.by.teacher"/></option>
                    <c:forEach var="teacher" items="${requestScope.teachers}">
                        <option value="${teacher.id}">${teacher.name} ${teacher.surname}</option>
                    </c:forEach>
                </select>

                <select name="select_by_category" class="form-select form-select-lg mb-3">
                    <option disabled selected value=""><fmt:message key="select.by.category"/></option>
                    <c:forEach var="category" items="${requestScope.categories}">
                        <option value="${category.id}">${category.title}</option>
                    </c:forEach>
                </select>

                <div class="mt-4 pt-2">
                    <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                </div>
            </form>
            <a class="btn btn-outline-secondary justify-content-end"
               href="${pageContext.request.contextPath}/controller?action=download_courses"
               role="button"><fmt:message key="download.courses"/></a>
        </caption>
        <thead>
        <th scope="col"><fmt:message key="title"/></th>
        <th scope="col"><fmt:message key="duration"/></th>
        <th scope="col"><fmt:message key="start.date"/></th>
        <th scope="col"><fmt:message key="amount.students"/></th>
        <th scope="col"><fmt:message key="category"/></th>
        <th scope="col"><fmt:message key="status"/></th>
        <th scope="col"><fmt:message key="teacher"/></th>
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
                    <c:out value="${course.getTeacher().getName()} ${course.getTeacher().getSurname()}"/>
                </c:if>
            </td>
            </tbody>
        </c:forEach>
    </table>
    <div class="row  justify-content-md-end">
        <div class="col col-md-auto">
            <a>Rows per page:</a>
        </div>
        <div class="col col-md-auto">
            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="records_per_page"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                ${requestScope.records_per_page}
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=courses&records_per_page=2&sort=${requestScope.sort}&order=${requestScope.order}&select_by_category=${requestScope.select_by_category}&select_by_teacher=${requestScope.select_by_teacher}">2</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=courses&records_per_page=5&sort=${requestScope.sort}&order=${requestScope.order}&select_by_category=${requestScope.select_by_category}&select_by_teacher=${requestScope.select_by_teacher}">5</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=courses&records_per_page=10&sort=${requestScope.sort}&order=${requestScope.order}&select_by_category=${requestScope.select_by_category}&select_by_teacher=${requestScope.select_by_teacher}">10</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=courses&records_per_page=20&sort=${requestScope.sort}&order=${requestScope.order}&select_by_category=${requestScope.select_by_category}&select_by_teacher=${requestScope.select_by_teacher}">20</a>
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
                                                 href="${pageContext.request.contextPath}/controller?action=courses&page=${requestScope.currentPage-1}&records_per_page=${requestScope.records_per_page}&sort=${requestScope.sort}&order=${requestScope.order}&select_by_category=${requestScope.select_by_category}&select_by_teacher=${requestScope.select_by_teacher}">
                            <fmt:message key="previous"/></a>
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
                                                 href="${pageContext.request.contextPath}/controller?action=courses&page=${requestScope.currentPage+1}&records_per_page=${requestScope.records_per_page}&sort=${requestScope.sort}&order=${requestScope.order}&select_by_category=${requestScope.select_by_category}&select_by_teacher=${requestScope.select_by_teacher}">${requestScope.currentPage+1}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage < 2}">
                        <li class="page-item disabled">
                            <span class="page-link">${requestScope.currentPage+2}</span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 2}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=courses&page=${requestScope.currentPage+2}&records_per_page=${requestScope.records_per_page}&sort=${requestScope.sort}&order=${requestScope.order}&select_by_category=${requestScope.select_by_category}&select_by_teacher=${requestScope.select_by_teacher}">${requestScope.currentPage+2}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage < 1}">
                        <li class="page-item disabled">
                            <span class="page-link"><fmt:message key="next"/></span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfPages - requestScope.currentPage >= 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=courses&page=${requestScope.currentPage+1}&records_per_page=${requestScope.records_per_page}&sort=${requestScope.sort}&order=${requestScope.order}&select_by_category=${requestScope.select_by_category}&select_by_teacher=${requestScope.select_by_teacher}"><fmt:message
                                key="next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>