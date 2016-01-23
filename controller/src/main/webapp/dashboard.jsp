<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Author: Vladislav Lybachevskiy
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CRM_OCEAN - Dashboard</title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/crm-ocean.css" rel="stylesheet">
    <jsp:include page="/jsp/menu.jsp" />
    <link rel="import" href="jsp/menu.jsp" />

</head>
<body>
<div class="row-fluid">
    <div style="float:left;width: 65%;height: 100%;">
        <fieldset class="scheduler-border">
            <legend class="scheduler-border">Виджеты</legend>
            <div class="row-fluid">
                <div id="widgetsInLeft" style="float:left;width:50%;height: auto;">
                    <div id="widgetDeals">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border">Сделки</legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;">Всего сделок:</div>
                                <div style="float: right;alignment: right;">${allDeals}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Бюджет:</div>
                                <div style="float: right;alignment: right;">${dealsBudget}</div>
                            </div>
                        </fieldset>
                    </div>
                    <div id="widgetDealsWithoutTasks">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border">Сделки без задач</legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;">Сделок без задач:</div>
                                <div style="float: right;alignment: right;">${dealsWithoutTasks}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Сделок с задачами:</div>
                                <div style="float: right;alignment: right;">${dealWithTasks}</div>
                            </div>
                        </fieldset>
                    </div>
                    <div id="widgetSuccessDeals">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border">Успешные сделки</legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;">Успешных сделок:</div>
                                <div style="float: right;alignment: right;">${successDeals}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Нереализовано:</div>
                                <div style="float: right;alignment: right;">${unsuccessClosedDeals}</div>
                            </div>
                        </fieldset>
                    </div>

                </div>
                <div id="widgetsIntRight" style="float:left;width:50%;height: auto;">
                    <div id="widgetTasksInProgressAndClosed">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border">Задачи в работе и выполненые</legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;">Задачи в работе:</div>
                                <div style="float: right;alignment: right;">${tasksInProgress}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Выполненные:</div>
                                <div style="float: right;alignment: right;">${finishedTasks}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Просроченные:</div>
                                <div style="float: right;alignment: right;">${overdueTasks}</div>
                            </div>
                        </fieldset>
                    </div>
                    <div id="widgetAllContactsAllCompanies">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border">Всего контактов и Всего компаний</legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;">Контактов:</div>
                                <div style="float: right;alignment: right;">${contacts}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Компаний:</div>
                                <div style="float: right;alignment: right;">${companies}</div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
    <div style="float:right;height: 100%;">
        <fieldset class="scheduler-border" style="height: auto;">
            <legend class="scheduler-border">Последние события</legend>
            <c:forEach var="event" items="${events}">
                <fieldset class="scheduler-border">
                    <fmt:formatDate value="${event.getEventDate()}" pattern="dd-MM-yyyy HH:mm" var="theFormattedEventDate"/>
                    <a href="">${theFormattedEventDate}</a><br>
                    <a href="">${event.getUser().getLogin()}</a><br>
                    ${event.getOperationType().toString()}<br>
                    <a href="">${event.getEventContent()}</a>
                </fieldset>
            </c:forEach>
        </fieldset>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="/js/bootstrap.js"></script>
</body>
</html>
