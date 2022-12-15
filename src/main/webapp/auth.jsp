<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>

<html lang="${language}">
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


<div align="center">

    <h2><fmt:message key="auth.title"/></h2>

    <c:if test="${message != null}">
        <div class="alert alert-warning alert-dismissible fade show col-lg-2" role="alert">
            <strong>${message}</strong>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <form action="controller" method="post">
        <input type="hidden" name="action" value="auth"/>

        <div class="form-floating mt-4 mb-3 col-lg-2 ">
            <input class="form-control" name="login" id="floatingInput" placeholder="login" value="${login}"
                   pattern="^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d.-]{4,16}$" title="Login must..." required>
            <label for="floatingInput"><fmt:message key="auth.login"/></label>
        </div>

        <div class="form-floating col-lg-2 ">
            <input type="password" class="form-control" name="password" id="floatingPassword" placeholder="Password"
                   value="${password}"
                   pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                   title="Password must be between 8 and 20 characters, capital letter and no special characters"
                   required>
            <label for="floatingPassword"><fmt:message key="auth.password"/></label>
        </div>

        <button type="submit" class="btn btn-primary mt-2 mb-2"><fmt:message key="auth.button"/></button>

    </form>

    <fmt:message key="auth.foot.message"/>
    <a href="register.jsp"> Register </a>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>