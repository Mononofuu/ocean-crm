<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 26.12.2015
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cal" uri="/WEB-INF/callendarTag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/bootstrap.css' %>
</style>

<html>
<head>
    <title>Редактирование задачи</title>
    <link href="../resources/css/tasklist.css" rel="stylesheet" type="text/css"/>
    <script src="../resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../resources/js/tasklist.js" type="text/javascript"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/smoothness/jquery-ui.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.css"/>

    <jsp:useBean id="datetime" class="java.util.Date"/>
</head>
<body>

<jsp:useBean id="task" type="com.becomejavasenior.Task" scope="request">
<jsp:setProperty name="task" property="*" />
</jsp:useBean>

<div class="col-xs-2">
    <jsp:include page="menu.jsp"/>
</div>
<div class="col-xs-10">

<form class="form-horizontal" action="taskedit?action=update"  method="post">
    <fieldset>


        <legend>Редактирование задачи</legend>

        <input type="hidden" name="id" value="${task.id}">
        <input type="hidden" name="subjectid" value="${subjectid}">
        <input type="hidden" name="backurl" value="${backurl}">

        <div class="form-group">
            <label class="col-sm-2 control-label" for="period">Период</label>
            <div class="col-sm-4">
                <select class="form-control" name="period" id="period">
                    <option value="today">Сегодня</option>
                    <option value="allday">Весь день</option>
                    <option value="tomorow">Завтра</option>
                    <option value="nextweek">Следующая неделя</option>
                    <option value="nextmonth">Следующий месяц</option>
                    <option value="nextyear">Следующий год</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-2 control-label" for="datepicker">или выбрать</label>
            <div class="col-xs-4">
                <fmt:formatDate value="${task.dueTime}" pattern="HH:ss" var="theFormattedTimeDueTime"/>
                <fmt:formatDate value="${task.dueTime}" pattern="MM/dd/yyyy" var="theFormattedDateDueTime"/>
                <div class="col-xs-7">
                    <input class="form-control" type="text" id="datepicker" name="duedate" value="${theFormattedDateDueTime}"/>
                </div>
                <div class="col-xs-5">
                    <input class="form-control" type="text" id="timepicker" name="duetime" value="${theFormattedTimeDueTime}">
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-2 control-label" for="tasktype">Ответственный</label>
            <div class="col-xs-4">
                <select id="user" name="user" class="form-control">
                    <c:forEach items="${users}" var="user">
                        <c:choose>
                            <c:when test="${user == task.user}">
                                <option selected value=${user.id}>${user.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value=${user.id}>${user.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-2 control-label" for="tasktype">Тип задачи</label>
            <div class="col-xs-4">
                <select id="tasktype" name="tasktype" class="form-control">
                    <c:forEach items="${tasktypes}" var="task_type">
                        <c:choose>
                            <c:when test="${task_type == task.type}">
                                <option selected value=${task_type}>${task_type.toString()}</option>
                            </c:when>
                            <c:otherwise>
                                <option value=${task_type}>${task_type.toString()}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-2 control-label" for="taskcomment">Текст задачи</label>
            <div class="col-xs-4">
                <input id="taskcomment" name="taskcomment" type="text" placeholder="" class="form-control input-md" required="" value="${task.comment}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="btn_task_save"></label>
            <div class="col-md-6">
                <button id="btn_task_save" name="btn_task_update" value="save" class="btn btn-default">Записать</button>
                <button id="btn_task_close" name="btn_task_update" value ="close" class="btn btn-default">Закрыть</button>
                <button id="btn_task_delete" name="btn_task_update" value="delete" class="btn btn-default">Удалить</button>
                <button id="btn_task_cancel" name="btn_task_cancel" class="btn btn-default" onclick="window.history.back()">Отмена</button>
            </div>
            <div class="col-md-2">
            </div>
        </div>

    </fieldset>
</form>
</div>
</body>
</html>
