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
    <link rel="stylesheet" href="../css/first.css">

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
        <div class="row justify-content-between">
            <tags:param_cap action="manage_courses"/>
            <div class="col-md-auto">
                <a class="btn btn-outline-secondary btn-sm mt-3"
                   href="${pageContext.request.contextPath}/controller?action=show_add_course"
                   role="button"><fmt:message key="add.course"/></a>
            </div>
        </div>
        <thead>
        <th scope="col"><fmt:message key="title"/></th>
        <th scope="col"><fmt:message key="duration"/></th>
        <th scope="col"><fmt:message key="start.date"/></th>
        <th scope="col"><fmt:message key="amount.students"/></th>
        <th scope="col"><fmt:message key="category"/></th>
        <th scope="col"><fmt:message key="status"/></th>
        <th scope="col"><fmt:message key="teacher"/></th>
        <th scope="col"><fmt:message key="action"/></th>
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
                    ${course.getTeacher().getName()} ${course.getTeacher().getSurname()}
                </c:if>
                <c:if test="${course.getTeacher().getName() == null}">
                    <a href="${pageContext.request.contextPath}/controller?action=show_assign_page&course_id=<c:out value='${course.id}'/>"><fmt:message
                            key="assign"/></a>
                </c:if>
            </td>
            <td>
                <div class="row">
                    <div class="col-md-auto">
                        <form action="${pageContext.request.contextPath}/controller" method="get">
                            <input type="hidden" name="action" value="show_edit_course"/>
                            <input type="hidden" name="course_id" value="${course.id}"/>

                            <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message
                                    key="edit"/></button>
                        </form>
                    </div>
                    <div class="col-md-auto">
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="action" value="delete_course"/>
                            <input type="hidden" name="course_id" value="${course.id}"/>
                            <input type="hidden" name="page" value="${requestScope.currentPage}"/>
                            <input type="hidden" name="records_per_page" value="${requestScope.records_per_page}"/>
                            <input type="hidden" name="sort_type" value="${requestScope.sort_type}"/>
                            <input type="hidden" name="select_type" value="${requestScope.select_type}"/>

                            <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message
                                    key="delete"/></button>
                        </form>
                    </div>
                </div>
            </td>
            </tbody>
        </c:forEach>
    </table>
    <tags:pagination
            href="${pageContext.request.contextPath}/controller?action=manage_courses&sort=${param.sort}&order=${param.order}&select_by_category=${param.select_by_category}&select_by_teacher=${param.select_by_teacher}"/>
</div>
<jsp:include page="/parts/footer.jsp"/>
${sessionScope.remove("message")}
${sessionScope.remove("error")}
</body>
</html>