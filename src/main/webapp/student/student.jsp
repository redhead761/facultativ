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
    <title> Facultative </title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="wrapper">
    <jsp:include page="../parts/header.jsp"/>
    <jsp:include page="../parts/student_header.jsp"/>
    <main class="main">
        <tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>

        <div class="col-lg-10 mx-auto p-5">
            <table class="table table-light table-striped caption-top table-bordered">
                <tags:param_cap action="show_all_courses"/>
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
                    <td><fmt:message key="${course.getStatus()}"/></td>
                    <td>${course.getTeacher().getName()} ${course.getTeacher().getSurname()}</td>
                    <td>
                        <c:if test="${course.getStatus() eq 'COMING_SOON'}">
                            <form action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="action" value="enroll"/>
                                <input type="hidden" name="course_id" value="${course.id}"/>
                                <input type="hidden" name="current_page" value="${param.current_page}"/>
                                <input type="hidden" name="records_per_page" value="${param.records_per_page}"/>
                                <input type="hidden" name="sort" value="${param.sort}"/>
                                <input type="hidden" name="select_by_category" value="${param.select_by_category}"/>
                                <input type="hidden" name="select_by_teacher" value="${param.select_by_teacher}"/>

                                <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message
                                        key="enroll"/></button>
                            </form>
                        </c:if>
                    </td>
                    </tbody>
                </c:forEach>
            </table>
            <tags:pagination
                    href="${pageContext.request.contextPath}/controller?action=show_all_courses&sort=${param.sort}&order=${param.order}&select_by_category=${param.select_by_category}&select_by_teacher=${param.select_by_teacher}"/>
        </div>
    </main>
    <jsp:include page="/parts/footer.jsp"/>
</div>
</body>
</html>