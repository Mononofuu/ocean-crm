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
    <script src="/js/files.js"></script>


</head>
<body>
<div class="col-lg-2">
    <jsp:include page="menu.jsp"/>
</div>
<div ng-controller="DealController" class="col-lg-10">
    <div class="row">
        <form class="col-md-3" name="dynamicForm" ng-submit="processForm('newdeal')">
            <fieldset>
                <legend>Добавить сделку</legend>
                <input class="form-control" type="text" name="dealname" placeholder="Название сделки"
                       ng-model="formData.dealname" required/>
                <br/>
                <input class="form-control" type="text" name="dealtags" placeholder="Теги"
                       ng-model="formData.dealtags" required/>
                <br/>
                <select class="form-control" name="dealresp" ng-model="formData.dealresp">
                    <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>
                </select>
                <br/>
                <input class="form-control" type="text" name="dealbudget" placeholder="Бюджет" pattern="[0-9]*"
                       title="Цифра с плавающей точкой" ng-model="formData.dealbudget" required/>
                <br/>
                <select class="form-control" name="tasktype" ng-model="formData.tasktype">
                    <option ng-repeat="status in dealStatuses" value="{{status.id}}">{{status.name}}</option>
                </select>
                <br/>
            <textarea class="form-control textarea" name="dealcomment" placeholder="Примечание по сделке"
                      ng-model="formData.dealcomment"></textarea>
                <br/>

                <div id="selectedFiles"></div>
                <br/>
                <input class="form-control btn-success" type="file" name="dealfiles" id="files" multiple
                       ng-model="files"><br/>

            </fieldset>
        </form>
        <form class="col-md-3" name="dynamicForm" ng-submit="processForm('newcontact')">
            <fieldset>
                <legend>Добавить контакт</legend>
                <a ng-click="removeContact(sc.id)" class="btn-info btn btn-block" ng-repeat="sc in selectedContacts"
                >{{sc.name}}</a>
                {{selectedContacts}}
                {{formData.dealcontactlist}}
                <label>Выберите контакт</label>
                <select class="form-control" ng-model="selected" ng-change="selectContact(selected)">
                    <option ng-repeat="contact in contacts" value="{{contact.id +','+ contact.name}}">{{contact.name}}
                    </option>
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
                <input class="form-control" type="text" name="contactposition"
                       placeholder="Должность (Название должности)"
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
                <button type="submit" class="btn btn-sm btn-success btn-block">
                    <span class="glyphicon"></span> Создать контакт
                </button>
            </fieldset>
        </form>

        <form class="col-md-3" name="dynamicForm" ng-submit="processForm('newcompany')">
            <fieldset>
                <legend>Прикрепить компанию</legend>
                <label>Выберите компанию</label>
                <select class="form-control" name="dealcompany" ng-model="formData.dealcompany">
                    <option ng-repeat="company in companies" value="{{company.id}}">{{company.name}}</option>

                </select>
                <label>или добавить новую</label>
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
                <button type="submit" class="btn btn-sm btn-success btn-block">
                    <span class="glyphicon"></span> Создать команию
                </button>
            </fieldset>
        </form>

        <form class="col-md-3" name="dynamicForm" ng-submit="processForm('newtask')" ng-init="addTask=false">
            <fieldset>
                <legend>Запланировать задачу</legend>
                <input type="checkbox" ng-model="addTask">Добавить задачу к новой сделке
                <div ng-show="addTask">
                    <select class="form-control" name="taskperiod" ng-model="formData.taskperiod">
                        <option>Сегодня</option>
                        <option>Завтра</option>
                        <option>Послезавтра</option>
                    </select>
                    <label>или</label>
                    <input class="form-control" type="datetime-local" name="taskduedate"
                           ng-model="formData.taskduedate" required>
                    <br/>
                    <select class="form-control" name="taskuser" ng-model="formData.taskuser">
                        <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>
                    </select>
                    <br/>
                    <select class="form-control" name="tasktype" ng-model="formData.tasktype">
                        <option ng-repeat="taskType in taskTypes" value="{{taskType}}">{{taskType}}</option>
                    </select>
                    <br/>
                <textarea class="form-control textarea" name="taskcomment" placeholder="Текст задачи"
                          ng-model="formData.taskcomment"></textarea>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="row">
        <button type="submit" ng-click="processForm('newdeal')" class="btn btn-lg btn-primary btn-block">Создать сделку
        </button>
    </div>
</div>
</body>
</html>
