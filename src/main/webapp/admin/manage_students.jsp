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
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<jsp:include page="../parts/header.jsp"/>
<jsp:include page="../parts/admin_header.jsp"/>

<tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>

<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <thead>
        <th scope="col"><fmt:message key="name"/></th>
        <th scope="col"><fmt:message key="surname"/></th>
        <th scope="col"><fmt:message key="status"/></th>
        <th scope="col"><fmt:message key="action"/></th>
        </thead>
        <c:forEach var="student" items="${requestScope.students}">
            <tbody>
            <td>${student.name}</td>
            <td>${student.surname}</td>
            <td>${student.block}</td>
            <td>
                <div class="row">
                    <div class="col-md-auto">
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="action" value="update_block"/>
                            <input type="hidden" name="type" value="block"/>
                            <input type="hidden" name="student_id" value="${student.id}"/>
                            <input type="hidden" name="current_page" value="${requestScope.current_page}"/>
                            <input type="hidden" name="records_per_page" value="${requestScope.records_per_page}"/>

                            <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message
                                    key="block"/></button>
                        </form>
                    </div>
                    <div class="col-md-auto">
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="action" value="update_block"/>
                            <input type="hidden" name="type" value="unblock"/>
                            <input type="hidden" name="student_id" value="${student.id}"/>
                            <input type="hidden" name="current_page" value="${requestScope.current_page}"/>
                            <input type="hidden" name="records_per_page" value="${requestScope.records_per_page}"/>

                            <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message
                                    key="unblock"/></button>
                        </form>
                    </div>
                </div>
            </td>
            </tbody>
        </c:forEach>
    </table>
    <tags:pagination href="${pageContext.request.contextPath}/controller?action=manage_students"/>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>