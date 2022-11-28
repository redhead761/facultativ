<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>

<head>
    <title> Admin cabinet </title>
</head>

<body>
    <h2> Admin data </h2>
    <hr>
    Name: ${user.name}<br>
    Surname: ${user.surname}<br>
    Email: ${user.email}<br>
<%@ include file="parts/admin_header.jsp" %>

    <a href="controller?action=log_out">Log out</a>
</body>

</html>