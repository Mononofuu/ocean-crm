<!DOCTYPE html>
<!--
@author Anton Sakhno <sakhno83@gmail.com>
-->
<!--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
-->
<html>
<head>
    <title>Создание контакта</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="menu.jsp"/>
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
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.1/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.1/js/select2.min.js"></script>
</head>
<body>
<div class="container">
    <form:form class="form-horizontal" action="/contacts/add" method="post" modelAttribute="contactForm" id="newcontactform">
        <div class="col-xs-4 autowindow">
            <h4><spring:message code="label.addcontact"/> </h4>
            <c:if test="${not empty resultmessage}">
                <div class="alert alert-success" role="alert">
                    <span><spring:message code="${resultmessage}"/></span>
                </div>
            </c:if>
            <spring:bind path="contact.name">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-12">
                        <spring:message code="label.namesurname" var="nameSurname"/>
                        <form:input path="contact.name" class="form-control" type="text" id="name" placeholder="${nameSurname}"/>
                        <form:errors path="contact.name" class="control-label"/>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="tags">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-12">
                        <spring:message code="label.tags" var="tags"/>
                        <form:input path="tags" class="form-control" type="text" name="tags" id="tags" placeholder="${tags}"/>
                        <form:errors path="tags" class="control-label"/>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="userContactId">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-12">
                        <form:select path="userContactId" class="form-control" name="responsible" id="responsible">
                            <option value="" disabled selected><spring:message code="label.responsible"/> </option>
                            <c:forEach var="user" items="${userslist}">
                                <form:option value="${user.id}">${user.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="contact.post">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-12">
                        <spring:message code="label.position" var="position"/>
                        <form:input path="contact.post" class="form-control" type="text" name="position" id="position" placeholder="${position}"/>
                        <form:errors path="contact.post" class="control-label"/>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="contact.phone">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-7">
                        <spring:message code="label.phonenumber" var="phonenumber"/>
                        <form:input path="contact.phone" class="form-control" type="text" name="phonenumber" id="phonenumber" placeholder="${phonenumber}"/>
                        <form:errors path="contact.phone" class="control-label"/>
                    </div>
                    <div class="col-sm-5">
                        <form:select path="contact.phoneType" class="form-control" name="phonetype" id="phonetype">
                            <option value=""><spring:message code="label.type"/></option>
                            <c:forEach var="phonetype" items="${phonetypelist}">
                                <form:option value="${phonetype}"><spring:message code="${phonetype.toString()}"/></form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="contact.email">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-12">
                        <spring:message code="label.email" var="email"/>
                        <form:input path="contact.email" class="form-control" type="text" name="email" id="email" placeholder="${email}"/>
                        <form:errors path="contact.email" class="control-label"/>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="contact.skype">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-12">
                        <spring:message code="label.skype" var="skype"/>
                        <form:input path="contact.skype" class="form-control" type="text" name="skype" id="skype" placeholder="${skype}"/>
                        <form:errors path="contact.skype" class="control-label"/>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="contactComment">
                <div class="form-group">
                    <div class="col-sm-12">
                        <spring:message code="label.contactnote" var="contactnote"/>
                        <form:textarea path="contactComment" class="form-control" name="notes" id="notes" placeholder="${contactnote}" rows="3"></form:textarea>
                    </div>
                </div>
            </spring:bind>
            <div class="form-group">
                <div class="col-sm-12">
                    <input type="file" min="1" max="10" name="file[]" id="file" multiple="true">
                </div>
            </div>
            <input class="btn btn-primary" type="submit" value='<spring:message code="label.add"/>'>
            <input class="btn resetbutton" type="reset" value='<spring:message code="label.reset"/>'>

        </div>
        <div class="col-xs-4">
            <div class="col-sm-12 autowindow">
                <h4><spring:message code="label.quickdealadd"/></h4>
                <spring:bind path="deal.name">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.dealname" var="dealname"/>
                            <form:input path="deal.name" class="form-control" type="text" name="newdealname" id="newdealname" placeholder="${dealname}"/>
                            <form:errors path="deal.name" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="deal.status">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <form:select path="deal.status" class="form-control" name="dealtype" id="dealtype">
                                <form:option value="" disabled="true" selected="true"><spring:message code="label.phase"/></form:option>
                                <c:forEach var="dealstatus" items="${dealstatuses}">
                                    <form:option value="${dealstatus.id}" ><spring:message code="${dealstatus.toString()}"/></form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="deal.budget">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-9">
                            <spring:message code="label.budget" var="budget"/>
                            <form:input path="deal.budget" class="form-control" type="number" value="${null}" name="budget" id="budget" placeholder="${budget}"/>
                            <form:errors path="deal.budget" class="control-label"/>
                        </div>
                        <div class="col-sm-2">
                            <label for="budget"><spring:message code="label.uan"/></label>
                        </div>
                    </div>
                </spring:bind>
                <input class="btn btn-primary" type="submit" value='<spring:message code="label.add"/>'>
            </div>
            <div class="col-sm-12 autowindow">
                <h4><spring:message code="label.addtask"/></h4>
                <div class="form-group small-gutter">
                    <div class="col-sm-4">
                        <form:select path="period" class="form-control" name="period" id="period">
                            <form:option value="today"><spring:message code="label.today"/></form:option>
                            <form:option value="allday"><spring:message code="label.allday"/></form:option>
                            <form:option value="tomorow"><spring:message code="label.tomorow"/></form:option>
                            <form:option value="nextweek"><spring:message code="label.nextweek"/></form:option>
                            <form:option value="nextmonth"><spring:message code="label.nextmonth"/></form:option>
                            <form:option value="nextyear"><spring:message code="label.nextyear"/></form:option>
                        </form:select>
                    </div>
                    <div class="col-sm-1">
                        <label for="period"><spring:message code="label.or"/></label>
                    </div>
                    <div class="col-sm-3">
                        <spring:message code="label.date" var="dueDate"/>
                        <form:input path="dueDate" class="form-control" type="text" id="datepicker" name="duedate" placeholder="${dueDate}"/>
                    </div>
                    <div class="col-sm-3">
                        <spring:message code="label.time" var="dueTime"/>
                        <form:input path="dueTime" class="form-control" type="text" id="timepicker" name="duetime" placeholder="${dueTime}"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <form:select path="userTaskId" class="form-control" name="taskresponsible" id="taskresponsible">
                            <option value="" disabled selected><spring:message code="label.responsible"/></option>
                            <c:forEach var="user" items="${userslist}">
                                <form:option value="${user.id}">${user.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <form:select path="task.type" class="form-control" name="tasktype" id="tasktype">
                            <option value="" disabled selected><spring:message code="label.tasktype"/></option>
                            <c:forEach var="tasktype" items="${tasktypes}">
                                <form:option value="${tasktype}"><spring:message code="${tasktype.toString()}"/></form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <spring:bind path="task.comment">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.tasknote" var="tasknote"/>
                            <form:textarea path="task.comment" class="form-control" name="tasktext" id="tasktext" placeholder="${tasknote}" rows="2"></form:textarea>
                            <form:errors path="task.comment" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <input type="hidden" name="company" id="company" >
                <input class="btn btn-primary" type="submit" value='<spring:message code="label.add"/>'>
            </div>
        </div>
    </form:form>
    <div class="col-xs-2 autowindow">
        <div class="row">
            <div class="form-group">
                <label for="companyselect"><spring:message code="label.choosecompany"/></label>
                <select class="form-control" id="companyselect" name="companyid" form="newcompanyform">
                    <option value="" disabled selected></option>
                    <c:forEach var="company" items="${companylist}">
                        <option value="${company.id}">${company.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row">
            <form method="post" id="newcompanyform" action="/companies/ajaxadd">
                <div class="form-group">
                    <label for="newcompanyname"><spring:message code="label.addcompany"/></label>
                    <input class="form-control" type="text" name="newcompanyname" id="newcompanyname"
                           placeholder='<spring:message code="label.companyname"/>'>
                </div>
                <div class="form-group">
                    <input class="form-control" type="text" name="newcompanyphone" id="newcompanyphone"
                           placeholder='<spring:message code="label.phonenumber"/>'>
                </div>
                <div class="form-group">
                    <input class="form-control" type="text" name="newcompanyemail" id="newcompanyemail"
                           placeholder='<spring:message code="label.email"/>'>
                </div>
                <div class="form-group">
                    <input class="form-control" type="text" name="newcompanywebaddress" id="newcompanywebaddress"
                           placeholder='<spring:message code="label.web"/>'>
                </div>
                <div class="form-group">
                    <input class="form-control" type="text" name="newcompanyaddress" id="newcompanyaddress"
                           placeholder='<spring:message code="label.address"/>'>
                </div>
                <input name="submit" class="btn btn-primary" type="submit" value='<spring:message code="label.addcompany"/>'>
            </form>
        </div>
    </div>
</div>
</body>
</html>