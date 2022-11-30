<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
    <title> Facultative </title>
    </head>

    <body>
        <div align="center">
            <h2>Auth page</h2>
            ${error}
            <form action="controller" method="post">
                <input type="hidden" name="action" value="auth" />
                Login:   <input name="login"/><br><br>
                Password:   <input type="password" name="password" /><br><br>
                 <input type="submit" value="Log in"/>
            </form>
            Don't have account?
            <a href="register.jsp"> Register </a>
        </div>
    </body>
</html>