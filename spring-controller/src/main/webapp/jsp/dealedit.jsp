<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 22.12.2015
  Time: 22:11
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/bootstrap.css' %>
    <%@include file='../css/bootstrap-theme.css' %>
</style>
<html>
<head>
    <title><spring:message code="label.dealedit"/></title>

    <script src="../resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="../resources/js/bootstrap.min.js" type="text/javascript"></script>

</head>
<body>
<jsp:useBean id="deal" type="com.becomejavasenior.Deal" scope="request"/>
<div class="col-xs-2">
    <jsp:include page="menu.jsp"/>
</div>
<div class="col-xs-10">

    <h3><spring:message code="label.dealedit"/></h3>
    <div class="tabbable tabs-below col-md-10">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#common" data-toggle="tab"><spring:message code="label.commoninformation"/></a></li>
            <li><a href="#contacts" data-toggle="tab"><spring:message code="label.contacts"/></a></li>
            <li><a href="#tasks" data-toggle="tab"><spring:message code="label.tasksandcomments"/></a></li>
        </ul>

        <div class="tab-content">

            <div class="tab-pane container-fluid active" id="common">
                <form  class="form-horizontal" method="post" action="dealedit?action=update">
                    <fieldset>
                        <input type="hidden" name="id" value="${deal.id}">

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="name"><spring:message code="label.tasksandcomments"/></label>
                            <div class="col-md-4">
                                <input id="name" name="name" type="text" placeholder="" class="form-control input-md" required="" value="${deal.name}" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="company"><spring:message code="label.company"/></label>
                            <div class="col-md-4">
                                <select id="company" name="company" class="form-control">
                                    <c:forEach items="${companies}" var="company">
                                        <c:choose>
                                            <c:when test="${company.id == deal.dealCompany.id}">
                                                <option selected value=${company.id}>${company.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${company.id}>${company.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-1">
                                <button type="submit" class="btn btn-default navbar-btn" value="company" name="submit">
                                    <span class="glyphicon glyphicon-pencil"></span>
                                </button>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="maincontact"><spring:message code="label.maincontact"/></label>
                            <div class="col-md-4">
                                <select id="maincontact" name="maincontact" class="form-control">
                                    <option value="0">Не выбран</option>
                                    <c:forEach items="${dealcontacts}" var="contact">
                                        <c:choose>
                                            <c:when test="${contact.id == deal.mainContact.id}">
                                                <option selected value=${contact.id}>${contact.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${contact.id}>${contact.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-1">
                                <button type="submit" class="btn btn-default navbar-btn" value="contactmain" name="submit">
                                    <span class="glyphicon glyphicon-pencil"></span>
                                </button>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="tags"><spring:message code="label.tags"/></label>
                            <div class="col-md-4">
                                <input id="tags" name="tags" type="text" placeholder="" class="form-control input-md" value="${tags}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="user"><spring:message code="label.responsible"/></label>
                            <div class="col-md-4">
                                <select id="user" name="user" class="form-control">
                                    <c:forEach items="${users}" var="user">
                                        <c:choose>
                                            <c:when test="${user.id == deal.responsible.id}">
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
                            <label class="col-md-2 control-label" for="budget"><spring:message code="label.budget"/></label>
                            <div class="col-md-4">
                                <input id="budget" name="budget" type="text" placeholder="" class="form-control input-md" required="" value="${deal.budget}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="currency"></label>
                            <div class="col-md-4">
                                <select id="currency" name="currency" class="form-control">
                                    <c:forEach items="${currencies}" var="currency">
                                        <c:choose>
                                            <c:when test="${currency.id == deal.currency.id}">
                                                <option selected value=${currency.id}>${currency.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${currency.id}>${currency.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="dealstatus"><spring:message code="label.dealstatus"/></label>
                            <div class="col-md-4">
                                <select id="dealstatus" name="dealstatus" class="form-control">
                                    <c:forEach items="${deal_statuses}" var="status">
                                        <c:choose>
                                            <c:when test="${status.id == deal.status.id}">
                                                <option selected value=${status.id}>${status.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${status.id}>${status.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="btndealsave"></label>
                            <div class="col-md-4">
                                <button type="submit" class="btn btn-default navbar-btn" value="deal" name="submit" id="btndealsave"><spring:message code="label.save"/></button>
                            </div>
                        </div>

                    </fieldset>
                </form>
            </div>

            <div class="tab-pane container-fluid" id="contacts">

                <div class="panel-group" id="accordion">

                    <c:forEach items="${dealcontacts}" var="contact" varStatus="status">

                        <div class="panel-info">
                            <div class="panel-heading">
                            <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#accordion${contact.id}">
                            ${contact.name}
                            </a>
                            </h4>
                        </div>

                            <div id="accordion${contact.id}" class="panel-collapse collapse">
                                <div class="panel-body">

                                    <form  class="form-horizontal" method="post" action="dealedit?action=contactupdate&contactid=${contact.id}&dealid=${deal.id}">
                                        <fieldset>

                                            <div class="form-group">
                                                <label class="col-xs-2 control-label" for="contactname${contact.id}"><spring:message code="label.name"/></label>
                                                <div class="col-xs-8">
                                                    <input id="contactname${contact.id}" name="contactname${contact.id}" type="text" placeholder="" class="form-control input-md" value="${contact.name}">

                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-xs-2 control-label" for="contactcompany${contact.id}"><spring:message code="label.company"/></label>
                                                <div class="col-xs-8">
                                                    <select id="contactcompany${contact.id}" name="contactcompany${contact.id}" class="form-control">
                                                        <c:forEach items="${companies}" var="company">
                                                            <c:choose>
                                                                <c:when test="${company.id == contact.company.id}">
                                                                    <option selected value=${company.id}>${company.name}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value=${company.id}>${company.name}</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-md-1">
                                                    <button type="submit" class="btn btn-default navbar-btn" name="button_updatecontact${contact.id}" value="companyedit">
                                                        <span class="glyphicon glyphicon-pencil"></span>
                                                    </button>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-xs-2 control-label" for="contactpost${contact.id}"><spring:message code="label.position"/></label>
                                                <div class="col-xs-8">
                                                    <input id="contactpost${contact.id}" name="contactpost${contact.id}" type="text" placeholder="" class="form-control input-md" value="${contact.post}">

                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-xs-2 control-label" for="contactphone${contact.id}"><spring:message code="label.phonenumber"/></label>
                                                <div class="col-xs-8">
                                                    <input id="contactphone${contact.id}" name="contactphone${contact.id}" type="text" placeholder="" class="form-control input-md" required="" value="${contact.phone}">

                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-xs-2 control-label" for="contactphonetype${contact.id}"></label>
                                                <div class="col-xs-8">
                                                    <select id="contactphonetype${contact.id}" name="contactphonetype${contact.id}" class="form-control">
                                                        <c:forEach items="${phonetypes}" var="phonetype">
                                                            <c:choose>
                                                                <c:when test="${phonetype == contact.phoneType}">
                                                                    <option selected value=${phonetype}>${phonetype.toString()}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value=${phonetype}>${phonetype.toString()}</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-xs-2 control-label" for="contactuser${contact.id}"><spring:message code="label.responsible"/></label>
                                                <div class="col-xs-8">
                                                    <select id="contactuser${contact.id}" name="contactuser${contact.id}" class="form-control">
                                                        <c:forEach items="${users}" var="user">
                                                            <c:choose>
                                                                <c:when test="${user.id == contact.user.id}">
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
                                                <label class="col-xs-2 control-label" for="contactemail${contact.id}">E-mail</label>
                                                <div class="col-xs-8">
                                                    <input id="contactemail${contact.id}" name="contactemail${contact.id}" type="text" placeholder="" class="form-control input-md" value="${contact.email}"}>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-xs-2 control-label" for="contactskype${contact.id}">Skype</label>
                                                <div class="col-xs-8">
                                                    <input id="contactskype${contact.id}" name="contactskype${contact.id}" type="text" placeholder="" class="form-control input-md" value="${contact.skype}">
                                                </div>
                                            </div>

                                            <label class="col-md-2 control-label" for="button_savecontact${contact.id}"></label>
                                            <div class="col-md-2">
                                                <button id="button_savecontact${contact.id}" name="button_updatecontact${contact.id}" value="save" class="btn btn-default navbar-btn"><spring:message code="label.save"/></button>
                                            </div>
                                            <div class="col-md-2">
                                                <button id="button_deletecontact${contact.id}" name="button_updatecontact${contact.id}" value="delete" class="btn btn-default navbar-btn"><spring:message code="label.deletefromdeal"/></button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#accordionnew">
                                    <spring:message code="label.add"/> ...
                                </a>
                            </h4>
                        </div>
                        <div id="accordionnew" class="panel-collapse collapse">
                            <div class="panel-body">
                                <form  class="form-horizontal" method="post" action="dealedit?action=update">
                                    <fieldset>
                                        <div class="tab-pane container-fluid" id="contactnew">
                                            <form  class="form-horizontal" method="post" action="dealedit?action=update">
                                                <input type="hidden" name="id" value="${deal.id}">
                                                <fieldset>
                                                    <div class="tab-content col-xs-8">

                                                        <div class="form-group">
                                                            <label class="col-xs-2 control-label" for="newcontact"><spring:message code="label.name"/></label>
                                                            <div class="col-xs-8">
                                                                <select id="newcontact" name="newcontact" class="form-control">
                                                                    <c:forEach items="${contacts}" var="contact">
                                                                        <option value=${contact.id}>${contact.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <label class="col-md-2 control-label" for="button_addcontact"></label>
                                                        <div class="col-md-2">
                                                            <button type="submit" id="button_addcontact" value="dealcontactadd" name="submit" class="btn btn-default navbar-btn"><spring:message code="label.contactadd"/></button>
                                                        </div>
                                                    </div>
                                                </fieldset>
                                            </form>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="tab-pane container-fluid" id="tasks">

                <div class="table-responsive col-xs-12">
                    <h3><spring:message code="label.comments"/></h3>
                    <table class="table table-striped table-bordered table-hover table-condensed">
                        <thead>
                        <tr>
                            <th><spring:message code="label.creationdate"/></th>
                            <th><spring:message code="label.username"/></th>
                            <th><spring:message code="label.comment"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${comments}" var="comment">
                            <tr>
                                <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${comment.dateCreated}"/></td>
                                <td>${comment.user.name}</td>
                                <td>${comment.text}</td>
                                <td>
                                    <a href="/commentedit?action=edit&id=${comment.id}&backurl=/dealedit?action=edit&subjectid=${deal.id}"><spring:message code="label.edit"/></a></td>
                                </td>
                                <td>
                                    <a href="/commentedit?action=delete&id=${comment.id}&backurl=/dealedit?action=edit&subjectid=${deal.id}"><spring:message code="label.delete"/></a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <a href="/commentedit?action=edit&id=0&backurl=/dealedit?action=edit&subjectid=${deal.id}"><spring:message code="label.addcomment"/></a></td>

                    <h3><spring:message code="label.tasks"/></h3>
                    <table class="table table-striped table-bordered table-hover table-condensed">
                        <thead>
                        <tr>
                            <th><spring:message code="label.creationdate"/></th>
                            <th><spring:message code="label.dateend"/></th>
                            <th><spring:message code="label.username"/></th>
                            <th><spring:message code="label.tasktype"/></th>
                            <th><spring:message code="label.tasknote"/></th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${tasks}" var="task">
                            <tr>
                                <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${task.dateCreated}"/></td>
                                <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${task.dueTime}"/></td>
                                <td>${task.user.name}</td>
                                <td><spring:message code="${task.type.toString()}"/></td>
                                <td>${task.comment}</td>
                                <c:choose>
                                    <c:when test="${(task.isClosed == 1) || (task.isDeleted == 1)}">
                                        <td></td>
                                        <td></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><a href="/taskedit?action=edit&id=${task.id}&backurl=/dealedit?action=edit&subjectid=${deal.id}"><spring:message code="label.edit"/></a></td>
                                        <td><a href="/taskedit?action=delete&id=${task.id}&backurl=/dealedit?action=edit&subjectid=${deal.id}"><spring:message code="label.delete"/></a></td>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${task.isClosed == 1}">
                                        <td><spring:message code="label.taskclosed"/></td>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${task.isDeleted == 1}">
                                                <td><spring:message code="label.taskdeleted"/></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td></td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <a href="/taskedit?action=edit&id=0&backurl=/dealedit?action=edit&subjectid=${deal.id}"><spring:message code="label.taskadd"/></a></td>
                </div>

            </div>

        </div>
    </div>
</div>

</body>
</html>
