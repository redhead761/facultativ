<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="wrapper">
    <jsp:include page="/parts/header.jsp"/>
    <main class="main main-center">
        <div class="h-50">
            <div class="text-black-50">
                <h1 class="mb-3"><fmt:message key="welcome.first"/></h1>
                <h5 class="mb-4"><fmt:message key="welcome.second"/></h5>
                <c:if test="${sessionScope.user == null}">
                    <a class="btn btn-outline-secondary btn-lg m-2" href="register.jsp" role="button"><fmt:message
                            key="sign.up"/></a>
                    <a class="btn btn-outline-secondary btn-lg m-2"
                       href="${pageContext.request.contextPath}/controller?action=auth" role="button"><fmt:message
                            key="log.in"/></a>
                </c:if>
            </div>
        </div>
    </main>
    <jsp:include page="/parts/footer.jsp"/>
</div>
</body>
</html>