<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/crm-ocean.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <jsp:include page="/jsp/menu.jsp" />
</head>
<body>
<div class="row-fluid">
    <div style="float:left;width: 65%;height: 100%;">
        <fieldset class="scheduler-border">
            <legend class="scheduler-border"><spring:message code="label.dashboard"/></legend>
            <div class="row-fluid">
                <div id="widgetsInLeft" style="float:left;width:50%;height: auto;">
                    <div id="widgetDeals">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"><spring:message code="label.deals"/></legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;"><spring:message code="label.totalamount"/>:</div>
                                <div style="float: right;alignment: right;">${allDeals}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;"><spring:message code="label.budget"/>:</div>
                                <div style="float: right;alignment: right;">${dealsBudget}</div>
                            </div>
                        </fieldset>
                    </div>
                    <div id="widgetDealsWithoutTasks">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"><spring:message code="label.dealswotasks"/></legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;"><spring:message code="label.dealswotasks"/>:</div>
                                <div style="float: right;alignment: right;">${dealsWithoutTasks}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;"><spring:message code="label.dealswithtasks"/>:</div>
                                <div style="float: right;alignment: right;">${dealWithTasks}</div>
                            </div>
                        </fieldset>
                    </div>
                    <div id="widgetSuccessDeals">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"><spring:message code="label.dealssuccess"/></legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;"><spring:message code="label.dealssuccess"/>:</div>
                                <div style="float: right;alignment: right;">${successDeals}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;"><spring:message code="label.unrealizeddeals"/>:</div>
                                <div style="float: right;alignment: right;">${unsuccessClosedDeals}</div>
                            </div>
                        </fieldset>
                    </div>

                </div>
                <div id="widgetsIntRight" style="float:left;width:50%;height: auto;">
                    <div id="widgetTasksInProgressAndClosed">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"><spring:message code="label.tasksinpogressandfinished"/></legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;"><spring:message code="label.tasksinpogress"/>:</div>
                                <div style="float: right;alignment: right;">${tasksInProgress}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;"><spring:message code="label.finished"/>:</div>
                                <div style="float: right;alignment: right;">${finishedTasks}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;"><spring:message code="label.expired"/>:</div>
                                <div style="float: right;alignment: right;">${overdueTasks}</div>
                            </div>
                        </fieldset>
                    </div>
                    <div id="widgetAllContactsAllCompanies">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"><spring:message code="label.totalcontactsandcompanies"/></legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;"><spring:message code="label.contacts"/>:</div>
                                <div style="float: right;alignment: right;">${contacts}</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;"><spring:message code="label.company"/>:</div>
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
            <legend class="scheduler-border"><spring:message code="label.lastevents"/></legend>
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
</body>
</html>
