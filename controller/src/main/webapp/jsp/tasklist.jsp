<!DOCTYPE html>
<!--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cal" uri="/WEB-INF/callendarTag" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
-->
<html>
<head>
    <title><spring:message code="label.tasklist"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="../jsp/menu.jsp" />
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
    <link rel="stylesheet" href="../resources/css/bootstrap-datetimepicker.min.css" />
    <jsp:useBean id="datetime" class="java.util.Date"/>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-3 window">
                <h4><spring:message code="label.filters"/></h4>
                <form class="form-horizontal" action="tasklist" method="post">
                    <input type="hidden" name="currentuser" value="${user.id}">
                    <div class="form-group">
                        <div class="col-sm-10">
                            <select class="form-control" size="5" name="filtername" id="filtername">
                                <option value="mytasks"><spring:message code="label.onlymytasks"/></option>
                                <option value="overduetasks"><spring:message code="label.overduetasks"/></option>
                                <option value="complitetasks"><spring:message code="label.complitedtasks"/></option>
                                <option value="alltasks" selected><spring:message code="label.alltasks"/></option>
                                <option value="deletedtasks"><spring:message code="label.deletedtasks"/></option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="formlable">
                            <label for="datepicker" class="control-label"><spring:message code="label.when"/></label>
                        </div>
                        <div>
                            <div class="col-sm-6">
                                <input class="form-control" type="text" id="datepicker" name="duedate" placeholder="<spring:message code="label.date"/>">
                            </div>
                            <div class="col-sm-6">
                                <input class="form-control" type="text" id="timepicker" name="duetime" placeholder="<spring:message code="label.time"/>">
                            </div>
                        </div>
                        
                    </div>
                    <div class="form-group col-sm-10">
                        <label for="tasktype">Типы</label>
                        <select class="form-control" name="tasktype" id="tasktype">
                            <option value=""><spring:message code="label.alltypes"/></option>
                             <c:forEach var="tasktype" items="${tasktypes}">
                                <option value="${tasktype.ordinal()+1}"><spring:message code="${tasktype.toString()}"/></option>
                             </c:forEach>
                         </select>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                        <label for="state"><spring:message code="label.allstatuses"/></label>
                            <select class="form-control" name="state" id="state">
                                <option></option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label for="users"><spring:message code="label.managers"/></label>
                            <select class="form-control" name="user" id="users">
                                <option value=""><spring:message code="label.anymanager"/></option>
                                <c:forEach var="user" items="${users}">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="checkbox authorcheckbox">
                        <label>
                            <input type="checkbox" value="entiredept">
                            <spring:message code="label.selectentiredepartment"/>
                        </label>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label for="authors"><spring:message code="label.authors"/></label>
                            <select class="form-control" name="authors" id="authors">
                                <option></option>
                            </select>
                        </div>
                    </div>
            
                    <input class="btn btn-primary" type="submit" value="<spring:message code="label.submit"/>">
                    <input class="btn resetbutton" type="reset" value="<spring:message code="label.reset"/>">
                </form>
            </div>
            <div class="col-sm-7">
                <ul class="nav nav-pills">
                    <li class="active"><a href="#todotasks" data-toggle="tab">To-do Line</a></li>
                    <li><a href="#today" data-toggle="tab"><spring:message code="label.day"/></a></li>
                    <li><a href="#weekstasks" data-toggle="tab"><spring:message code="label.week"/></a></li>
                    <li><a href="#monthtasks" data-toggle="tab"><spring:message code="label.month"/></a></li>
                    <li><a href="#tasklist" data-toggle="tab"><spring:message code="label.list"/></a></li>
                    <a class="btn btn-success addtask" href="new_task_prepare"><spring:message code="label.addtask"/></a>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane active container-fluid" id="todotasks">
                        <div class="row no-gutter">
                            <div class="col-sm-4 overdue">
                                <div class="rowname"><spring:message code="label.overduetasks"/></div>
                                <c:forEach var="task" items="${tasklist}">
                                    <c:if test="${datetime.time>task.dueTime.time}">
                                        <div class="element">
                                            <fmt:formatDate type="both" value="${task.dueTime}" pattern="dd.MM.yyyy HH:mm"/><br>
                                            <div class="comment">${task.comment}</div>
                                            ${task.user.name}<br>
                                            <spring:message code="label.type"/>: <spring:message code="${task.type.toString()}"/><br>
                                            ${task.subject.name}<br>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="col-sm-4 todotoday">
                                <div class="rowname"><spring:message code="label.tasksfortoday"/></div>
                                <fmt:formatDate type="date" value="${datetime}" var="date"/>
                                <c:forEach var="task" items="${tasklist}">
                                    <fmt:formatDate type="date" value="${task.dueTime}" var="dueDate"/>
                                    <c:if test="${date eq dueDate}">
                                        <div class="element">
                                            <fmt:formatDate type="both" value="${task.dueTime}" pattern="dd.MM.yyyy HH:mm"/><br>
                                            <div class="comment">${task.comment}</div>
                                            ${task.user.name}<br>
                                            <spring:message code="label.type"/>: <spring:message code="${task.type.toString()}"/><br>
                                            ${task.subject.name}<br>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="col-sm-4 todotomorow">
                                <div class="rowname"><spring:message code="label.tasksfortomorow"/></div>
                                <fmt:formatDate type="date" value="${tomorowdate.time}" var="tomorowdatestring"/>
                                <c:forEach var="task" items="${tasklist}">
                                    <fmt:formatDate type="date" value="${task.dueTime}" var="dueDate"/>
                                    <c:if test="${tomorowdatestring eq dueDate}">
                                        <div class="element">
                                            <fmt:formatDate type="both" value="${task.dueTime}" pattern="dd.MM.yyyy HH:mm"/><br>
                                            <div class="comment">${task.comment}</div>
                                            ${task.user.name}<br>
                                            <spring:message code="label.type"/>: <spring:message code="${task.type.toString()}"/><br>
                                            ${task.subject.name}<br>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane container-fluid" id="today">
                        <cal:day tasks="${tasklist}"></cal:day>
                    </div>
                    <div class="tab-pane container-fluid" id="weekstasks">
                        <cal:week tasks="${tasklist}"></cal:week>
                    </div>
                    <div class="tab-pane container-fluid" id="monthtasks">
                        <cal:month tasks="${tasklist}"></cal:month>
                    </div>
                    <div class="tab-pane container-fluid" id="tasklist">
                        <table class="table table-striped table-bordered">
                            <tr>
                                <th class="date"><spring:message code="label.datetodo"/>/<spring:message code="label.time"/>/<br><spring:message code="label.responsible"/></th>
                                <th class="contactdeal"><spring:message code="label.contactordeal"/></th>
                                <th class="taskcomment"><spring:message code="label.type"/>/<spring:message code="label.tasknote"/></th>
                            </tr>
                            <c:forEach var="task" items="${tasklist}">
                                <c:choose>
                                    <c:when test="${datetime.time>task.dueTime.time}">
                                        <tr class="danger">
                                            <td><fmt:formatDate type="both" value="${task.dueTime}" pattern="dd.MM.yyyy HH:mm"/> ${task.user.name}</td>
                                            <td>${task.subject.name}</td>
                                            <td>${task.comment}</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="info">
                                            <td><fmt:formatDate type="both" value="${task.dueTime}" pattern="dd.MM.yyyy HH:mm"/> ${task.user.name}</td>
                                            <td>${task.subject.name}</td>
                                            <td>${task.comment}</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
