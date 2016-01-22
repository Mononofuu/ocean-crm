<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<style>
    <%@include file='../resources/css/menu.css' %>
</style>
<html>
<fmt:bundle basename="app">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="menu"/></title>
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
                            <a href="#"><fmt:message key="profile"/></a>
                            <a href="/logout"><fmt:message key="logout"/></a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="username"><fmt:message key="notloggedin"/></div>
                        <a href="/signin"><fmt:message key="login"/></a>
                    </c:otherwise>
                </c:choose>
            </div>
        </li>
        <li>
            <a href="/dashboard"><fmt:message key="dashboard"/></a>
        </li>
        <li>
            <a href="/dealslist"><fmt:message key="deals"/></a>
        </li>
        <li>
            <a href="/contactlist"><fmt:message key="contacts"/></a>
        </li>
        <li>
            <a href="/tasklist"><fmt:message key="tasks"/></a>
        </li>
        <li>
            <a href="#6"><fmt:message key="analytics"/></a>
        </li>
        <li>
            <a href="#7"><fmt:message key="settings"/></a>
            <ul class="sub-menu">
                <li><a class="header" href="#"><fmt:message key="settings"/></a></li>
                <li><a href="#"><fmt:message key="generalsettings"/></a></li>
                <li><a href="/deal_status"><fmt:message key="salesstage"/></a></li>
                <li><a href="#"><fmt:message key="usersandrights"/></a></li>
                <li><a href="#"><fmt:message key="fieldeditor"/></a></li>
                <li><a href="#"><fmt:message key="businessprocesses"/></a></li>
                <li><a href="#"><fmt:message key="visithistory"/></a></li>
                <li><a href="#"><fmt:message key="security"/></a></li>
            </ul>
        </li>
    </ul>
    </body>
</fmt:bundle>
</html>
