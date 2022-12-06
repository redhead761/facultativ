<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title> Facultative </title>
    </head>

    <body>
        <%@ include file="../parts/admin_header.jsp" %>
        <h2>Please fill in the fields</h2>
        ${message}

        <c:if test="${course_id != null}">
            <form action="controller" method="post">
            <input type="hidden" name="action" value="update_course" />
            <input type="hidden" name="course_id" value="<c:out value='${course_id}' />" />
        </c:if>

        <c:if test="${course_id == null}">
            <form action="controller" method="post">
            <input type="hidden" name="action" value="add_course" />
        </c:if>

            Title:  <input name="title"/><br><br>
            Duration: <input name="duration"/><br><br>
            Start date: <input type="date" name="start_date"/><br><br>
            Description: <input name="description" /><br><br>
            Category: <select name="category">
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.title}</option>
                </c:forEach>
                </select><br><br>
            Status: <select name="status">
                <c:forEach var="status" items="${statuses}">
                    <option>${status.valueOf(status)}</option>
                </c:forEach>
                </select><br><br>
            <input type="submit" value="enter"/>
        </form>

        <a href="controller?action=manage_courses">Back</a><br><br>

    </body>
</html>