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

<%@ include file="../parts/admin_header.jsp" %>
<a class="btn btn-primary" href="controller?action=manage_courses" role="button">Back</a>
<div align="center">
    <h2>Please fill in the fields</h2>
    ${message}


    <c:if test="${course_id != null}">
    <form action="controller" method="post">
        <input type="hidden" name="action" value="update_course"/>
        <input type="hidden" name="course_id" value="${course_id}"/>
        </c:if>

        <c:if test="${course_id == null}">
        <form action="controller" method="post">
            <input type="hidden" name="action" value="add_course"/>
            </c:if>

            <div class="form-floating mt-4 mb-3 col-lg-2 ">
                <input class="form-control" name="title" id="floatingInputTitle" placeholder="title"
                       pattern="^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d.-]{4,16}$" title="title must...">
                <label for="floatingInputTitle">Title</label>
            </div>

            <div class="form-floating mt-4 mb-3 col-lg-2">
                <input class="form-control" name="duration" id="floatingInputDuration" placeholder="duration"
                       pattern="^[0-9]{0,3}" title="duration must...">
                <label for="floatingInputDuration">Duration</label>
            </div>

            <div class="form-floating mt-4 mb-3 col-lg-2">
                <input class="form-control" type="date" name="start_date" id="floatingInputDate" placeholder="duration">
                <label for="floatingInputDate">Start date</label>
            </div>

            <div class="dropdown mt-4 mb-3 col-lg-2">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuCategory"
                        data-bs-toggle="dropdown"
                        aria-expanded="false">
                    Category
                </button>
                <ul class="dropdown-menu">
                    <c:forEach var="category" items="${categories}">
                        <li>
                            <button class="list-group-item"
                                    value="category_id=${category.id}">${category.title}</button>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <div class="dropdown mt-4 mb-3 col-lg-2">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuStatus"
                        data-bs-toggle="dropdown"
                        aria-expanded="false">
                    Status
                </button>
                <ul class="dropdown-menu">
                    <c:forEach var="status" items="${statuses}">
                        <li>
                            <button class="list-group-item"
                                    value="status_id=${status.id}">${status.valueOf(status)}</button>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <div class="form-floating mt-4 mb-3 col-lg-2 ">
                <input class="input" type="submit" value="enter">
            </div>
        </form>
</div>

<jsp:include page="/parts/footer.jsp"/>
</body>
</html>
