<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title> Facultative </title>
</head>

<body>

    <h2>Register page</h2>
    ${error}
    <form action="controller" method="post">
       <input type="hidden" name="action" value="register" />
       <input type="hidden" name="type" value="student" />
       Login:   <input name="login"/><br>
       Password:   <input type="password" name="password" /><br>
       Repeat password: <input type="password" name="repeat_password" /><br>
       Name: <input name="name" /><br>
       Surname: <input name="surname" /><br>
       Email: <input name="email" /><br>
       <input type="submit" value="enter"/>
    </form>
    <a href="auth.jsp"> Back </a>
</body>
</html>