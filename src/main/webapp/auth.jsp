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
    <script src="https://www.google.com/recaptcha/api.js"></script>
    <title> Facultative </title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="wrapper">
    <jsp:include page="/parts/header.jsp"/>
    <tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>
    <main class="main">
        <section>
            <div class="container-fluid h-custom">
                <div class="row d-flex justify-content-center align-items-center h-80">
                    <div class="col-md-9 col-lg-6 col-xl-5">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                             class="img-fluid" alt="Sample image">
                    </div>
                    <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                        <form action="controller" method="post">
                            <input type="hidden" name="action" value="auth"/>

                            <div class="form-floating mt-4 mb-3">
                                <input class="form-control form-control-lg" name="login" id="floatingInput"
                                       placeholder="login"
                                       value="${requestScope.login}"
                                       pattern="^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d.-]{4,16}$"
                                       title="<fmt:message key="login.validate.message"/>" required>
                                <label class="form-label" for="floatingInput"><fmt:message key="login"/></label>
                            </div>

                            <div class="form-floating">
                                <input type="password" class="form-control form-control-lg" name="password"
                                       id="password" placeholder="Password"
                                       pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                                       title="<fmt:message key="password.validate.message"/>"
                                       required>
                                <label class="form-label" for="password"><fmt:message key="password.login"/></label>
                            </div>

                            <div class="d-flex justify-content-between align-items-center">
                                <div class="form-check-inline mb-4">
                                    <input class="form-check-input" type="checkbox" onclick="checkPass()"
                                           id="flexCheckDefault">
                                    <label class="form-check-label" for="flexCheckDefault">
                                        <fmt:message key="show.password"/>
                                    </label>
                                </div>
                                <a href="recovery.jsp" class="text-body mb-4"><fmt:message key="forgot.password"/></a>
                            </div>

                            <div class="g-recaptcha"
                                 data-sitekey="6Len5_sjAAAAAMmiw0qgGKANuS-CJT-1vQi1b83Q"></div>

                            <div class="d-flex justify-content-between align-items-center mt-4">
                                <button type="submit" class="btn btn-primary btn-lg"
                                        style="padding-left: 2.5rem; padding-right: 2.5rem;"><fmt:message
                                        key="log.in"/>
                                </button>
                            </div>

                            <div class="text-center text-lg-start pt-2">
                                <p class="small fw-bold pt-1 mb-0"><fmt:message key="dont.have.account"/><a
                                        href="register.jsp"
                                        class="link-danger"> <fmt:message key="register"/></a>
                                </p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </main>
    <jsp:include page="/parts/footer.jsp"/>
</div>
<script lang="java_script">
    function checkPass() {
        let x = document.getElementById("password");
        if (x.type === "password") {
            x.type = "text";
        } else {
            x.type = "password";
        }
    }
</script>
</body>
</html>