<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title> Facultative </title>
</head>

<body>

    <h2>Auth page</h2>
    ${error}
    <form action="controller" method="post">
       <input type="hidden" name="action" value="auth" />
       Login:   <input name="login"/><br>
       Password:   <input type="password" name="password" /><br>
       <input type="submit" value="enter"/>
    </form>
    <a href="register.jsp"> Register </a>


</body>

</html>