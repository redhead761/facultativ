<%@ attribute name="href" required="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>

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
                   href="${href}&records_per_page=2">2</a>
            </li>

            <li><a class="dropdown-item"
                   href="${href}&records_per_page=5">5</a>
            </li>

            <li><a class="dropdown-item"
                   href="${href}&records_per_page=10">10</a>
            </li>

            <li><a class="dropdown-item"
                   href="${href}&records_per_page=20">20</a>
            </li>
        </ul>
    </div>
    <div class="col col-md-auto">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:if test="${requestScope.current_page == 1}">
                    <li class="page-item disabled">
                        <span class="page-link"><fmt:message key="previous"/></span>
                    </li>
                </c:if>

                <c:if test="${requestScope.current_page > 1}">
                    <li class="page-item"><a class="page-link"
                                             href="${href}&current_page=${requestScope.current_page-1}&records_per_page=${requestScope.records_per_page}">
                        <fmt:message key="previous"/></a>
                    </li>
                </c:if>

                <li class="page-item active" aria-current="page">
                    <span class="page-link">${requestScope.current_page}</span>
                </li>

                <c:if test="${requestScope.no_of_pages - requestScope.current_page < 1}">
                    <li class="page-item disabled">
                        <span class="page-link">${requestScope.current_page+1}</span>
                    </li>
                </c:if>

                <c:if test="${requestScope.no_of_pages - requestScope.current_page >= 1}">
                    <li class="page-item"><a class="page-link"
                                             href="${href}&current_page=${requestScope.current_page+1}&records_per_page=${requestScope.records_per_page}">${requestScope.current_page+1}</a>
                    </li>
                </c:if>

                <c:if test="${requestScope.no_of_pages - requestScope.current_page < 2}">
                    <li class="page-item disabled">
                        <span class="page-link">${requestScope.current_page+2}</span>
                    </li>
                </c:if>

                <c:if test="${requestScope.no_of_pages - requestScope.current_page >= 2}">
                    <li class="page-item"><a class="page-link"
                                             href="${href}&current_page=${requestScope.current_page+2}&records_per_page=${requestScope.records_per_page}">${requestScope.current_page+2}</a>
                    </li>
                </c:if>

                <c:if test="${requestScope.no_of_pages - requestScope.current_page < 1}">
                    <li class="page-item disabled">
                        <span class="page-link"><fmt:message key="next"/></span>
                    </li>
                </c:if>

                <c:if test="${requestScope.no_of_pages - requestScope.current_page >= 1}">
                    <li class="page-item"><a class="page-link"
                                             href="${href}&current_page=${requestScope.current_page+1}&records_per_page=${requestScope.records_per_page}"><fmt:message
                            key="next"/></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
