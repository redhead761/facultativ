<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

<jsp:include page="/parts/header.jsp"/>
<jsp:include page="/parts/teacher_header.jsp"/>

<tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>

<section class="vh-80">
    <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-12 col-xl-11">
                <div class="card-body p-md-5">
                    <div class="row justify-content-center">
                        <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                            <p class="text-center h1 fw-bold mb-3 mx-1 mx-md-4"><fmt:message key="edit.profile"/></p>

                            <form class="mx-1 mx-md-4" action="${pageContext.request.contextPath}/controller"
                                  method="post">
                                <input type="hidden" name="action" value="edit_teacher"/>

                                <div class="form-floating d-flex flex-row align-items-center mb-4">
                                    <input class="form-control" name="login" id="floatingInputLogin"
                                           placeholder="login" value="${sessionScope.user.login}"
                                           pattern="^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d.-]{4,16}$"
                                           title="<fmt:message key="login.validate.message"/>" required>
                                    <label for="floatingInputLogin"><fmt:message key="login"/></label>
                                </div>

                                <div class="form-floating d-flex flex-row align-items-center mb-4 ">
                                    <input class="form-control" name="name" id="floatingInputName"
                                           placeholder="name" value="${sessionScope.user.name}"
                                           pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                                           title="<fmt:message key="name.validate.message"/>"
                                           required>
                                    <label for="floatingInputName"><fmt:message key="name"/></label>
                                </div>

                                <div class="form-floating d-flex flex-row align-items-center mb-4">
                                    <input class="form-control" name="surname" id="floatingInputSurname"
                                           placeholder="surname"
                                           value="${sessionScope.user.surname}"
                                           pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                                           title="<fmt:message key="surname.validate.message"/>"
                                           required>
                                    <label for="floatingInputSurname"><fmt:message key="surname"/></label>
                                </div>

                                <div class="form-floating d-flex flex-row align-items-center mb-4">
                                    <input type="email" class="form-control" name="email" id="floatingInputEmail"
                                           placeholder="email"
                                           value="${sessionScope.user.email}"
                                           pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$"
                                           title="<fmt:message key="email.validate.message"/>"
                                           required>
                                    <label for="floatingInputEmail"><fmt:message key="email"/></label>
                                </div>

                                <div class="form-floating d-flex flex-row align-items-center mb-4">
                                    <input class="form-control" name="degree" id="floatingInputDegree"
                                           placeholder="name" value="${sessionScope.user.degree}"
                                           pattern="^[\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!?@#$^&*()={}| ]{1,100}"
                                           title="<fmt:message key="degree.validate.message"/>">
                                    <label for="floatingInputDegree"><fmt:message key="degree"/></label>
                                </div>

                                <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                    <button type="submit" class="btn btn-primary btn-lg"><fmt:message
                                            key="submit"/></button>
                                </div>

                            </form>

                        </div>
                        <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                                 class="img-fluid" alt="Sample image">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="/parts/footer.jsp"/>
</section>
</body>
</html>