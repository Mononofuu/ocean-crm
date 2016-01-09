<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 26.12.2015
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/bootstrap.css' %>
</style>

<html>
<head>
    <title>Редактирование задачи</title>
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
            <label class="col-xs-2 control-label" for="taskcomment">Комментарий</label>
            <div class="col-xs-4">
                <input id="taskcomment" name="taskcomment" type="text" placeholder="" class="form-control input-md" required="" value="${task.comment}">
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
            <label class="col-md-2 control-label" for="btn_task_save"></label>
            <div class="col-md-2">
                <button id="btn_task_save" name="btn_task_save" class="btn btn-default">Записать</button>
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