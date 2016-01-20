<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<fmt:bundle basename="app">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>CRM_OCEAN - Login page</title>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/crm-ocean.css" rel="stylesheet">
    </head>
    <body>
    <div class="form-inline">
        <a href="<c:url value="/locale?lang=ru"/>">RU</a>
        <a href="<c:url value="/locale?lang=en"/>">EN</a>
    </div>
    <div class="container">

        <form class="form-signin" method="post">
            <h2 class="form-signin-heading"><fmt:message key="signin"/></h2>
            <label for="inputEmail" class="sr-only"><fmt:message key="email"/></label>
            <input name="login" type="text" id="inputEmail" class="form-control" placeholder=<fmt:message key="email"/> required
                   autofocus>
            <label for="inputPassword" class="sr-only"><fmt:message key="password"/></label>
            <input name="password" type="password" id="inputPassword" class="form-control" placeholder=<fmt:message key="password"/>
                   required>
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login"/></button>
            <button class="btn btn-lg btn-primary btn-block" onclick="location.href='/registration'" type="button">
                <fmt:message key="registration"/>
            </button>

            <%
                List<String> errors = (List<String>) request.getAttribute("errors");
                if (errors != null && errors.size() > 0) { %>

            <%
                for (String error : errors) {
            %>
            <div class="alert alert-warning"><%=error%>
            </div>
            <%
                }
            %>
            <% }%>

        </form>

    </div>
    </body>
</fmt:bundle>
</html>
