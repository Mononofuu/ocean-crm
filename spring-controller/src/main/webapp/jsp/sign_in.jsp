<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>CRM_OCEAN - Login page</title>
        <link href="../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="../resources/css/tasklist.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
    <div class="form-inline">
        <a href="?language=ru">RU</a>
        <a href="?language=en">EN</a>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-xs-5 autowindow">
                <c:url value="/j_spring_security_check" var="loginUrl"/>
                <form class="form-horizontal" action="${loginUrl}" method="post">
                    <h2 class="form-signin-heading"><spring:message code="label.signin"/></h2>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <label for="j_username" class="control-label"><spring:message code="label.emaillogin"/></label>
                        </div>
                        <div class="col-sm-7">
                            <input name="j_username" type="text" id="j_username" class="form-control" placeholder='<spring:message code="label.emaillogin"/>' required autofocus>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <label for="j_password" class="control-label"><spring:message code="label.password"/></label>
                        </div>
                        <div class="col-sm-7">
                            <input name="j_password" type="password" id="j_password" class="form-control" placeholder='<spring:message code="label.password"/>' required>

                        </div>
                    </div>
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="label.enter"/></button>
                    <button class="btn btn-lg btn-primary btn-block" onclick="location.href='/users/add'" type="button">
                        <spring:message code="label.registration"/>
                    </button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <div class="alert alert-info" role="alert">
                    <b>login</b> - testlogin<br>
                    <b>password</b> - testpassword
                </div>
                <c:if test="${param.error!=null}">
                    <div class="alert alert-warning">
                        ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                    </div>
                </c:if>
                <c:if test="${param.logout!=null}">
                    <div class="alert alert-warning">
                        You have been logged out.
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    </body>
</html>
