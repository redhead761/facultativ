<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <script src="https://www.google.com/recaptcha/api.js"></script>
    <title>Facultative</title>
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

</body>

<jsp:include page="/parts/header.jsp"/>
<tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>

<div align="center">
    <h3><fmt:message key="recovery"/></h3><br>
    <h6><fmt:message key="enter.email"/></h6>

    <form action="controller" method="post">
        <input type="hidden" name="action" value="recovery_password"/>
        <div class="form-floating col-lg-4 d-flex flex-row align-items-center mb-4">
            <input type="email" class="form-control" name="email" id="floatingInputEmail"
                   placeholder="email"
                   value="${requestScope.email}"
                   pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$" title="Email must contain @"
                   required>
            <label for="floatingInputEmail"><fmt:message key="email"/></label>
        </div>
        <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
            <button type="submit" class="btn btn-primary btn-lg"><fmt:message key="submit"/></button>
        </div>
    </form>
    <a href="auth.jsp"><fmt:message key="log.in"/> </a>
</div>
<jsp:include page="/parts/footer.jsp"/>
</html>
