<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <div align="center">
            <h2>Auth page</h2>
            ${error}

            <form action="controller" method="post">
                <input type="hidden" name="action" value="auth" />

                <div class="form-floating mb-3 col-lg-4 ">
                  <input class="form-control" name="login" id="floatingInput" placeholder="name@example.com">
                  <label for="floatingInput">Login</label>
                </div>

                <div class="form-floating col-lg-4 ">
                  <input type="password" class="form-control" name="password" id="floatingPassword" placeholder="Password">
                  <label for="floatingPassword">Password</label>
                </div>

                <button type="submit" class="btn btn-light mt-2 mb-2">Log in</button>
            </form>

            Don't have account?
            <a href="register.jsp"> Register </a>
        </div>

    </body>
</html>