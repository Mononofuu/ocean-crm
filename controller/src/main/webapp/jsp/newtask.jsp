<!DOCTYPE html>
<!--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cal" uri="/WEB-INF/callendarTag" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
-->
<html>
<head>
    <title>Список Задач</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="../jsp/menu.jsp"/>
    <link href="../resources/css/tasklist.css" rel="stylesheet" type="text/css"/>

    <script src="../resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../resources//js/tasklist.js" type="text/javascript"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/smoothness/jquery-ui.css"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.css"/>

    <link href="../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="../resources/css/bootstrap-datetimepicker.min.css"/>
    <jsp:useBean id="datetime" class="java.util.Date"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-5 window autowindow">
            <form class="form-horizontal" action="newtask" method="post" id="newtaskform">
                <h4 align="center">Новая задача</h4>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="period">Период</label>
                    <div class="col-sm-9">
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
                    <label class="col-sm-3 control-label" for="datepicker">или выбрать</label>
                    <div class="col-sm-5">
                        <input class="form-control" type="text" id="datepicker" name="duedate" placeholder="дата">
                    </div>
                    <div class="col-sm-4">
                        <input class="form-control" type="text" id="timepicker" name="duetime" placeholder="время">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="subject">Название</label>
                    <div class="col-sm-9">
                        <select class="form-control" name="subject" id="subject">
                            <option value="" disabled selected>Выбрать</option>
                            <c:forEach var="deal" items="${deallist}">
                                <option value="${deal.id}">${deal.name}</option>
                            </c:forEach>
                            <c:forEach var="contact" items="${contactlist}">
                                <option value="${contact.id}">${contact.name}</option>
                            </c:forEach>
                            <c:forEach var="company" items="${companylist}">
                                <option value="${company.id}">${company.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="taskresponsible">Ответственный</label>
                    <div class="col-sm-9">
                        <select class="form-control" name="taskresponsible" id="taskresponsible">
                            <option value="" disabled selected>Выбрать</option>
                            <c:forEach var="user" items="${userslist}">
                                <option value="${user.id}">${user.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="tasktype">Тип задачи</label>
                    <div class="col-sm-9">
                        <select class="form-control" name="tasktype" id="tasktype">
                            <c:forEach var="tasktype" items="${tasktypes}">
                                <option value="${tasktype}">${tasktype.toString()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="tasktext">Текст задачи</label>
                    <div class="col-sm-9">
                            <textarea class="form-control" name="tasktext" id="tasktext" placeholder="Текст задачи"
                                      rows="2"></textarea>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6"></div>
                    <input class="btn btn-primary" type="submit" value="Добавить" id="addtask">
                    <input class="btn resetbutton" type="reset" value="Сбросить">
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
