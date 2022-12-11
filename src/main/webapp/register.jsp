<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<a class="btn btn-primary" href="auth.jsp" role="button">Back</a>
<div align="center">
    <h2>Add teacher</h2>
    ${message}
    <form action="controller" method="post">
        <input type="hidden" name="action" value="register"/>
        <input type="hidden" name="type" value="student"/>

        <div class="form-floating mt-4 mb-3 col-lg-2 ">
            <input class="form-control" name="login" id="floatingInputLogin" placeholder="login"
                   pattern="^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d.-]{4,16}$" title="Login must..." required>
            <label for="floatingInputLogin">Login</label>
        </div>

        <div class="form-floating mt-4 mb-3 col-lg-2 ">
            <input class="form-control" name="password" id="floatingInputPassword" placeholder="password"
                   pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$" title="Password must..." required>
            <label for="floatingInputPassword">Password</label>
        </div>

        <div class="form-floating mt-4 mb-3 col-lg-2 ">
            <input class="form-control" name="repeat_password" id="floatingInputConfirmPassword"
                   placeholder="confirm password"
                   pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$" title="Password must..." required>
            <label for="floatingInputConfirmPassword">Confirm password</label>
        </div>

        <div class="form-floating mt-4 mb-3 col-lg-2 ">
            <input class="form-control" name="name" id="floatingInputName" placeholder="name"
                   pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'-]{1,30}" title="Name must..." required>
            <label for="floatingInputName">Name</label>
        </div>

        <div class="form-floating mt-4 mb-3 col-lg-2 ">
            <input class="form-control" name="surname" id="floatingInputSurname" placeholder="surname"
                   pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'-]{1,30}" title="Surname must..." required>
            <label for="floatingInputSurname">Surname</label>
        </div>

        <div class="form-floating mt-4 mb-3 col-lg-2 ">
            <input type="email" class="form-control" name="email" id="floatingInputEmail" placeholder="email"
                   pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$" title="Email must..." required>
            <label for="floatingInputEmail">Email</label>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
<jsp:include page="/parts/footer.jsp"/>
</body>
</html>