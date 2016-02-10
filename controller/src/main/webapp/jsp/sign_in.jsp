<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <a href="<c:url value="/locale?lang=ru"/>">RU</a>
        <a href="<c:url value="/locale?lang=en"/>">EN</a>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-xs-5 autowindow">
                <c:url value="/j_spring_security_check" var="loginUrl"/>
                <form class="form-horizontal" action="${loginUrl}" method="post">
                    <h2 class="form-signin-heading">Please sign in</h2>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <label for="j_username" class="control-label">Email address</label>
                        </div>
                        <div class="col-sm-8">
                            <input name="j_username" type="text" id="j_username" class="form-control" placeholder="Email address" required autofocus>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <label for="j_password" class="control-label">Password</label>
                        </div>
                        <div class="col-sm-8">
                            <input name="j_password" type="password" id="j_password" class="form-control" placeholder="Password" required>

                        </div>
                    </div>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                    <button class="btn btn-lg btn-primary btn-block" onclick="location.href='/registration'" type="button">
                        Registration
                    </button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
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
