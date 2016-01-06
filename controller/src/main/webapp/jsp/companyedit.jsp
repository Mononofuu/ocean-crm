<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 26.12.2015
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/bootstrap.css' %>
</style>

<html>
<head>
    <title>Редактирование компании</title>
</head>
<body>

<jsp:useBean id="company" type="com.becomejavasenior.Company" scope="request">
<jsp:setProperty name="company" property="*" />
</jsp:useBean>

<div class="col-xs-2">
    <jsp:include page="menu.jsp"/>
</div>
<div class="col-xs-10">

<form class="form-horizontal" action="companyedit?action=update"  method="post">
    <fieldset>


        <legend>Редактирование компании</legend>

        <input type="hidden" name="id" value="${company.id}">
        <input type="hidden" name="backurl" value="${backurl}">

        <div class="form-group">
            <label class="col-md-2 control-label" for="textinput">Название компании</label>
            <div class="col-md-4">
                <input id="textinput" name="name" type="text" placeholder="" class="form-control input-md" required="" value="${company.name}">

            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="phoneNumber">Телефон</label>
            <div class="col-md-4">
                <input id="phoneNumber" name="phoneNumber" type="text" placeholder="" class="form-control input-md" value="${company.phoneNumber}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="email">Email</label>
            <div class="col-md-4">
                <input id="email" name="email" type="text" placeholder="" class="form-control input-md" value="${company.email}">

            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="web">Web-адрес</label>
            <div class="col-md-4">
                <input id="web" name="web" type="text" placeholder="" class="form-control input-md" value ="${company.web}">

            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="address">Адрес</label>
            <div class="col-md-4">
                <input id="address" name="address" type="text" placeholder="" class="form-control input-md" value ="${company.adress}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="btn_save_company"></label>
            <div class="col-md-2">
                <button id="btn_save_company" name="btn_save_company" class="btn btn-default">Записать</button>
                <button id="btn_company_cancel" name="btn_company_cancel" class="btn btn-default" onclick="window.history.back()">Отмена</button>
            </div>
            <div class="col-md-2">
            </div>
        </div>

    </fieldset>
</form>
</div>
</body>
</html>
