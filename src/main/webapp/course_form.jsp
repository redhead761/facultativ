<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title> Facultative </title>
</head>

<body>
<%@ include file="parts/admin_header.jsp" %>
    <h2>Please fill in the fields</h2>
    ${message}

        <c:if test="${course != null}">
                    <form action="controller" method="post">
                    <input type="hidden" name="action" value="update_course" />
                    <input type="hidden" name="course_id" value="<c:out value='${course.id}' />" />
        </c:if>

        <c:if test="${course == null}">
                <form action="controller" method="post">
                <input type="hidden" name="action" value="add_course" />
        </c:if>

        Title:  <input name="title"/><br>
        Duration: <input name="duration"/><br>
        Start date: <input type="date" name="start_date"/><br>
        Description: <input name="description" /><br>
        Category: <select name="category">
                          <c:forEach var="category" items="${categories}">
                          <option value="${category.id}">${category.title}</option>
                          </c:forEach>
                      </select><br>
        Status: <select name="status">
                                  <c:forEach var="status" items="${statuses}">
                                  <option>${status.valueOf(status)}</option>
                                  </c:forEach>
                              </select><br>
        <input type="submit" value="enter"/>
    </form>
    <a href="controller?action=admin_courses">Back</a><br>

</body>
</html>