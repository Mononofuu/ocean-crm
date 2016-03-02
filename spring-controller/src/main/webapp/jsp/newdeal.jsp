<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page isELIgnored="false" %>
<style>
    <%@include file='../resources/css/newdeal.css' %>
    <%@include file='../resources/css/bootstrap.min.css' %>
</style>
<!DOCTYPE html>

<html ng-app="dealFormAjaxApp">
<head>
    <title><spring:message code="label.adddeal"/></title>
    <meta charset="utf-8">

    <!-- LOAD JQUERY -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <%--<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.0/angular.min.js"></script>--%>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <!-- PROCESS FORM WITH AJAX (NEW) -->
    <script src="../resources/js/newdeal.js"></script>
    <%--<script src="../resources/js/files.js"></script>--%>

</head>
<body>
<div class="col-lg-2">
    <jsp:include page="menu.jsp"/>
</div>
<div ng-controller="DealController" class="col-lg-10">
    <div class="row">
        <form class="col-md-3" name="dealForm" ng-submit="addDealAsyncAsJSON()" novalidate>
            <fieldset>
                <legend><spring:message code="label.adddeal"/></legend>
                <label><spring:message code="label.dealname"/></label>
                <input class="form-control" type="text" name="name"
                       ng-model="deal.name" required/>
                <br/>
                <label><spring:message code="label.creationdate"/></label>
                <input class="form-control" type="date" name="dateCreated"
                       ng-model="deal.dateCreated" required/>
                <br/>
                <label><spring:message code="label.responsible"/></label>
                <select class="form-control" name="responsible" ng-model="deal.user">
                    <option ng-repeat="user in users" value="{{user}}">{{user.name}}</option>
                </select>
                <br/>
                <label><spring:message code="label.budget"/></label>
                <input class="form-control" type="text" name="budget" pattern="[0-9]*"
                       ng-model="deal.budget" required/>
                <br/>
                <button ng-disabled="dealForm.$invalid" type="submit" ng-click="processForm('deals')"
                        class="btn btn-lg btn-primary btn-block"><spring:message code="label.createdeal"/>
                </button>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
