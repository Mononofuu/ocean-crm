<!DOCTYPE html>
<!--
@author Alekseichenko Sergey
-->
<!--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
-->
<html>
<head>
    <title><spring:message code="label.adddeal"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="menu.jsp"/>
    <script src="../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../resources/js/tasklist.js" type="text/javascript"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/smoothness/jquery-ui.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.css"/>
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.1/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.1/js/select2.min.js"></script>
</head>
<body>
<div class="container col-lg-10">
    <form:form class="form-horizontal" action="/deals/add" method="post" modelAttribute="dealForm" id="newdealForm">
        <div class="col-lg-3">
            <div class="col-sm-12 panel panel-default">
                <h4><spring:message code="label.createdeal"/></h4>
                <spring:bind path="deal.name">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.dealname" var="dealname"/>
                            <form:input path="deal.name" class="form-control" type="text" name="newdealname"
                                        id="newdealname" placeholder="${dealname}"/>
                            <form:errors path="deal.name" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="tags">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.tags" var="tags"/>
                            <form:input path="tags" class="form-control" type="text" name="tags" id="tags"
                                        placeholder="${tags}"/>
                            <form:errors path="tags" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="userContactId">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <form:select path="userContactId" class="form-control" name="responsible" id="responsible">
                                <option value="" disabled selected><spring:message code="label.responsible"/></option>
                                <c:forEach var="user" items="${userslist}">
                                    <form:option value="${user.id}">${user.name}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="deal.budget">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-9">
                            <spring:message code="label.budget" var="budget"/>
                            <form:input path="deal.budget" class="form-control" type="number" value="${null}"
                                        name="budget" id="budget" placeholder="${budget}"/>
                            <form:errors path="deal.budget" class="control-label"/>
                        </div>
                        <div class="col-sm-2">
                            <label for="budget"><spring:message code="label.uan"/></label>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="deal.status">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <form:select path="deal.status" class="form-control" name="dealtype" id="dealtype">
                                <form:option value="" disabled="true" selected="true"><spring:message
                                        code="label.phase"/></form:option>
                                <c:forEach var="dealstatus" items="${dealstatuses}">
                                    <form:option value="${dealstatus.id}"><spring:message
                                            code="${dealstatus.toString()}"/></form:option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="deal.status" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="dealComment">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <spring:message code="label.comment" var="dealnote"/>
                            <form:textarea path="dealComment" class="form-control" name="notes" id="notes"
                                           placeholder="${dealnote}" rows="3"></form:textarea>
                        </div>
                    </div>
                </spring:bind>
                <div class="form-group col-xs-12">
                    <input type="file" style="width: inherit; overflow: hidden;" min="1" max="10" name="file[]"
                           id="file" multiple="true">
                </div>
                <input class="btn btn-primary" type="submit" value='<spring:message code="label.add"/>'>
            </div>
        </div>
        <div class="col-lg-3">
            <div class="col-sm-12 panel panel-default">
                <h4><spring:message code="label.addcontact"/></h4>
                <h5><spring:message code="label.choosecontact"/></h5>
                <spring:bind path="dealContactId">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <form:select path="dealContactId" class="form-control" name="dealcontact" id="dealcontact">
                                <option value="" disabled selected><spring:message code="label.contacts"/></option>
                                <c:forEach var="contact" items="${contactlist}">
                                    <option value="${contact.id}">${contact.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </spring:bind>
                <h5><spring:message code="label.ornew"/></h5>
                <spring:bind path="contact.name">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.namesurname" var="contactname"/>
                            <form:input path="contact.name" class="form-control" type="text" name="newcontactname"
                                        id="newcontactname" placeholder="${contactname}"/>
                            <form:errors path="contact.name" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="contactCompanyId">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <form:select path="contactCompanyId" class="form-control" name="contactcompany"
                                         id="contactcompany">
                                <option value="" disabled selected><spring:message code="label.company"/></option>
                                <c:forEach var="company" items="${companylist}">
                                    <option value="${company.id}">${company.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="contact.post">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.position" var="position"/>
                            <form:input path="contact.post" class="form-control" type="text" name="position"
                                        id="position" placeholder="${position}"/>
                            <form:errors path="contact.post" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="contact.phone">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-7">
                            <spring:message code="label.phonenumber" var="phonenumber"/>
                            <form:input path="contact.phone" class="form-control" type="text" name="phonenumber"
                                        id="phonenumber" placeholder="${phonenumber}"/>
                            <form:errors path="contact.phone" class="control-label"/>
                        </div>
                        <div class="col-sm-5">
                            <form:select path="contact.phoneType" class="form-control" name="phonetype" id="phonetype">
                                <option value=""><spring:message code="label.type"/></option>
                                <c:forEach var="phonetype" items="${phonetypelist}">
                                    <form:option value="${phonetype}"><spring:message
                                            code="${phonetype.toString()}"/></form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="contact.email">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.email" var="email"/>
                            <form:input path="contact.email" class="form-control" type="text" name="email" id="email"
                                        placeholder="${email}"/>
                            <form:errors path="contact.email" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="contact.skype">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.skype" var="skype"/>
                            <form:input path="contact.skype" class="form-control" type="text" name="skype" id="skype"
                                        placeholder="${skype}"/>
                            <form:errors path="contact.skype" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <input type="submit" class="btn btn-primary" name="savecontact"
                       value='<spring:message code="label.addcontact"/>'>
            </div>
        </div>
        <div class="col-lg-3">
            <div class="col-sm-12 panel panel-default">
                <h4><spring:message code="label.addcompany"/></h4>
                <h5><spring:message code="label.choosecompany"/></h5>
                <spring:bind path="dealCompanyId">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <form:select path="dealCompanyId" class="form-control" name="dealcompany" id="dealcompany">
                                <option value="" disabled selected><spring:message code="label.company"/></option>
                                <c:forEach var="company" items="${companylist}">
                                    <option value="${company.id}">${company.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </spring:bind>
                <h5><spring:message code="label.ornew"/></h5>
                <spring:bind path="company.name">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.companyname" var="companyname"/>
                            <form:input path="company.name" class="form-control" type="text" name="newcompanyname"
                                        id="newdealname" placeholder="${companyname}"/>
                            <form:errors path="company.name" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="company.phoneNumber">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.phonenumber" var="companyphone"/>
                            <form:input path="company.phoneNumber" class="form-control" type="text"
                                        name="newcompanyphone"
                                        id="newcompanyphone" placeholder="${companyphone}"/>
                            <form:errors path="company.phoneNumber" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="company.email">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.email" var="companyemail"/>
                            <form:input path="company.email" class="form-control" type="text" name="newcompanyemail"
                                        id="newcompanyemail" placeholder="${companyemail}"/>
                            <form:errors path="company.email" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="company.web">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.web" var="companyweb"/>
                            <form:input path="company.web" class="form-control" type="text" name="newcompanyweb"
                                        id="newcompanyweb" placeholder="${companyweb}"/>
                            <form:errors path="company.web" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="company.address">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <spring:message code="label.address" var="companyaddress"/>
                            <form:input path="company.address" class="form-control" type="text" name="newcompanyaddress"
                                        id="newcompanyaddress" placeholder="${companyaddress}"/>
                            <form:errors path="company.address" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <input type="submit" class="btn btn-primary" name="savecompany"
                       value='<spring:message code="label.addcompany"/>'>
            </div>
        </div>
        <div class="col-lg-3">
            <div class="col-sm-12 panel panel-default">
                <h4><spring:message code="label.addtask"/></h4>
                <spring:bind path="taskPeriod">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <form:select path="taskPeriod" class="form-control" name="taskPeriod" id="taskPeriod">
                                <option value="" disabled selected><spring:message code="label.period"/></option>
                                <form:option value="today"><spring:message code="label.today"/></form:option>
                                <form:option value="allday"><spring:message code="label.allday"/></form:option>
                                <form:option value="tomorow"><spring:message code="label.tomorow"/></form:option>
                                <form:option value="nextweek"><spring:message code="label.nextweek"/></form:option>
                                <form:option value="nextmonth"><spring:message code="label.nextmonth"/></form:option>
                                <form:option value="nextyear"><spring:message code="label.nextyear"/></form:option>
                            </form:select>
                        </div>
                    </div>
                </spring:bind>
                <h5><spring:message code="label.orchoosedate"/></h5>
                <spring:bind path="dueDate">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-7">
                            <spring:message code="label.date" var="dueDate"/>
                            <form:input path="dueDate" class="form-control" type="text" id="datepicker" name="duedate"
                                        placeholder="${dueDate}"/>
                        </div>
                        <div class="col-sm-5">
                            <spring:message code="label.time" var="dueTime"/>
                            <form:input path="dueTime" class="form-control" type="text" id="timepicker" name="duetime"
                                        placeholder="${dueTime}"/>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="taskUserId">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <form:select path="taskUserId" class="form-control" name="responsible" id="responsible">
                                <option value="" disabled selected><spring:message code="label.responsible"/></option>
                                <c:forEach var="user" items="${userslist}">
                                    <form:option value="${user.id}">${user.name}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="task.type">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="col-sm-12">
                            <form:select path="task.type" class="form-control" name="type" id="type">
                                <option value="" disabled selected><spring:message code="label.tasktype"/></option>
                                <c:forEach var="type" items="${tasktypes}">
                                    <form:option value="${type}"><spring:message
                                            code="${type.toString()}"/></form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="task.comment">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <spring:message code="label.tasknote" var="tasknote"/>
                            <form:textarea path="task.comment" class="form-control" name="notes" id="notes"
                                           placeholder="${tasknote}" rows="3"></form:textarea>
                        </div>
                    </div>
                </spring:bind>
            </div>
        </div>

    </form:form>
</div>
</body>
</html>