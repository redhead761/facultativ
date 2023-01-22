<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

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
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<jsp:include page="/parts/header.jsp"/>

<div class="text-center">
    <h2><fmt:message key="all.course"/></h2>
    <hr>
</div>


<div class="col-lg-10 mx-auto p-5">
    <table class="table table-light table-striped caption-top table-bordered">
        <div class="row justify-content-between">
            <div class="col-md-auto mb-2">
                <form action="${pageContext.request.contextPath}/controller" method="get">
                    <input type="hidden" name="action" value="courses"/>
                    <input type="hidden" name="records_per_page" value="${requestScope.records_per_page}"/>

                    <div class="row justify-content-start">
                        <div class="col-md-auto">
                            <select name="sort" class="form-select form-select-sm">
                                <option disabled selected value=""><fmt:message key="sort"/></option>
                                <option value=""><fmt:message key="default"/></option>
                                <option value="course.title" ${requestScope.sort == 'course.title' ? 'selected' : '' }>
                                    <fmt:message key="alphabetical"/></option>
                                <option value="course.duration" ${requestScope.sort == 'course.duration' ? 'selected' : '' }>
                                    <fmt:message key="duration"/></option>
                                <option value="course.amount_students" ${requestScope.sort == 'course.amount_students' ? 'selected' : '' }>
                                    <fmt:message key="amount.students"/></option>
                            </select>
                        </div>

                        <div class="col-md-auto mt-1">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="DESC" id="flexCheckDefault"
                                       name="order" ${requestScope.order == 'DESC' ? 'checked' : '' }>
                                <label class="form-check-label" for="flexCheckDefault">
                                    <fmt:message key="reverse"/>
                                </label>
                            </div>
                        </div>

                        <div class="col-md-auto">
                            <select name="select_by_teacher" class="form-select form-select-sm">
                                <option disabled selected value=""><fmt:message key="select.by.teacher"/></option>
                                <option value=""><fmt:message key="default"/></option>
                                <c:forEach var="teacher" items="${requestScope.teachers}">
                                    <option value="${teacher.id}" ${requestScope.select_by_teacher == teacher.id ? 'selected' : '' }>${teacher.name} ${teacher.surname}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-auto">
                            <select name="select_by_category" class="form-select form-select-sm ">
                                <option disabled selected value=""><fmt:message key="select.by.category"/></option>
                                <option value=""><fmt:message key="default"/></option>
                                <c:forEach var="category" items="${requestScope.categories}">
                                    <option value="${category.id}"${requestScope.select_by_category == category.id ? 'selected' : '' }>${category.title}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-auto">
                            <button type="submit" class="btn btn-outline-secondary btn-sm"><fmt:message
                                    key="submit"/></button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-auto">
                <a class="btn btn-outline-secondary btn-sm mt-3"
                   href="${pageContext.request.contextPath}/controller?action=download_courses"
                   role="button"><fmt:message key="download.courses"/></a>
            </div>
        </div>
        <thead>
        <th scope="col"><fmt:message key="title"/></th>
        <th scope="col"><fmt:message key="duration"/></th>
        <th scope="col"><fmt:message key="start.date"/></th>
        <th scope="col"><fmt:message key="amount.students"/></th>
        <th scope="col"><fmt:message key="category"/></th>
        <th scope="col"><fmt:message key="status"/></th>
        <th scope="col"><fmt:message key="teacher"/></th>
        </thead>
        <c:forEach var="course" items="${requestScope.courses}">
            <tbody>
            <td>${course.title}</td>
            <td>${course.duration}</td>
            <td>${course.startDate}</td>
            <td>${course.amountStudents}</td>
            <td>${course.getCategory().title}</td>
            <td>${course.getStatus()}</td>
            <td>
                <c:if test="${course.getTeacher().getName() != null}">
                    <c:out value="${course.getTeacher().getName()} ${course.getTeacher().getSurname()}"/>
                </c:if>
            </td>
            </tbody>
        </c:forEach>
    </table>
    <tags:pagination
            href="${pageContext.request.contextPath}/controller?action=courses&sort=${requestScope.sort}&order=${requestScope.order}&select_by_category=${requestScope.select_by_category}&select_by_teacher=${requestScope.select_by_teacher}"/>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>