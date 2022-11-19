<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div align="center">
            <table border="1" cellpadding="6">
                <caption><h2>All courses in facultative</h2></caption>
                <tr>
                    <th>Title</th>
                    <th>Duration</th>
                    <th>Start date</th>
                    <th>Students on course</th>
                    <th>Category</th>
                    <th>Status</th>
                </tr>
                <c:forEach var="course" items="${courses}">
                    <tr>
                        <td><c:out value="${course.title}" /></td>
                        <td><c:out value="${course.duration}" /></td>
                        <td><c:out value="${course.startDate}" /></td>
                        <td><c:out value="${course.studentsOnCourse}" /></td>
                        <td><c:out value="${course.getCategory().title}" /></td>
                        <td><c:out value="${course.getStatus().title}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <a href="student?action=sortaz">Back</a>|