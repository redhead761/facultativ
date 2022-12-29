<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?action=manage_categories">Back
                    to
                    categories</a>
            </li>
        </ul>
    </div>
</div>
<div align="center">

    <h2>Please fill in the fields</h2>

    <c:if test="${sessionScope.message != null}">
        <div class="alert alert-warning alert-dismissible fade show col-lg-2" role="alert">
            <strong>${sessionScope.message}</strong>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <c:if test="${requestScope.category_id != null}">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="action" value="update_category"/>
        <input type="hidden" name="category_id" value="${param.category_id}"/>
        </c:if>

        <c:if test="${requestScope.category_id == null}">
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="action" value="add_category"/>
            </c:if>

            <div class="form-floating mt-4 mb-3 col-lg-2 ">
                <input class="form-control" name="title" id="floatingInputTitle" placeholder="title"
                       value="${requestScope.title}"
                       pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9\\s\\-_,\\.:;()''\'\'#№]{1,30}" title="title must..." required>
                <label for="floatingInputTitle">Title</label>
            </div>

            <div class="form-floating mt-4 mb-3 col-lg-2 ">
                <input class="form-control" name="title" id="floatingInputDescription" placeholder="description"
                       value="${requestScope.description}"
                       pattern="^[\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={} ]{0,200}" title="description must...">
                <label for="floatingInputDescription">Description</label>
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>
        </form>

</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>