<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<div class="filter">
    <p style="text-align: center">Фильтры</p>

    <form action="/dealspyramid" method="get">
        <select name="selectedfilter" title="Стандартные фильтры">
            <option value="" disabled selected>Выберите фильтр</option>
            <option value="open">Открытые сделки</option>
            <option value="my">Только мои сделки</option>
            <option value="success">Успешно завершенные</option>
            <option value="fail">Нереализованные сделки</option>
            <option value="notask">Сделки без задач</option>
            <option value="expired">Сделки c просроченными задачами</option>
            <option value="deleted">Удаленные</option>
        </select>
        <button type="submit">Применить фильтр</button>
        <br>
        <select id="when" name="when" title="Когда">
            <option value="" disabled selected>Когда</option>
            <option value="alltime">За все время</option>
            <option value="today">За сегодня</option>
            <option value="3days">За 3 дня</option>
            <option value="week">За неделю</option>
            <option value="month">За месяц</option>
            <option value="quarter">За квартал</option>
        </select>
        <input id="calend" class="input" type="datetime-local" name="period"><br>
        <select class="input" name="phase">
            <option value="" disabled selected>Этапы</option>
            <c:forEach items="${phases}" var="item">
                <option value="${item.id}">${item.name}</option>
            </c:forEach>
        </select><br>
        <select class="input" name="managers">
            <option value="" disabled selected>Менеджеры</option>
            <c:forEach items="${contacts}" var="item">
                <option value="${item.id}">${item.name}</option>
            </c:forEach>
        </select><br>
        <select name="tasks" title="Задачи">
            <option value="" disabled selected>Задачи</option>
            <option value="">Не учитывать</option>
            <option value="">На сегодня</option>
            <option value="">На завтра</option>
            <option value="">На этой неделе</option>
            <option value="">В этом месяце</option>
            <option value="">В этом квартале</option>
            <option value="">Нет задач</option>
            <option value="">Просрочены</option>
        </select><br>
        <input class="input" type="text" name="tags" placeholder="Теги"/><br>
        <button type="submit">Сохранить фильтр</button>
    </form>

</div>
<div class="visual">
    <fieldset id="primary_contact">
        <legend>Первичный контакт</legend>
        <a>${fn:length(primaryDeals)} сделок: ${primaryDealsBudget}USD</a>
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
        <a>${fn:length(conversationDeals)} сделок: ${conversationDealsBudget}USD</a>
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
        <a>${fn:length(decisionDeals)} сделок: ${decisionDealsBudget}USD</a>
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
        <a>${fn:length(approvalDeals)} сделок: ${approvalDealsBudget}USD</a>
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
</div>
</body>
</html>
