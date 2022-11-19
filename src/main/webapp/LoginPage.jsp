<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title> Facultative </title>
</head>

<body>
    <h2>Hello User! Please, enter your login and password</h2>
    <hr>
    <form action="login" method="post">
       Login:      <input name="login"/><br>
       Password:   <input type="password" name="password" /><br>
       <input type="submit" value="enter"/>
    </form>

</body>

</html>
