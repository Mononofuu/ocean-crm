<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/dealspyramid.css' %>
</style>
<!DOCTYPE html>
<html>
<head>
    <title>Воронка</title>
</head>
<body>
<fieldset id="primary_contact">
    <legend>Первичный контакт</legend>
    <c:forEach items="${primaryDeals}" var="deal">
        <div class="rectangle">
            <p><a href="http://www.w3schools.com/html/">${deal.name}</a></p>
            <a>${deal.budget}$</a></br>
            <a>${deal.dealCompany.name}</a>
            <c:forEach items="${deal.contacts}" var="contact">
                <a>${contact}</a>
            </c:forEach>
        </div>
    </c:forEach>
</fieldset>

<fieldset id="conversation">
    <legend>Переговоры</legend>
    <c:forEach items="${conversationDeals}" var="deal">
        <div class="rectangle">
            <p><a href="http://www.w3schools.com/html/">${deal.name}</a></p>
            <a>${deal.budget}$</a></br>
            <a>${deal.dealCompany.name}</a>
            <c:forEach items="${deal.contacts}" var="contact">
                <a>${contact}</a>
            </c:forEach>
        </div>
    </c:forEach>
</fieldset>

<fieldset id="decision">
    <legend>Принимают решение</legend>
    <c:forEach items="${decisionDeals}" var="deal">
        <div class="rectangle">
            <p><a href="http://www.w3schools.com/html/">${deal.name}</a></p>
            <a>${deal.budget}$</a></br>
            <a>${deal.dealCompany.name}</a>
            <c:forEach items="${deal.contacts}" var="contact">
                <a>${contact}</a>
            </c:forEach>
        </div>
    </c:forEach>
</fieldset>

<fieldset id="approval">
    <legend>Согласование договора</legend>
    <c:forEach items="${approvalDeals}" var="deal">
        <div class="rectangle">
            <p><a href="http://www.w3schools.com/html/">${deal.name}</a></p>
            <a>${deal.budget}$</a></br>
            <a>${deal.dealCompany.name}</a>
            <c:forEach items="${deal.contacts}" var="contact">
                <a>${contact}</a>
            </c:forEach>
        </div>
    </c:forEach>
</fieldset>
</body>
</html>
