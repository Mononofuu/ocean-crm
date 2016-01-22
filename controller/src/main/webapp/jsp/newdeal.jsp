<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<style>
    <%@include file='../css/newdeal.css' %>
    <%@include file='../css/bootstrap.css' %>
</style>
<!DOCTYPE html>

<html ng-app="newDeal">
<fmt:bundle basename="app">
    <head>
        <title><fmt:message key="adddeal"/></title>
        <meta charset="utf-8">

        <!-- LOAD JQUERY -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
            <%--<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.0/angular.min.js"></script>--%>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
        <!-- PROCESS FORM WITH AJAX (NEW) -->
        <script src="/js/newdeal.js"></script>
        <script src="/js/files.js"></script>


    </head>
    <body>
    <div class="col-lg-2">
        <jsp:include page="menu.jsp"/>
    </div>
    <div ng-controller="DealController" class="col-lg-10">
        <div class="row">
            <form class="col-md-3" name="dealForm" ng-submit="processForm('newdeal')" novalidate>
                <fieldset>
                    <legend><fmt:message key="adddeal"/></legend>
                    <label><fmt:message key="dealname"/></label>
                    <input class="form-control" type="text" name="dealname"
                           ng-model="formData.dealname" required/>
                    <br/>
                    <label><fmt:message key="creationdate"/></label>
                    <input class="form-control" type="date" name="dealcreated"
                           ng-model="formData.dealcreated" required/>
                    <br/>
                    <label><fmt:message key="tags"/></label>
                    <input class="form-control" type="text" name="dealtags"
                           ng-model="formData.dealtags"/>
                    <br/>
                    <label><fmt:message key="responsible"/></label>
                    <select class="form-control" name="dealresp" ng-model="formData.dealresp">
                        <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>
                    </select>
                    <br/>
                    <label><fmt:message key="budget"/></label>
                    <input class="form-control" type="text" name="dealbudget" pattern="[0-9]*"
                           ng-model="formData.dealbudget" required/>
                    <br/>
                    <label><fmt:message key="phase"/></label>
                    <select class="form-control" name="dealstatus" ng-model="formData.dealstatus" required>
                        <option ng-repeat="status in dealStatuses" value="{{status.id}}">{{status.name}}</option>
                    </select>
                    <br/>
                    <label><fmt:message key="dealnote"/></label>
                <textarea class="form-control textarea" name="dealcomment"
                          ng-model="formData.dealcomment"></textarea>
                    <br/>
                    <div id="selectedFiles"></div>
                    <br/>
                    <input class="form-control btn-success" type="file" name="dealfiles" id="files" multiple
                           ng-model="files"><br/>
                </fieldset>
            </form>
            <form class="col-md-3" name="contactForm" ng-submit="processForm('newcontact')">
                <fieldset>
                    <legend><fmt:message key="addcontact"/></legend>
                    <a ng-click="removeContact(sc.id)" class="btn-info btn btn-block" ng-repeat="sc in selectedContacts"
                    >{{sc.name}}</a>
                    <label><fmt:message key="choosecontact"/></label>
                    <select class="form-control" ng-model="selected" ng-change="selectContact(selected)">
                        <option ng-repeat="contact in contacts" value="{{contact.id +','+ contact.name}}">
                            {{contact.name}}
                        </option>
                    </select>
                    <br/>
                    <label><fmt:message key="ornew"/></label>
                    <br/>
                    <label><fmt:message key="firstlastname"/></label>
                    <input class="form-control" type="text" name="contactname"
                           ng-model="formData.contactname" required/>
                    <br/>
                    <label><fmt:message key="company"/></label>
                    <select class="form-control" name="contactcompany" ng-model="formData.contactcompany">
                        <option ng-repeat="company in companies" value="{{company.id}}">{{company.name}}</option>
                    </select>
                    <br/>
                    <label><fmt:message key="position"/></label>
                    <input class="form-control" type="text" name="contactposition"
                           ng-model="formData.contactposition"/>
                    <br/>
                    <label><fmt:message key="phonetypenumber"/></label>
                    <div class="form-inline">
                        <select class="form-control" style="padding-left: 1%; padding-right: 1%; width: 23%"
                                name="contactphonetype" ng-model="formData.contactphonetype">
                            <option ng-repeat="phoneType in phoneTypes" value="{{phoneType}}">{{phoneType}}</option>
                        </select>
                        <input class="form-control" type="text" style="padding-left: 1%;width: 75%" type="text"
                               name="contactphonenumber" placeholder=
                                   <fmt:message key="phonenumber"/>
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
                        <span class="glyphicon"></span><fmt:message key="createcontact"/>
                    </button>
                </fieldset>
            </form>

            <form class="col-md-3" name="companyForm" ng-submit="processForm('newcompany')">
                <fieldset>
                    <legend><fmt:message key="addcompany"/></legend>
                    <label><fmt:message key="choosecompany"/></label>
                    <select class="form-control" name="dealcompany" ng-model="formData.dealcompany">
                        <option ng-repeat="company in companies" value="{{company.id}}">{{company.name}}</option>

                    </select><br/>
                    <label><fmt:message key="ornew"/></label>
                    <label><fmt:message key="companyname"/></label>
                    <input class="form-control" type="text" name="companyname"
                           ng-model="formData.companyname" required/>
                    <br/>
                    <label><fmt:message key="phonenumber"/></label>
                    <input class="form-control" type="tel" name="companyphone"
                           ng-model="formData.companyphone" required>
                    <br/>
                    <label><fmt:message key="email"/></label>
                    <input class="form-control" type="email" name="companyemail"
                           ng-model="formData.companyemail" required>
                    <br/>
                    <label><fmt:message key="web"/></label>
                    <input class="form-control" type="url" name="companysite"
                           ng-model="formData.companysite" required>
                    <br/>
                    <label><fmt:message key="address"/></label>
                <textarea class="form-control textarea" name="companyaddress"
                          ng-model="formData.companyaddress"></textarea>
                    <br/>
                    <button type="submit" ng-hide="companyForm.companyname.$invalid" ng-disabled="companyForm.$invalid"
                            class="btn btn-sm btn-success btn-block">
                        <span class="glyphicon"></span><fmt:message key="createcompany"/>
                    </button>
                </fieldset>
            </form>

            <form class="col-md-3" name="taskForm" ng-init="formData.addTask=false">
                <fieldset>
                    <legend><fmt:message key="addtask"/></legend>
                    <input type="checkbox" ng-model="formData.addTask"><fmt:message key="addtask"/>
                    <div ng-show="formData.addTask">
                        <label><fmt:message key="period"/></label>
                        <select class="form-control" name="taskperiod" ng-model="formData.taskperiod">
                            <option value="today"><fmt:message key="today"/></option>
                            <option value="allday"><fmt:message key="allday"/></option>
                            <option value="tomorow"><fmt:message key="tomorow"/></option>
                            <option value="nextweek"><fmt:message key="nextweek"/></option>
                            <option value="nextmonth"><fmt:message key="nextmonth"/></option>
                            <option value="nextyear"><fmt:message key="nextyear"/></option>
                        </select>
                        <label><fmt:message key="orchoosedate"/></label>
                        <input class="form-control" type="datetime-local" name="taskduedate"
                               ng-model="formData.taskduedate" required>
                        <br/>
                        <label><fmt:message key="responsible"/></label>
                        <select class="form-control" name="taskuser" ng-model="formData.taskuser" required>
                            <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>
                        </select>
                        <br/>
                        <label><fmt:message key="tasktype"/></label>
                        <select class="form-control" name="tasktype" ng-model="formData.tasktype">
                            <option ng-repeat="taskType in taskTypes" value="{{taskType}}">{{taskType}}</option>
                        </select>
                        <br/>
                        <label><fmt:message key="tasknote"/></label>
                    <textarea class="form-control textarea" name="taskcomment" placeholder=
                        <fmt:message key="tasknote"/>
                            ng-model="formData.taskcomment"></textarea>
                    </div>
                </fieldset>
            </form>
        </div>
        <div class="row">
            <button ng-disabled="dealForm.$invalid" type="submit" ng-click="processForm('newdeal')"
                    class="btn btn-lg btn-primary btn-block"><fmt:message key="createdeal"/>
            </button>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
