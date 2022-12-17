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
    <title> Facultative </title>
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<jsp:include page="../parts/admin_header.jsp"/>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/controller?action=manage_courses" role="button"><fmt:message key="back"/></a>

<div class="table-responsive col-lg-10 mx-auto p-4">
    <table class="table table-success table-striped caption-top table-bordered">
        <caption>
            <a class="btn btn-primary" href="category_form.jsp" role="button"><fmt:message key="admin.add.category"/></a>
            <fmt:message key="admin.table.category.name"/>
        </caption>
        <thead>
        <th scope="col"><fmt:message key="admin.table.category.title"/></th>
        <th scope="col"><fmt:message key="admin.table.category.description"/></th>
        <th scope="col"><fmt:message key="action"/></th>
        </thead>
        <c:forEach var="category" items="${sessionScope.categories}">
            <tbody>
            <td>${category.title}</td>
            <td>${category.description}</td>
            <td>
                <a href="${pageContext.request.contextPath}/controller?action=show_category_form&category_id=${category.id}">Edit</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="${pageContext.request.contextPath}/controller?action=delete_category&category_id=${category.id}">Delete</a>
            </td>
            </tbody>
        </c:forEach>
    </table>
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <c:if test="${sessionScope.currentPage == 1}">
                <li class="page-item disabled">
                    <span class="page-link">Previous</span>
                </li>
            </c:if>

            <c:if test="${sessionScope.currentPage > 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=manage_categories&page=${sessionScope.currentPage-1}">Previous</a>
                </li>
            </c:if>

            <li class="page-item active" aria-current="page">
                <span class="page-link">${sessionScope.currentPage}</span>
            </li>

            <c:if test="${sessionScope.noOfCategoriesPages - sessionScope.currentPage < 1}">
                <li class="page-item disabled">
                    <span class="page-link">${sessionScope.currentPage+1}</span>
                </li>
            </c:if>

            <c:if test="${sessionScope.noOfCategoriesPages - sessionScope.currentPage >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=manage_categories&page=${sessionScope.currentPage+1}">${sessionScope.currentPage+1}</a>
                </li>
            </c:if>

            <c:if test="${sessionScope.noOfCategoriesPages - sessionScope.currentPage < 2}">
                <li class="page-item disabled">
                    <span class="page-link">${sessionScope.currentPage+2}</span>
                </li>
            </c:if>

            <c:if test="${sessionScope.noOfCategoriesPages - sessionScope.currentPage >= 2}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=manage_categories&page=${sessionScope.currentPage+2}">${sessionScope.currentPage+2}</a>
                </li>
            </c:if>

            <c:if test="${sessionScope.noOfCategoriesPages - sessionScope.currentPage < 1}">
                <li class="page-item disabled">
                    <span class="page-link">Next</span>
                </li>
            </c:if>

            <c:if test="${sessionScope.noOfCategoriesPages - sessionScope.currentPage >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/controller?action=manage_categories&page=${sessionScope.currentPage+1}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>