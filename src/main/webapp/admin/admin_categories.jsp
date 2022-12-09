<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title> Facultative </title>
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<%@ include file="../parts/admin_header.jsp" %>
<a class="btn btn-primary" href="controller?action=manage_courses" role="button">Back</a><br>

<div class="table-responsive col-lg-10 mx-auto p-4">
    <table class="table table-success table-striped caption-top table-bordered">
        <caption>
            <a class="btn btn-primary" href="controller?action=show_category_form" role="button">Add category</a>
            All categories in facultative
        </caption>
        <thead>
        <th scope="col">Title</th>
        <th scope="col">Description</th>
        <th scope="col">Action</th>
        </thead>
        <c:forEach var="category" items="${categories}">
            <tbody>
            <td>${category.title}</td>
            <td>${category.description}</td>
            <td>
                <a href="controller?action=show_category_form&category_id=${category.id}">Edit</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="controller?action=delete_category&category_id=${category.id}">Delete</a>
            </td>
            </tbody>
        </c:forEach>
    </table>
</div>
</body>
</html>