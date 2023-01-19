<%@ attribute name="value_message" required="true" %>
<%@ attribute name="value_error" required="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>

<c:if test="${not empty value_error}">
    <div class="alert alert-warning alert-dismissible fade show text-center" role="alert">
        <strong><fmt:message key="${value_error}"/></strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<c:if test="${not empty value_message}">
    <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
        <strong><fmt:message key="${value_message}"/></strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>