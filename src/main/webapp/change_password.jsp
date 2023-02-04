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

<tags:notification value_message="${requestScope.message}" value_error="${requestScope.error}"/>

<section class="vh-80">
    <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-12 col-xl-11">
                <div class="card-body p-md-5">
                    <div class="row justify-content-center">
                        <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                            <p class="text-center h1 fw-bold mb-3 mx-1 mx-md-4"><fmt:message key="change.password"/></p>

                            <form class="mx-1 mx-md-4" action="${pageContext.request.contextPath}/controller"
                                  method="post">
                                <input type="hidden" name="action" value="change_password"/>
                                <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>

                                <div class="form-floating d-flex flex-row align-items-center mb-4">
                                    <input class="form-control" name="old_password" type="password"
                                           id="old_password"
                                           placeholder="old_password"
                                           pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                                           title="<fmt:message key="password.validate.message"/>" required>
                                    <label for="old_password"><fmt:message key="old.password"/></label>
                                </div>

                                <div class="form-floating d-flex flex-row align-items-center mb-4">
                                    <input class="form-control" name="new_password" type="password"
                                           id="new_password"
                                           placeholder="new_password"
                                           pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                                           title="<fmt:message key="password.validate.message"/>" required>
                                    <label for="new_password"><fmt:message key="new.password"/></label>
                                </div>

                                <div class="form-floating d-flex flex-row align-items-center">
                                    <input class="form-control" name="repeat_password" type="password"
                                           id="repeat_password"
                                           placeholder="confirm password"
                                           pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                                           title="<fmt:message key="password.validate.message"/>" required>
                                    <label for="repeat_password"><fmt:message key="confirm.password"/></label>
                                </div>

                                <div class="form-check-inline mb-2">
                                    <input class="form-check-input" type="checkbox"
                                           onclick="checkPass('old_password');checkPass('repeat_password');checkPass('new_password')"
                                           id="flexCheckDefault">
                                    <label class="form-check-label" for="flexCheckDefault">
                                        <fmt:message key="show.password"/>
                                    </label>
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