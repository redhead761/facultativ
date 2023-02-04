<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>

<footer class="bg-white text-center text-lg-start bottom text-muted">
    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.1);">
        <form>
            <label for="language"></label><select id="language" name="language" onchange="submit()">
            <option value="en" ${sessionScope.language == 'en' ? 'selected' : ''}>English</option>
            <option value="uk_UA" ${sessionScope.language == 'uk_UA' ? 'selected' : ''}>Українська</option>
        </select>
        </form>
        © 2023 Copyright:
        <a class="text-dark"> O.P.</a>
    </div>
    <%--    SCRIP BOOTSTRAP JS--%>
</footer>



