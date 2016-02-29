<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CRM_OCEAN - Registration page</title>
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <c:choose>
        <c:when test="${empty userForm.name}">
            <h2>Registration Form</h2>
        </c:when>
        <c:otherwise>
            <h2>Edit User Form</h2>
        </c:otherwise>
    </c:choose>
    <form:form class="form-horizontal" method="post" action="/users/add" modelAttribute="userForm">
        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="name" class="col-sm-3 control-label">User Name</label>
                <div class="col-sm-9">
                    <form:input path="name" type="text" class="form-control" id="name" placeholder="Full Name"/>
                    <form:errors path="name" class="control-label" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="login">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="login" class="required col-sm-3 control-label">Login</label>
                <div class="col-sm-9">
                    <form:input path="login" class="form-control" id="login" placeholder="login"/>
                    <form:errors path="login" class="control-label" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="password" class="required col-sm-3 control-label">Password</label>
                <div class="col-sm-9">
                    <form:password path="password" class="form-control" id="password" placeholder="password"/>
                    <form:errors path="password" class="control-label" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="photo">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-3 control-label" for="photo">Photo</label>
                <div class="col-sm-9">
                    <form:input path="photo" type="file" id="photo"/>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="email" class="col-sm-3 control-label">Email</label>
                <div class="col-sm-9">
                    <form:input path="email" class="form-control" id="email" placeholder="Email"/>
                    <form:errors path="email" class="control-label" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="phoneWork">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="phoneWork" class="col-sm-3 control-label">Phone work</label>
                <div class="col-sm-9">
                    <form:input path="phoneWork" class="form-control" id="phoneWork" placeholder="Phone Work"/>
                    <form:errors path="phoneWork" class="control-label" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="phoneHome">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="phoneHome" class="col-sm-3 control-label">Phone home</label>
                <div class="col-sm-9">
                    <form:input path="phoneHome" class="form-control" id="phoneHome" placeholder="Phone Home"/>
                    <form:errors path="phoneHome" class="control-label" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="language">
            <div class="form-group">
                <label for="language" class="col-sm-3 control-label">Language</label>
                <div class="col-sm-9">
                    <form:select path="language" name="language" id="language" class="form-control">
                        <form:option value=""></form:option>
                        <form:option value="EN">English</form:option>
                        <form:option value="RU">Rus</form:option>
                    </form:select>
                </div>
            </div>
        </spring:bind>

        <div class="form-group">
            <div class="col-sm-9 col-sm-offset-3">
                <c:choose>
                    <c:when test="${empty userForm.name}">
                        <button type="submit" class="btn btn-primary btn-block">Register</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn btn-primary btn-block">Update</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</div>

</body>
</html>
