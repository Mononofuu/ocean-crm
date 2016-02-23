<!DOCTYPE html>
<!--
@author Anton Sakhno <sakhno83@gmail.com>
-->
<!--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
    <div class="row">
        <form class="form-horizontal" action="/new_contact_add" method="get" enctype="multipart/form-data"
              id="newcontactform">
        <div class="col-xs-4 autowindow">

                <h4><spring:message code="label.addcontact"/> </h4>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input class="form-control" type="text" name="name" id="name" placeholder='<spring:message code="label.namesurname"/>'>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input class="form-control" type="text" name="tags" id="tags" placeholder='<spring:message code="label.tags"/>'>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <select class="form-control" name="responsible" id="responsible">
                            <option value="" disabled selected><spring:message code="label.responsible"/> </option>
                            <c:forEach var="user" items="${userslist}">
                                <option value="${user.id}">${user.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input class="form-control" type="text" name="position" id="position" placeholder='<spring:message code="label.position"/>'>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-7">
                        <input class="form-control" type="text" name="phonenumber" id="phonenumber"
                               placeholder='<spring:message code="label.phonenumber"/>'>
                    </div>
                    <div class="col-sm-5">
                        <select class="form-control" name="phonetype" id="phonetype">
                            <option value=""><spring:message code="label.type"/></option>
                            <c:forEach var="phonetype" items="${phonetypelist}">
                                <option value="${phonetype}"><spring:message code="${phonetype.toString()}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input class="form-control" type="text" name="email" id="email" placeholder='<spring:message code="label.email"/>'>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input class="form-control" type="text" name="skype" id="skype" placeholder='<spring:message code="label.skype"/>'>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <textarea class="form-control" name="notes" id="notes" placeholder='<spring:message code="label.contactnote"/>'
                                  rows="4"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input type="file" min="1" max="10" name="file[]" id="file" multiple="true">
                    </div>
                </div>
                <input class="btn btn-primary" type="submit" value='<spring:message code="label.add"/>' id="addcontact">
                <input class="btn resetbutton" type="reset" value='<spring:message code="label.reset"/>' id="resetcontact">

        </div>
        <div class="col-xs-4">
            <div class="col-sm-12 autowindow">
                    <h4><spring:message code="label.quickdealadd"/></h4>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input class="form-control" type="text" name="newdealname" id="newdealname" placeholder='<spring:message code="label.dealname"/>'>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <select class="form-control" name="dealtype" id="dealtype">
                                <option value="" disabled selected><spring:message code="label.phase"/></option>
                                <c:forEach var="dealstatus" items="${dealstatuses}">
                                    <option value="${dealstatus.id}"><spring:message code="${dealstatus.toString()}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-9">
                            <input class="form-control" type="text" name="budget" id="budget" placeholder='<spring:message code="label.budget"/>'>
                        </div>
                        <div class="col-sm-2">
                            <label for="budget"><spring:message code="label.uan"/></label>
                        </div>
                    </div>
                    <input class="btn btn-primary" type="submit" value='<spring:message code="label.add"/>' id="adddeal">
            </div>
            <div class="col-sm-12 autowindow">
                    <h4><spring:message code="label.addtask"/></h4>
                    <div class="form-group small-gutter">
                        <div class="col-sm-4">
                            <select class="form-control" name="period" id="period">
                                <option value="today"><spring:message code="label.today"/></option>
                                <option value="allday"><spring:message code="label.allday"/></option>
                                <option value="tomorow"><spring:message code="label.tomorow"/></option>
                                <option value="nextweek"><spring:message code="label.nextweek"/></option>
                                <option value="nextmonth"><spring:message code="label.nextmonth"/></option>
                                <option value="nextyear"><spring:message code="label.nextyear"/></option>
                            </select>
                        </div>
                        <div class="col-sm-1">
                            <label for="period"><spring:message code="label.or"/></label>
                        </div>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" id="datepicker" name="duedate" placeholder='<spring:message code="label.date"/>'>
                        </div>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" id="timepicker" name="duetime" placeholder='<spring:message code="label.time"/>'>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <select class="form-control" name="taskresponsible" id="taskresponsible">
                                <option value="" disabled selected><spring:message code="label.responsible"/></option>
                                <c:forEach var="user" items="${userslist}">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <select class="form-control" name="tasktype" id="tasktype">
                                <option value="" disabled selected><spring:message code="label.tasktype"/></option>
                                <c:forEach var="tasktype" items="${tasktypes}">
                                    <option value="${tasktype.name()}"><spring:message code="${tasktype.toString()}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea class="form-control" name="tasktext" id="tasktext" placeholder='<spring:message code="label.tasknote"/>'
                                      rows="2"></textarea>
                        </div>
                    </div>
                    <input type="hidden" name="subject" value="newcontactform">
                    <input class="btn btn-primary" type="submit" value='<spring:message code="label.add"/>' id="addtask">
            </div>
        </div>
        </form>
        <div class="col-xs-2 autowindow">
            <div class="row">
                <div class="form-group">
                    <label for="companyselect"><spring:message code="label.choosecompany"/></label>
                    <select class="form-control" id="companyselect" name="companyid" form="newcontactform">
                        <option value="" disabled selected></option>
                        <c:forEach var="company" items="${companylist}">
                            <option value="${company.id}">${company.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row">
                <form method="post" id="newcompanyform" action="/new_company">
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
</div>
</body>
</html>