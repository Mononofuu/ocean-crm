<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%--<%@include file='../css/newdeal.css' %>--%>
    <%@include file='../css/bootstrap.css' %>
</style>
<!DOCTYPE html>

<html ng-app="newDeal">

<head>
    <title>Добавить сделку</title>
    <meta charset="utf-8">

    <!-- LOAD JQUERY -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <%--<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.0/angular.min.js"></script>--%>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <!-- PROCESS FORM WITH AJAX (NEW) -->
    <script src="/js/newdeal.js"></script>


</head>
<body ng-controller="formController" onload="getCompanies(); getPhoneTypes()">
<div class="col-lg-2">
    <jsp:include page="menu.jsp"/>
</div>
<div class="col-lg-10">

    <form  class="col-md-3" name="dynamicForm" ng-submit="processForm()">
        <fieldset>
            <legend>Добавить контакт</legend>
            <div ng-controller="ContactsController">
                    <a ng-click="removeContact(sc.id)" class="btn-info btn btn-block" ng-repeat="sc in selectedContacts">{{sc.name}}</a><br/>
                <label>Выберите контакт</label>
                <select class="form-control" ng-model="selected" ng-change="selectContact(selected)">
                    <option ng-repeat="contact in contacts" value="{{contact.id +','+ contact.name}}">{{contact.name}}</option>
                </select>
            </div>
            <br/>
            <h5 class="center">или создать новый</h5>
            <input class="form-control" type="text" name="contactname" placeholder="Имя Фамилия"
                   ng-model="formData.contactname" required/>
            <br/>
            <select class="companies form-control" name="contactcompany"></select>
            <br/>
            <input class="form-control" type="text" name="contactposition" placeholder="Должность (Название должности)"
                   ng-model="formData.contactposition"/>
            <br/>
            <select class="phonetype form-control" name="contactphonetype"></select>
            <input class="form-control" type="text" name="contactphonenumber" placeholder="Номер телефона"
                   ng-model="formData.contactphonenumber"/>
            <br/>
            <input class="form-control" type="email" name="contactemail" placeholder="Email"
                   ng-model="formData.contactemail"/>
            <br/>
            <input class="form-control" type="text" name="contactskype" placeholder="Skype"
                   ng-model="formData.contactskype"/>
            <br/>
            <button type="submit" class="btn btn-success btn-block">
                <span class="glyphicon"></span> Создать контакт
            </button>
        </fieldset>
    </form>

    <form class="col-md-3" name="dynamicForm" ng-submit="processForm()">
        <fieldset>
            <legend>Прикрепить компанию</legend>
            <select class="companies form-control" name="dealcompany"></select>
            <h5 class="center">или добавить новую</h5>
            <div>
                <input class="form-control" type="text" name="companyname" placeholder="Название компании"
                       ng-model="formData.companyname" required/>
                <br/>
                <input class="form-control" type="tel" name="companyphone" placeholder="Телефон"
                       ng-model="formData.companyphone" required>
                <br/>
                <input class="form-control" type="email" name="companyemail" placeholder="Email"
                       ng-model="formData.companyemail" required>
                <br/>
                <input class="form-control" type="url" name="companysite" placeholder="Web-адрес"
                       ng-model="formData.companysite" required>
                <br/>
                <textarea class="form-control textarea" name="companyaddress" placeholder="Адрес"
                          ng-model="formData.companyaddress" required></textarea>
                <br/>
                <button type="submit" class="btn btn-success btn-block">
                    <span class="glyphicon"></span> Создать команию
                </button>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
