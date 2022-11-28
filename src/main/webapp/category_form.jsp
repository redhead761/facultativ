<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title> Facultative </title>
</head>

<body>
<%@ include file="parts/admin_header.jsp" %><br><br>

    <h2>Please fill in the fields</h2>
    ${message}

    <c:if test="${category != null}">
                <form action="controller" method="post">
                <input type="hidden" name="action" value="update_category" />
                 <input type="hidden" name="category_id" value="<c:out value='${category.id}' />" />
    </c:if>

    <c:if test="${category == null}">
                <form action="controller" method="post">
                <input type="hidden" name="action" value="add_category" />
    </c:if>
                Title:   <input name="title"/><br>
                Description: <input name="description" /><br>
                <input type="submit" value="enter"/>
    </form>

    <a href="controller?action=admin_categories">Back</a><br>

</body>
</html>