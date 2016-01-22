<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<style>
    <%@include file='../css/newcompany.css' %>
    <%@include file='../css/bootstrap.css' %>
</style>
<!DOCTYPE html>

<html ng-app="newCompany">
<fmt:bundle basename="app">
    <head>
        <title><fmt:message key="addcompany"/></title>
        <meta charset="utf-8">

        <!-- LOAD JQUERY -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
            <%--<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.0/angular.min.js"></script>--%>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
        <!-- PROCESS FORM WITH AJAX (NEW) -->
        <script src="/js/newcompany.js"></script>
        <script src="/js/files.js"></script>


    </head>
    <body>
    <div class="col-lg-2">
        <jsp:include page="menu.jsp"/>
    </div>
    <div ng-controller="ContactController" class="col-lg-10">
        <div class="row">
            <form class="col-md-3" name="companyForm" ng-submit="processForm('newcompany')" novalidate>
                <fieldset>
                    <legend><fmt:message key="createcompany"/></legend>
                    <label><fmt:message key="companyname"/></label>
                    <input class="form-control" type="text" name="companyname" placeholder=
                        <fmt:message key="companyname"/>
                            ng-model="formData.companyname" required/>
                    <br/>
                    <label><fmt:message key="tags"/></label>
                    <input class="form-control" type="text" name="companytags"
                           ng-model="formData.companytags"/>
                    <br/>
                    <label><fmt:message key="responsible"/></label>
                    <select title=
                                <fmt:message key="responsible"/> class="form-control" name="companyresp"
                            ng-model="formData.companyresp">
                        <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>
                    </select>
                    <label><fmt:message key="phonetypenumber"/></label>
                    <div class="form-inline">
                        <select class="form-control" style="padding-left: 1%; padding-right: 1%; width: 23%"
                                name="companyphonetype" ng-model="formData.companyphonetype">
                            <option ng-repeat="phoneType in phoneTypes" value="{{phoneType}}">{{phoneType}}</option>
                        </select>
                        <input class="form-control" type="tel" style="padding-left: 1%;width: 75%"
                               name="companyphone" placeholder=
                                   <fmt:message key="phonetypenumber"/>
                                       ng-model="formData.companyphone"/>
                    </div>
                    <br/>
                    <label><fmt:message key="email"/></label>
                    <input class="form-control" type="email" name="companyemail" placeholder=
                        <fmt:message key="email"/>
                            ng-model="formData.companyemail" required>
                    <br/>
                    <label><fmt:message key="web"/></label>
                    <input class="form-control" type="url" name="companysite" placeholder=
                        <fmt:message key="web"/>
                            ng-model="formData.companysite">
                    <br/>
                    <label><fmt:message key="address"/></label>
                <textarea class="form-control textarea" name="companyaddress" placeholder=
                    <fmt:message key="address"/>
                        ng-model="formData.companyaddress"></textarea>
                    <br/>
                    <label><fmt:message key="companynote"/></label>
                <textarea class="form-control textarea" name="companycomment"
                          placeholder=
                              <fmt:message key="companynote"/>
                                  ng-model="formData.companycomment"></textarea>
                    <br/>
                    <div id="selectedFiles"></div>
                    <br/>
                    <input class="form-control btn-success" type="file" name="companyfiles" id="files" multiple
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
                    <label><fmt:message key="firstlastname"/></label>
                    <input class="form-control" type="text" name="contactname" placeholder=
                        <fmt:message key="firstlastname"/>
                            ng-model="formData.contactname" required/>
                    <br/>
                    <label><fmt:message key="position"/></label>
                    <input class="form-control" type="text" name="contactposition"
                           placeholder=
                               <fmt:message key="position"/>
                                   ng-model="formData.contactposition"/>
                    <br/>
                    <label><fmt:message key="phonetypenumber"/></label>
                    <div class="form-inline">
                        <select class="form-control" style="padding-left: 1%; padding-right: 1%; width: 23%"
                                name="contactphonetype" ng-model="formData.contactphonetype">
                            <option ng-repeat="phoneType in phoneTypes" value="{{phoneType}}">{{phoneType}}</option>
                        </select>
                        <input class="form-control" style="padding-left: 1%;width: 75%" type="text"
                               name="contactphonenumber" placeholder=
                                   <fmt:message key="phonenumber"/>
                                       ng-model="formData.contactphonenumber"/>
                    </div>
                    <br/>
                    <label><fmt:message key="email"/></label>
                    <input class="form-control" type="email" name="contactemail" placeholder=
                        <fmt:message key="email"/>
                            ng-model="formData.contactemail"/>
                    <br/>
                    <label><fmt:message key="skype"/></label>
                    <input class="form-control" type="text" name="contactskype" placeholder=
                        <fmt:message key="skype"/>
                            ng-model="formData.contactskype"/>
                    <br/>
                    <button type="submit" ng-hide="contactForm.contactname.$invalid" ng-disabled="contactForm.$invalid"
                            class="btn btn-sm btn-success btn-block">
                        <span class="glyphicon"></span><fmt:message key="createcontact"/>
                    </button>
                </fieldset>
            </form>
            <form class="col-md-3" name="dealForm" ng-submit="addDeal()" novalidate>
                <fieldset>
                    <legend><fmt:message key="adddeal"/></legend>
                    <div ng-show="addedDeals.length>0">
                        <label><fmt:message key="dealcount"/>: {{addedDeals.length}}</label>
                        <label><fmt:message key="totalamount"/>: {{getTotal()}}</label>
                        <a ng-click="removeDeal(deal)" class="btn-info btn btn-block" ng-repeat="deal in addedDeals"
                        ><fmt:message key="name"/>:{{deal.name}}<br><fmt:message key="phase"/>:{{deal.status}}<br>
                            <fmt:message key="budget"/>:{{deal.budget}}</a>
                    </div>
                    <br/>
                    <label><fmt:message key="dealname"/></label>
                    <input class="form-control" type="text" name="dealname" placeholder=
                        <fmt:message key="dealname"/>
                            ng-model="formData.dealname" required/>
                    <br/>
                    <label><fmt:message key="phase"/></label>
                    <select class="form-control" name="dealstatus" ng-model="formData.dealstatus" required>
                        <option ng-repeat="status in dealStatuses" value="{{status.id}}">{{status.name}}</option>
                    </select>
                    <br/>
                    <label><fmt:message key="budget"/></label>
                    <input class="form-control" type="text" name="dealbudget" placeholder=
                        <fmt:message key="budget"/> pattern="[0-9]*"
                           ng-model="formData.dealbudget" required/>
                    <button type="submit" ng-hide="dealForm.dealname.$invalid" ng-disabled="dealForm.$invalid"
                            class="btn btn-sm btn-success btn-block">
                        <span class="glyphicon"></span><fmt:message key="adddeal"/>
                    </button>
                </fieldset>
            </form>
            <form class="col-md-3" name="taskForm" ng-submit="processForm('newtask')" ng-init="addTask=false">
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
                    <textarea class="form-control textarea" name="taskcomment" placeholder=<fmt:message key="tasknote"/>
                              ng-model="formData.taskcomment"></textarea>
                    </div>
                </fieldset>
            </form>
        </div>
        <div class="row">
            <button ng-disabled="companyForm.$invalid" type="submit" ng-click="processForm('newcompany')"
                    class="btn btn-lg btn-primary btn-block"><fmt:message key="createdeal"/>
            </button>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>