<!DOCTYPE html>
<!--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
-->
<html>
<head>
    <title>Список Задач</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="../jsp/menu.jsp" />
    <link href="../resources/css/tasklist.css" rel="stylesheet" type="text/css"/>
    <script src="../resources/js/jquery-1.11.3.min.js"></script>
    <script src="../resources/js/bootstrap.min.js"></script>
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <jsp:useBean id="datetime" class="java.util.Date"/>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-3 window">
                Фильтры
            </div>
            <div class="col-md-7">
                <ul class="nav nav-pills">
                    <li class="active"><a href="#todotasks" data-toggle="tab">To-do Line</a></li>
                    <li><a href="#today" data-toggle="tab">День</a></li>
                    <li><a href="#weekstasks" data-toggle="tab">Неделя</a></li>
                    <li><a href="#monthtasks" data-toggle="tab">Месяц</a></li>
                    <li><a href="#tasklist" data-toggle="tab">Список</a></li>
                    <a class="btn btn-success addtask" href="#">Добавить задачу</a>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane active container-fluid" id="todotasks">
                        <div class="row no-gutter">
                            <div class="col-md-4 overdue">
                                <div class="rowname">Просроченные задачи</div>
                                <c:forEach var="task" items="${tasklist}">
                                    <c:if test="${datetime.time>task.dueTime.time}">
                                        <div class="element">
                                            <fmt:formatDate type="both" value="${task.dueTime}" pattern="dd.MM.yyyy HH:mm"/><br>
                                            <div class="comment">${task.comment}</div>
                                            ${task.user.name}<br>
                                            ${task.type}<br>
                                            ${task.subject.name}<br>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="col-md-4 todotoday">
                                <div class="rowname">Задачи на сегодня</div>
                                <fmt:formatDate type="date" value="${datetime}" var="date"/>
                                <c:forEach var="task" items="${tasklist}">
                                    <fmt:formatDate type="date" value="${task.dueTime}" var="dueDate"/>
                                    <c:if test="${date eq dueDate}">
                                        <div class="element">
                                            <fmt:formatDate type="both" value="${task.dueTime}" pattern="dd.MM.yyyy HH:mm"/><br>
                                            <div class="comment">${task.comment}</div>
                                            ${task.user.name}<br>
                                            ${task.type}<br>
                                            ${task.subject.name}<br>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="col-md-4 todotomorow">
                                <div class="rowname">Задачи на завтра</div>
                                <fmt:formatDate type="date" value="${tomorowdate.time}" var="tomorowdate"/>
                                <c:forEach var="task" items="${tasklist}">
                                    <fmt:formatDate type="date" value="${task.dueTime}" var="dueDate"/>
                                    <c:if test="${tomorowdate eq dueDate}">
                                        <div class="element">
                                            <fmt:formatDate type="both" value="${task.dueTime}" pattern="dd.MM.yyyy HH:mm"/><br>
                                            <div class="comment">${task.comment}</div>
                                            ${task.user.name}<br>
                                            ${task.type}<br>
                                            ${task.subject.name}<br>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="today">
                        День
                    </div>
                    <div class="tab-pane" id="weekstasks">
                        Неделя
                    </div>
                    <div class="tab-pane" id="monthtasks">
                        Месяц
                    </div>
                    <div class="tab-pane container-fluid" id="tasklist">
                        <div class="row">
                            <div class="col-md-3">Дата исполнения/Время/<br>Ответственный</div>
                            <div class="col-md-3">Контакт или сделка</div>
                            <div class="col-md-6">Тип/Текст задачи</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
