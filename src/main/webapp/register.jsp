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
    <link href="style.css" rel="stylesheet" type="text/css">

</head>

<body>

<div class="wrapper">
    <jsp:include page="/parts/header.jsp"/>
    <main class="main">
        <div class="row">
            <div class="col-auto">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp"><fmt:message key="back.home"/></a>
                    </li>
                </ul>
            </div>
        </div>

        <tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>

        <section>
            <div class="container">
                <div class="row d-flex justify-content-center align-items-center">
                    <div class="col-lg-12 col-xl-11">
                        <div class="card-body p-md-5">
                            <div class="row justify-content-center">
                                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                                    <p class="text-center h1 fw-bold mb-3 mx-1 mx-md-4"><fmt:message key="sign.up"/></p>

                                    <form class="mx-1 mx-md-4" action="controller" method="post">
                                        <input type="hidden" name="action" value="register"/>

                                        <div class="form-floating d-flex flex-row align-items-center mb-4">
                                            <input class="form-control" name="login" id="floatingInputLogin"
                                                   placeholder="login" value="${requestScope.student.login}"
                                                   pattern="^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d.-]{4,16}$"
                                                   title="Login must..." required>
                                            <label for="floatingInputLogin"><fmt:message key="login"/></label>
                                        </div>

                                        <div class="row">
                                            <div class="col-lg-6">
                                                <div class="form-floating d-flex flex-row align-items-center">
                                                    <input class="form-control" name="password" type="password"
                                                           id="password"
                                                           placeholder="password"
                                                           pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                                                           title="Password must..." required>
                                                    <label for="password"><fmt:message key="password.login"/></label>
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <div class="form-floating d-flex flex-row align-items-center">
                                                    <input class="form-control" name="repeat_password" type="password"
                                                           id="repeat_password"
                                                           placeholder="confirm password"
                                                           pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                                                           title="Password must..." required>
                                                    <label for="repeat_password"><fmt:message
                                                            key="confirm.password"/></label>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-check-inline mb-2">
                                            <input class="form-check-input" type="checkbox"
                                                   onclick="checkPass('password');checkPass('repeat_password')"
                                                   id="flexCheckDefault">
                                            <label class="form-check-label" for="flexCheckDefault">
                                                <fmt:message key="show.password"/>
                                            </label>
                                        </div>

                                        <div class="row">
                                            <div class="col-lg-6">
                                                <div class="form-floating d-flex flex-row align-items-center mb-4 ">
                                                    <input class="form-control" name="name" id="floatingInputName"
                                                           placeholder="name" value="${requestScope.student.name}"
                                                           pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'-]{1,30}"
                                                           title="Name must..."
                                                           required>
                                                    <label for="floatingInputName"><fmt:message key="name"/></label>
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <div class="form-floating d-flex flex-row align-items-center mb-4">
                                                    <input class="form-control" name="surname" id="floatingInputSurname"
                                                           placeholder="surname"
                                                           value="${requestScope.student.surname}"
                                                           pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'-]{1,30}"
                                                           title="Surname must..."
                                                           required>
                                                    <label for="floatingInputSurname"><fmt:message
                                                            key="surname"/></label>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-floating d-flex flex-row align-items-center mb-4">
                                            <input type="email" class="form-control" name="email"
                                                   id="floatingInputEmail"
                                                   placeholder="email"
                                                   value="${requestScope.student.email}"
                                                   pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$" title="Email must..."
                                                   required>
                                            <label for="floatingInputEmail"><fmt:message key="email"/></label>
                                        </div>

                                        <div class="form-floating d-flex flex-row align-items-center mb-4">
                                            <input type="number" class="form-control" name="course_number"
                                                   id="floatingInputNumber"
                                                   placeholder="Course number"
                                                   value="${requestScope.student.courseNumber}"
                                                   min="1" max="6"
                                                   required>
                                            <label for="floatingInputNumber"><fmt:message key="course.number"/></label>
                                        </div>

                                        <div class="d-flex justify-content-center">
                                            <button type="submit" class="btn btn-primary btn-lg"><fmt:message
                                                    key="register"/></button>
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
        </section>
    </main>
    <jsp:include page="/parts/footer.jsp"/>
</div>
<script lang="java_script">
    function checkPass(p) {
        let x = document.getElementById(p);
        if (x.type === "password") {
            x.type = "text";
        } else {
            x.type = "password";
        }
    }
</script>
</body>
</html>