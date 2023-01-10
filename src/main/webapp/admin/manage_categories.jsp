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

<jsp:include page="../parts/header.jsp"/>
<jsp:include page="../parts/admin_header.jsp"/>

<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <caption>
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/add_category.jsp"
               role="button"><fmt:message key="admin.add.category"/></a>
            <fmt:message key="admin.table.category.name"/>
        </caption>
        <thead>
        <th scope="col"><fmt:message key="admin.table.category.title"/></th>
        <th scope="col"><fmt:message key="admin.table.category.description"/></th>
        <th scope="col"><fmt:message key="action"/></th>
        </thead>
        <c:forEach var="category" items="${requestScope.categories}">
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
                       href="${pageContext.request.contextPath}/controller?action=manage_categories&records_per_page=2">2</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_categories&records_per_page=5">5</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_categories&records_per_page=10">10</a>
                </li>

                <li><a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?action=manage_categories&records_per_page=20">20</a>
                </li>
            </ul>
        </div>
        <div class="col col-md-auto">
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <c:if test="${requestScope.currentPage == 1}">
                        <li class="page-item disabled">
                            <span class="page-link">Previous</span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.currentPage > 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_categories&page=${requestScope.currentPage-1}&records_per_page=${requestScope.records_per_page}">Previous</a>
                        </li>
                    </c:if>

                    <li class="page-item active" aria-current="page">
                        <span class="page-link">${requestScope.currentPage}</span>
                    </li>

                    <c:if test="${requestScope.noOfCategoriesPages - requestScope.currentPage < 1}">
                        <li class="page-item disabled">
                            <span class="page-link">${requestScope.currentPage+1}</span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfCategoriesPages - requestScope.currentPage >= 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_categories&page=${requestScope.currentPage+1}&records_per_page=${requestScope.records_per_page}">${requestScope.currentPage+1}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfCategoriesPages - requestScope.currentPage < 2}">
                        <li class="page-item disabled">
                            <span class="page-link">${requestScope.currentPage+2}</span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfCategoriesPages - requestScope.currentPage >= 2}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_categories&page=${requestScope.currentPage+2}&records_per_page=${requestScope.records_per_page}">${requestScope.currentPage+2}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfCategoriesPages - requestScope.currentPage < 1}">
                        <li class="page-item disabled">
                            <span class="page-link">Next</span>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.noOfCategoriesPages - requestScope.currentPage >= 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="${pageContext.request.contextPath}/controller?action=manage_categories&page=${requestScope.currentPage+1}&records_per_page=${requestScope.records_per_page}">Next</a>
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