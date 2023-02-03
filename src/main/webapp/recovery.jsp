<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
    <%--    <style>--%>
    <%--        <%@include file="/css/style.css"%>--%>
    <%--    </style>--%>
    <title>Facultative</title>
    <link href="style.css" rel="stylesheet" type="text/css" media="all">
</head>
<body style="height: 100vh">
<div class="d-flex flex-column" style="height: 100%">
    <jsp:include page="/parts/header.jsp"/>
    <tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>
    <div align="center" style="flex: 1 1 auto">
        <div class="card text-center col-lg-4 mt-4">
            <div class="card-header h5 text-white bg-secondary"><fmt:message key="recovery"/></div>
            <div class="card-body px-5 ">
                <p class="card-text py-2">
                    <fmt:message key="recovery.message"/>
                </p>
                <form action="controller" method="post">
                    <input type="hidden" name="action" value="recovery_password"/>
                    <div class="form-floating col-lg-12 d-flex flex-row align-items-center mb-4">
                        <input type="email" class="form-control" name="email" id="floatingInputEmail"
                               placeholder="email"
                               value="${requestScope.email}"
                               pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$" title="Email must contain @"
                               required>
                        <label for="floatingInputEmail"><fmt:message key="email"/></label>
                    </div>
                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                        <button type="submit" class="btn btn-primary btn-sm my-bnt"><fmt:message key="submit"/></button>
                    </div>
                </form>
                <div class="d-flex justify-content-between mt-4">
                    <a class="" href="auth.jsp"><fmt:message key="login"/></a>
                    <a class="" href="register.jsp"><fmt:message key="register"/></a>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="/parts/footer.jsp"/>
</div>
</body>


<%--<div align="center">--%>
<%--    <h3><fmt:message key="recovery"/></h3><br>--%>
<%--    <h6><fmt:message key="enter.email"/></h6>--%>

<%--    <form action="controller" method="post">--%>
<%--        <input type="hidden" name="action" value="recovery_password"/>--%>
<%--        <div class="form-floating col-lg-4 d-flex flex-row align-items-center mb-4">--%>
<%--            <input type="email" class="form-control" name="email" id="floatingInputEmail"--%>
<%--                   placeholder="email"--%>
<%--                   value="${requestScope.email}"--%>
<%--                   pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$" title="Email must contain @"--%>
<%--                   required>--%>
<%--            <label for="floatingInputEmail"><fmt:message key="email"/></label>--%>
<%--        </div>--%>
<%--        <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">--%>
<%--            <button type="submit" class="btn btn-primary btn-lg"><fmt:message key="submit"/></button>--%>
<%--        </div>--%>
<%--    </form>--%>
<%--    <a href="auth.jsp"><fmt:message key="log.in"/> </a>--%>
<%--</div>--%>


</html>
