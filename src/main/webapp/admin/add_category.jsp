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
<div class="row">
    <div class="col">
        <jsp:include page="../parts/admin_header.jsp"/>
    </div>
    <div class="col-auto">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?action=manage_categories"><fmt:message
                        key="back.categories"/></a>
            </li>
        </ul>
    </div>
</div>
<tags:notification value_message="${sessionScope.message}" value_error="${sessionScope.error}"></tags:notification>

<div align="center">

    <h2><fmt:message key="add.category.form"/></h2>

    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="action" value="add_category"/>

        <div class="form-floating mt-4 mb-3 col-lg-4 ">
            <input class="form-control" name="title" id="floatingInputTitle" placeholder="title"
                   value="${sessionScope.category.title}"
                   pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9\\s\\-_,\\.:;()''\'\'#№]{1,100}"
                   title="Title must contains 1 to 100 characters"
                   required>
            <label for="floatingInputTitle"><fmt:message key="title"/></label>
        </div>

        <div class="form-floating mt-4 mb-3 col-lg-4 ">
            <input class="form-control" name="description" id="floatingInputDescription" placeholder="description"
                   value="${sessionScope.category.description}"
                   pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9\\s\\-_,\\.:;()''`\'\'#№?!]{0,500}"
                   title="Description must contains 0 to 500 characters">
            <label for="floatingInputDescription"><fmt:message key="description"/></label>
        </div>

        <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
    </form>
</div>
<jsp:include page="/parts/footer.jsp"/>
${sessionScope.remove("error")}
${sessionScope.remove("message")}
${sessionScope.remove("category")}
</body>
</html>