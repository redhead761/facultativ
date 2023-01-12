<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--<c:set var="language"--%>
<%--       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"--%>
<%--       scope="session"/>--%>
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
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<jsp:include page="/parts/header.jsp"/>
<div id="intro-example" class="text-center min-vh-80">
    <div class="mask">
        <div class="d-flex justify-content-center align-items-center h-50">
            <div class="text-black-50">
                <h1 class="mb-3">Welcome to "Facultative"</h1>
                <h5 class="mb-4">Find your perfect course</h5>
                <a class="btn btn-outline-secondary btn-lg m-2" href="auth.jsp" role="button">Log in</a>
                <a class="btn btn-outline-secondary btn-lg m-2" href="register.jsp" role="button">Sign up</a
                >
            </div>
        </div>
    </div>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>