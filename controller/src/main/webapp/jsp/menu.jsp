<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<style>
    <%@include file='../resources/css/menu.css' %>
</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="label.menu"/></title>
        <link href="../resources/css/menu.css" rel="stylesheet" type="text/css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
        <script src="../resources/js/menu.js"></script>
    </head>
    <body>
    <div class="form-inline">
        <a href="<c:url value="/locale?lang=ru"/>">RU</a>
        <a href="<c:url value="/locale?lang=en"/>">EN</a>
    </div>
    <ul class="menu">
        <li>
            <a class="logo" href="#1">CRM-OCEAN</a>
            <div class="profile">
                <c:choose>
                    <c:when test="${not empty user}">
                        <div class="username">${user.name}</div>
                        <div>
                            <a href="#"><spring:message code="label.profile"/></a>
                            <a href="/logout"><spring:message code="label.logout"/></a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="username"><spring:message code="label.notloggedin"/></div>
                        <a href="/signin"><spring:message code="label.login"/></a>
                    </c:otherwise>
                </c:choose>
            </div>
        </li>
        <li>
            <a href="/dashboard"><spring:message code="label.dashboard"/></a>
        </li>
        <li>
            <a href="/dealslist"><spring:message code="label.deals"/></a>
        </li>
        <li>
            <a href="/contactlist"><spring:message code="label.contacts"/></a>
        </li>
        <li>
            <a href="/tasklist"><spring:message code="label.tasks"/></a>
        </li>
        <li>
            <a href="#6"><spring:message code="label.analytics"/></a>
        </li>
        <li>
            <a href="#7"><spring:message code="label.settings"/></a>
            <ul class="sub-menu">
                <li><a class="header" href="#"><spring:message code="label.settings"/></a></li>
                <li><a href="#"><spring:message code="label.generalsettings"/></a></li>
                <li><a href="/deal_status"><spring:message code="label.salesstage"/></a></li>
                <li><a href="#"><spring:message code="label.usersandrights"/></a></li>
                <li><a href="#"><spring:message code="label.fieldeditor"/></a></li>
                <li><a href="#"><spring:message code="label.businessprocesses"/></a></li>
                <li><a href="#"><spring:message code="label.visithistory"/></a></li>
                <li><a href="#"><spring:message code="label.security"/></a></li>
            </ul>
        </li>
    </ul>
    </body>
</html>
