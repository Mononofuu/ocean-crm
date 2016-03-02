<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page isELIgnored="false" %>
<style>
    <%@include file='../resources/css/newdeal.css' %>
    <%@include file='../resources/css/bootstrap.min.css' %>
</style>
<!DOCTYPE html>

<html ng-app="newDeal">
<head>
    <title><spring:message code="label.adddeal"/></title>
    <meta charset="utf-8">

    <!-- LOAD JQUERY -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <%--<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.0/angular.min.js"></script>--%>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <!-- PROCESS FORM WITH AJAX (NEW) -->
    <script src="../resources/js/newdeal.js"></script>
    <script src="../resources/js/files.js"></script>

</head>
<body>
<div class="col-lg-2">
    <jsp:include page="menu.jsp"/>
</div>
<div ng-controller="DealController" class="col-lg-10">
    <div class="row">
        <form class="col-md-3" name="dealForm" ng-submit="processForm('deals')" novalidate>
            <fieldset>
                <legend><spring:message code="label.adddeal"/></legend>
                <label><spring:message code="label.dealname"/></label>
                <input class="form-control" type="text" name="dealname"
                       ng-model="formData.dealname" required/>
                <br/>
                <label><spring:message code="label.creationdate"/></label>
                <input class="form-control" type="date" name="dealcreated"
                       ng-model="formData.dealcreated" required/>
                <br/>
                <label><spring:message code="label.tags"/></label>
                <input class="form-control" type="text" name="dealtags"
                       ng-model="formData.dealtags"/>
                <br/>
                <label><spring:message code="label.responsible"/></label>
                <select class="form-control" name="dealresp" ng-model="formData.dealresp">
                    <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>
                </select>
                <br/>
                <label><spring:message code="label.budget"/></label>
                <input class="form-control" type="text" name="dealbudget" pattern="[0-9]*"
                       ng-model="formData.dealbudget" required/>
                <br/>
                <label><spring:message code="label.phase"/></label>
                <select class="form-control" name="dealstatus" ng-model="formData.dealstatus" required>
                    <option ng-repeat="status in dealStatuses" value="{{status.id}}">{{status.name}}</option>
                </select>
                <br/>
                <label><spring:message code="label.dealnote"/></label>
                <textarea class="form-control textarea" name="dealcomment"
                          ng-model="formData.dealcomment"></textarea>
                <br/>
                <div id="selectedFiles"></div>
                <br/>
                <input class="form-control btn-success" type="file" name="dealfiles" id="files" multiple
                       ng-model="files"><br/>
            </fieldset>
        </form>
        <form class="col-md-3" name="contactForm" ng-submit="processForm('contacts')">
            <fieldset>
                <legend><spring:message code="label.addcontact"/></legend>
                <a ng-click="removeContact(sc.id)" class="btn-info btn btn-block" ng-repeat="sc in selectedContacts"
                >{{sc.name}}</a>
                <label><spring:message code="label.choosecontact"/></label>
                <select class="form-control" ng-model="selected" ng-change="selectContact(selected)">
                    <option ng-repeat="contact in contacts" value="{{contact.id +','+ contact.name}}">
                        {{contact.name}}
                    </option>
                </select>
                <br/>
                <label><spring:message code="label.ornew"/></label>
                <br/>
                <label><spring:message code="label.firstlastname"/></label>
                <input class="form-control" type="text" name="contactname"
                       ng-model="formData.contactname" required/>
                <br/>
                <label><spring:message code="label.company"/></label>
                <select class="form-control" name="contactcompany" ng-model="formData.contactcompany">
                    <option ng-repeat="company in companies" value="{{company.id}}">{{company.name}}</option>
                </select>
                <br/>
                <label><spring:message code="label.position"/></label>
                <input class="form-control" type="text" name="contactposition"
                       ng-model="formData.contactposition"/>
                <br/>
                <label><spring:message code="label.phonetypenumber"/></label>
                <div class="form-inline">
                    <select class="form-control" style="padding-left: 1%; padding-right: 1%; width: 23%"
                            name="contactphonetype" ng-model="formData.contactphonetype">
                        <option ng-repeat="phoneType in phoneTypes" value="{{phoneType}}">{{phoneType}}</option>
                    </select>
                    <input class="form-control" type="text" style="padding-left: 1%;width: 75%" type="text"
                           name="contactphonenumber" placeholder=
                           <spring:message code="label.phonenumber"/>
                                   ng-model="formData.contactphonenumber"/>
                </div>
                <br/>
                <label>Email</label>
                <input class="form-control" type="email" name="contactemail"
                       ng-model="formData.contactemail"/>
                <br/>
                <label>Skype</label>
                <input class="form-control" type="text" name="contactskype"
                       ng-model="formData.contactskype"/>
                <br/>
                <button type="submit" ng-hide="contactForm.contactname.$invalid" ng-disabled="contactForm.$invalid"
                        class="btn btn-sm btn-success btn-block">
                    <span class="glyphicon"></span><spring:message code="label.createcontact"/>
                </button>
            </fieldset>
        </form>

        <form class="col-md-3" name="companyForm" ng-submit="processForm('companies')">
            <fieldset>
                <legend><spring:message code="label.addcompany"/></legend>
                <label><spring:message code="label.choosecompany"/></label>
                <select class="form-control" name="dealcompany" ng-model="formData.dealcompany">
                    <option ng-repeat="company in companies" value="{{company.id}}">{{company.name}}</option>

                </select><br/>
                <label><spring:message code="label.ornew"/></label>
                <label><spring:message code="label.companyname"/></label>
                <input class="form-control" type="text" name="companyname"
                       ng-model="formData.companyname" required/>
                <br/>
                <label><spring:message code="label.phonenumber"/></label>
                <input class="form-control" type="tel" name="companyphone"
                       ng-model="formData.companyphone" required>
                <br/>
                <label><spring:message code="label.email"/></label>
                <input class="form-control" type="email" name="companyemail"
                       ng-model="formData.companyemail" required>
                <br/>
                <label><spring:message code="label.web"/></label>
                <input class="form-control" type="url" name="companysite"
                       ng-model="formData.companysite" required>
                <br/>
                <label><spring:message code="label.address"/></label>
                <textarea class="form-control textarea" name="companyaddress"
                          ng-model="formData.companyaddress"></textarea>
                <br/>
                <button type="submit" ng-hide="companyForm.companyname.$invalid" ng-disabled="companyForm.$invalid"
                        class="btn btn-sm btn-success btn-block">
                    <span class="glyphicon"></span><spring:message code="label.createcompany"/>
                </button>
            </fieldset>
        </form>

        <form class="col-md-3" name="taskForm" ng-init="formData.addTask=false">
            <fieldset>
                <legend><spring:message code="label.addtask"/></legend>
                <input type="checkbox" ng-model="formData.addTask"><spring:message code="label.addtask"/>
                <div ng-show="formData.addTask">
                    <label><spring:message code="label.period"/></label>
                    <select class="form-control" name="taskperiod" ng-model="formData.taskperiod">
                        <option value="today"><spring:message code="label.today"/></option>
                        <option value="allday"><spring:message code="label.allday"/></option>
                        <option value="tomorow"><spring:message code="label.tomorow"/></option>
                        <option value="nextweek"><spring:message code="label.nextweek"/></option>
                        <option value="nextmonth"><spring:message code="label.nextmonth"/></option>
                        <option value="nextyear"><spring:message code="label.nextyear"/></option>
                    </select>
                    <label><spring:message code="label.orchoosedate"/></label>
                    <input class="form-control" type="datetime-local" name="taskduedate"
                           ng-model="formData.taskduedate" required>
                    <br/>
                    <label><spring:message code="label.responsible"/></label>
                    <select class="form-control" name="taskuser" ng-model="formData.taskuser" required>
                        <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>
                    </select>
                    <br/>
                    <label><spring:message code="label.tasktype"/></label>
                    <select class="form-control" name="tasktype" ng-model="formData.tasktype">
                        <option ng-repeat="taskType in taskTypes" value="{{taskType}}">{{taskType}}</option>
                    </select>
                    <br/>
                    <label><spring:message code="label.tasknote"/></label>
                    <textarea class="form-control textarea" name="taskcomment" placeholder=
                    <spring:message code="label.tasknote"/>
                            ng-model="formData.taskcomment"></textarea>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="row">
        <button ng-disabled="dealForm.$invalid" type="submit" ng-click="processForm('deals')"
                class="btn btn-lg btn-primary btn-block"><spring:message code="label.createdeal"/>
        </button>
    </div>
</div>
</body>
</html>
