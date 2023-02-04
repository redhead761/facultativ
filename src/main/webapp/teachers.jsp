<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

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
    <link href="style.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="wrapper">
    <jsp:include page="/parts/header.jsp"/>
    <div class="text-center">
        <h2><fmt:message key="our.teachers"/></h2>
        <hr>
    </div>
    <main class="main">
        <div class="col-lg-10 mx-auto p-5">
            <table class="table table-light table-striped caption-top table-bordered">
                <thead>
                <th scope="col"><fmt:message key="name"/></th>
                <th scope="col"><fmt:message key="surname"/></th>
                <th scope="col"><fmt:message key="email"/></th>
                </thead>
                <c:forEach var="teacher" items="${requestScope.teachers}">
                    <tbody>
                    <td>${teacher.name}</td>
                    <td>${teacher.surname}</td>
                    <td>${teacher.email}</td>
                    </tbody>
                </c:forEach>
            </table>
            <tags:pagination href="${pageContext.request.contextPath}/controller?action=teachers"/>
        </div>
    </main>
    <jsp:include page="/parts/footer.jsp"/>
</div>
</body>
</html>