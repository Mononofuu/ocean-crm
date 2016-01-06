<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 22.12.2015
  Time: 22:11
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.becomejavasenior.PhoneType" %>
<style>
    <%@include file='../css/bootstrap.css' %>
    <%@include file='../css/bootstrap-theme.css' %>
</style>
<html>
<head>
    <title>Редактирование сделки</title>

    <script src="../resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="../resources/js/bootstrap.min.js" type="text/javascript"></script>

</head>
<body>
<jsp:useBean id="deal" type="com.becomejavasenior.Deal" scope="request"/>
<div class="col-xs-2">
    <jsp:include page="menu.jsp"/>
</div>
<div class="col-xs-10">
    <h3>Редактирование сделки</h3>
    <div class="tabbable tabs-below col-md-7">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#common" data-toggle="tab">Общая информация</a></li>
            <li><a href="#contacts" data-toggle="tab">Контакты</a></li>
            <li><a href="#tasks" data-toggle="tab">Задачи и примечания</a></li>
        </ul>

        <div class="tab-content">

            <div class="tab-pane container-fluid active" id="common">
                <form  class="form-horizontal" method="post" action="dealedit?action=update">
                    <fieldset>
                        <input type="hidden" name="id" value="${deal.id}">

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="name">Наименование</label>
                            <div class="col-md-4">
                                <input id="name" name="name" type="text" placeholder="" class="form-control input-md" required="" value="${deal.name}" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="company">Компания</label>
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
                            <label class="col-md-2 control-label" for="maincontact">Основной контакт</label>
                            <div class="col-md-4">
                                <select id="maincontact" name="maincontact" class="form-control">
                                    <c:forEach items="${contacts}" var="contact">
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
                            <label class="col-md-2 control-label" for="tags">Теги</label>
                            <div class="col-md-4">
                                <input id="tags" name="tags" type="text" placeholder="" class="form-control input-md" value="${tags}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label" for="user">Ответственный</label>
                            <div class="col-md-4">
                                <select id="user" name="user" class="form-control">
                                    <c:forEach items="${users}" var="user">
                                        <c:choose>
                                            <c:when test="${user.id == deal.user.id}">
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
                            <label class="col-md-2 control-label" for="budget">Бюджет</label>
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
                            <label class="col-md-2 control-label" for="dealstatus">Этап</label>
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
                                <button type="submit" class="btn btn-default navbar-btn" value="deal" name="submit" id="btndealsave">Записать</button>
                            </div>
                        </div>

                    </fieldset>
                </form>
            </div>

            <div class="tab-pane container-fluid" id="contacts">
            <div class="tabbable tabs-left">
                <ul class="nav nav-tabs" for="dealstatus">
                    <c:forEach items="${contacts}" var="contact" varStatus="status">
                        <li><a href="#contact${contact.id}" data-toggle="tab">${contact.name}</a></li>
                    </c:forEach>
                </ul>

                <div class="tab-content col-xs-3">
                    <div class="form-group">
                        <div class="form-group">
                            <button id="button_addcontact" name="button_addcontact" class="btn btn-default navbar-btn">Добавить контакт</button>
                        </div>
                    </div>
                    <div class="checkbox">
                        <label><input type="checkbox" id="contact_all_fields"> Вывести все поля</label>
                    </div>
                </div>

                <div class="tab-content col-xs-9">
                    <c:forEach items="${contacts}" var="contact" varStatus="status">

                        <c:choose>
                            <c:when test="${status.getIndex() == 0}">

                            </c:when>
                            <c:otherwise>

                            </c:otherwise>
                        </c:choose>


                        <div class="tab-pane container-fluid" id="contact${contact.id}">
                            <form  class="form-horizontal" method="post" action="dealedit?action=contactupdate&contactid=${contact.id}&dealid=${deal.id}">
                                <fieldset>

                                    <div class="form-group">
                                        <label class="col-xs-4 control-label" for="contactname${contact.id}">Имя</label>
                                        <div class="col-xs-8">
                                            <input id="contactname${contact.id}" name="contactname${contact.id}" type="text" placeholder="" class="form-control input-md" value="${contact.name}">

                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-4 control-label" for="contactcompany${contact.id}">Компания</label>
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
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-4 control-label" for="contactpost${contact.id}">Должность</label>
                                        <div class="col-xs-8">
                                            <input id="contactpost${contact.id}" name="contactpost${contact.id}" type="text" placeholder="" class="form-control input-md" value="${contact.post}">

                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-4 control-label" for="contactphone${contact.id}">Номер телефона</label>
                                        <div class="col-xs-8">
                                            <input id="contactphone${contact.id}" name="contactphone${contact.id}" type="text" placeholder="" class="form-control input-md" required="" value="${contact.phone}">

                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-4 control-label" for="contactphonetype${contact.id}"></label>
                                        <div class="col-xs-8">
                                            <select id="contactphonetype${contact.id}" name="contactphonetype${contact.id}" class="form-control">
                                                <c:forEach items="${phonetypes}" var="phonetype">
                                                    <c:choose>
                                                        <c:when test="${phonetype == contact.phoneType}">
                                                            <option selected value=${phonetype}>${phonetype}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value=${phonetype}>${phonetype}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-4 control-label" for="contactuser${contact.id}">Ответственный</label>
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
                                        <label class="col-xs-4 control-label" for="contactemail${contact.id}">E-mail</label>
                                        <div class="col-xs-8">
                                            <input id="contactemail${contact.id}" name="contactemail${contact.id}" type="text" placeholder="" class="form-control input-md" value="${contact.email}"}>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-4 control-label" for="contactskype${contact.id}">Skype</label>
                                        <div class="col-xs-8">
                                            <input id="contactskype${contact.id}" name="contactskype${contact.id}" type="text" placeholder="" class="form-control input-md" required="" value="${contact.skype}">
                                        </div>
                                    </div>

                                    <label class="col-md-2 control-label" for="button_savecontact${contact.id}"></label>
                                    <div class="col-md-2">
                                        <button id="button_savecontact${contact.id}" name="button_savecontact${contact.id}" class="btn btn-default navbar-btn">Записать</button>
                                    </div>

                                </fieldset>

                            </form>
                        </div>
                    </c:forEach>
                </div>

            </div>
            </div>

            <div class="tab-pane container-fluid" id="tasks">
                Задачи
            </div>

        </div>
    </div>
</div>

</body>
</html>
