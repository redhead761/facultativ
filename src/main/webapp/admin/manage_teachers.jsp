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
    <jsp:include page="../parts/admin_header.jsp"/>
    <main class="main">

        <tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>


        <div class="col-lg-10 mx-auto p-5">
            <table class="table table-light table-striped caption-top table-bordered">
                <caption>
                    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/add_teacher.jsp"
                       role="button"><fmt:message key="add.teacher"/></a>
                </caption>
                <thead>
                <th scope="col"><fmt:message key="name"/></th>
                <th scope="col"><fmt:message key="surname"/></th>
                <th scope="col"><fmt:message key="email"/></th>
                <th scope="col"><fmt:message key="degree"/></th>
                <th scope="col"><fmt:message key="action"/></th>
                </thead>
                <c:forEach var="teacher" items="${requestScope.teachers}">
                    <tbody>
                    <td>${teacher.name}</td>
                    <td>${teacher.surname}</td>
                    <td>${teacher.email}</td>
                    <td>${teacher.degree}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="action" value="delete_teacher"/>
                            <input type="hidden" name="teacher_id" value="${teacher.id}"/>
                            <input type="hidden" name="current_page" value="${requestScope.current_page}"/>
                            <input type="hidden" name="records_per_page" value="${requestScope.records_per_page}"/>

                            <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message
                                    key="delete"/></button>
                        </form>
                    </td>
                    </tbody>
                </c:forEach>
            </table>
            <tags:pagination href="${pageContext.request.contextPath}/controller?action=manage_teachers"/>
        </div>
    </main>
    <jsp:include page="/parts/footer.jsp"/>
</div>
</body>
</html>