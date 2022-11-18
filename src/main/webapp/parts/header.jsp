<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<a href="index.jsp">To start page</a> |
 <a href="LoginPage.jsp">Login</a> |
  <a href='login?login=${user.getRole().getTitle()}'>Cabinet</a>