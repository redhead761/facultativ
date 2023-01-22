<%@ attribute name="action" required="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>

<div class="col-md-auto mb-2">
    <form action="${pageContext.request.contextPath}/controller" method="get">
        <input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="records_per_page" value="${requestScope.records_per_page}"/>

        <div class="row justify-content-start">
            <div class="col-md-auto">
                <select name="sort" class="form-select form-select-sm">
                    <option disabled selected value=""><fmt:message key="sort"/></option>
                    <option value=""><fmt:message key="default"/></option>
                    <option value="course.title" ${param.sort == 'course.title' ? 'selected' : '' }>
                        <fmt:message key="alphabetical"/></option>
                    <option value="course.duration" ${param.sort == 'course.duration' ? 'selected' : '' }>
                        <fmt:message key="duration"/></option>
                    <option value="course.amount_students" ${param.sort == 'course.amount_students' ? 'selected' : '' }>
                        <fmt:message key="amount.students"/></option>
                </select>
            </div>

            <div class="col-md-auto mt-1">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="DESC" id="flexCheckDefault"
                           name="order" ${param.order == 'DESC' ? 'checked' : '' }>
                    <label class="form-check-label" for="flexCheckDefault">
                        <fmt:message key="reverse"/>
                    </label>
                </div>
            </div>

            <div class="col-md-auto">
                <select name="select_by_teacher" class="form-select form-select-sm">
                    <option disabled selected value=""><fmt:message key="select.by.teacher"/></option>
                    <option value=""><fmt:message key="default"/></option>
                    <c:forEach var="teacher" items="${requestScope.teachers}">
                        <option value="${teacher.id}" ${param.select_by_teacher == teacher.id ? 'selected' : '' }>${teacher.name} ${teacher.surname}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-auto">
                <select name="select_by_category" class="form-select form-select-sm ">
                    <option disabled selected value=""><fmt:message key="select.by.category"/></option>
                    <option value=""><fmt:message key="default"/></option>
                    <c:forEach var="category" items="${requestScope.categories}">
                        <option value="${category.id}"${param.select_by_category == category.id ? 'selected' : '' }>${category.title}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-auto">
                <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message
                        key="submit"/></button>
            </div>
        </div>
    </form>
</div>