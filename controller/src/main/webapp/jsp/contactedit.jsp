<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 26.12.2015
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    <%@include file='../css/bootstrap.css' %>
</style>

<html>
<head>
    <title>Редактирование контакта</title>
</head>
<body>

<jsp:useBean id="contact" type="com.becomejavasenior.Contact" scope="request">
    <jsp:setProperty name="contact" property="*" />
</jsp:useBean>

<div class="col-xs-2">
    <jsp:include page="menu.jsp"/>
</div>


<div class="col-xs-10">
<form class="form-horizontal" action="contactedit?action=update"  method="post">
    <fieldset>

        <legend>Редактирование контакта</legend>

        <input type="hidden" name="id" value="${contact.id}">
        <input type="hidden" name="backurl" value="${backurl}">

        <div class="form-group">
            <label class="col-md-2 control-label" for="textinput">Имя контакта</label>
            <div class="col-md-4">
                <input id="textinput" name="name" type="text" placeholder="" class="form-control input-md" required="" value="${contact.name}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="contactcompany">Компания</label>
            <div class="col-md-4">
                <select id="contactcompany" name="companyid" class="form-control">
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
            <label class="col-xs-2 control-label" for="contactpost">Должность</label>
            <div class="col-xs-4">
                <input id="contactpost" name="contactpost" type="text" placeholder="" class="form-control input-md" value="${contact.post}">

            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-2 control-label" for="contactphone">Номер телефона</label>
            <div class="col-xs-4">
                <input id="contactphone" name="contactphone" type="text" placeholder="" class="form-control input-md" required="" value="${contact.phone}">

            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-2 control-label" for="contactphonetype"></label>
            <div class="col-xs-4">
                <select id="contactphonetype" name="contactphonetype" class="form-control">
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
            <label class="col-xs-2 control-label" for="contactuser">Ответственный</label>
            <div class="col-xs-4">
                <select id="contactuser" name="contactuser" class="form-control">
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
            <label class="col-xs-2 control-label" for="contactemail">E-mail</label>
            <div class="col-xs-4">
                <input id="contactemail" name="contactemail" type="text" placeholder="" class="form-control input-md" value="${contact.email}"}>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-2 control-label" for="contactskype">Skype</label>
            <div class="col-xs-4">
                <input id="contactskype" name="contactskype" type="text" placeholder="" class="form-control input-md" required="" value="${contact.skype}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="btn_save_contact"></label>
            <div class="col-md-2">
                <button id="btn_save_contact" name="btn_save_contact" class="btn btn-default">Записать</button>
                <button id="btn_contact_cancel" name="btn_contact_cancel" class="btn btn-default" onclick="window.history.back()">Отмена</button>
            </div>
            <div class="col-md-2">
            </div>
        </div>

    </fieldset>
</form>
</div>
</body>
</html>
