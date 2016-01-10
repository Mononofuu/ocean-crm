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
<body>
<div class="col-lg-2">
    <jsp:include page="menu.jsp"/>
</div>
<div ng-controller="DealController" class="col-lg-10">

    <form class="col-md-3" name="dynamicForm" ng-submit="processForm('newcontact')">
        <fieldset>
            <legend>Добавить контакт</legend>
                    <a ng-click="removeContact(sc.id)" class="btn-info btn btn-block" ng-repeat="sc in selectedContacts">{{sc.name}}</a><br/>
                <label>Выберите контакт</label>
                <select class="form-control" ng-model="selected" ng-change="selectContact(selected)">
                    <option ng-repeat="contact in contacts" value="{{contact.id +','+ contact.name}}">{{contact.name}}</option>
                </select>
            <br/>
            <label>или создайте новый</label>
            <input class="form-control" type="text" name="contactname" placeholder="Имя Фамилия"
                   ng-model="formData.contactname" required/>
            <br/>
            <select class="form-control" name="contactcompany" ng-model="formData.contactcompany">
                <option ng-repeat="company in companies" value="{{company.id}}">{{company.name}}</option>
            </select>
            <br/>
            <input class="form-control" type="text" name="contactposition" placeholder="Должность (Название должности)"
                   ng-model="formData.contactposition"/>
            <br/>
            <select class="form-control" name="contactphonetype" ng-model="formData.contactphonetype">
                <option ng-repeat="phoneType in phoneTypes" value="{{phoneType}}">{{phoneType}}</option>
            </select>
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

    <form  class="col-md-3" name="dynamicForm" ng-submit="processForm('newcompany')">
        <fieldset>
            <legend>Прикрепить компанию</legend>
            <label>Выберите компанию</label>
            <select class="form-control" name="dealcompany">
                <option ng-repeat="company in companies" value="{{company.id}}">{{company.name}}</option>

            </select>
            <label >или добавить новую</label>
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
