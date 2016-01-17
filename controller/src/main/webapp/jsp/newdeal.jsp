<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/newdeal.css' %>
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
        <form class="col-md-3" name="dealForm" ng-submit="processForm('newdeal')" novalidate>
            <fieldset>
                <legend>Добавить сделку</legend>
                <label>Название сделки</label>
                <input class="form-control" type="text" name="dealname"
                       ng-model="formData.dealname" required/>
                <br/>
                <label>Дата создания</label>
                <input class="form-control" type="date" name="dealcreated"
                       ng-model="formData.dealcreated" required/>
                <br/>
                <label>Теги</label>
                <input class="form-control" type="text" name="dealtags"
                       ng-model="formData.dealtags"/>
                <br/>
                <label>Ответственный</label>
                <select class="form-control" name="dealresp" ng-model="formData.dealresp">
                    <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>
                </select>
                <br/>
                <label>Бюджет</label>
                <input class="form-control" type="text" name="dealbudget" pattern="[0-9]*"
                       title="Цифра с плавающей точкой" ng-model="formData.dealbudget" required/>
                <br/>
                <label>Этап</label>
                <select class="form-control" name="dealstatus" ng-model="formData.dealstatus" required>
                    <option ng-repeat="status in dealStatuses" value="{{status.id}}">{{status.name}}</option>
                </select>
                <br/>
                <label>Примечание по сделке</label>
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
                <legend>Добавить контакт</legend>
                <a ng-click="removeContact(sc.id)" class="btn-info btn btn-block" ng-repeat="sc in selectedContacts"
                >{{sc.name}}</a>
                <label>Выберите контакт</label>
                <select class="form-control" ng-model="selected" ng-change="selectContact(selected)">
                    <option ng-repeat="contact in contacts" value="{{contact.id +','+ contact.name}}">{{contact.name}}
                    </option>
                </select>
                <br/>
                <label>или создайте новый:</label>
                <br/>
                <label>Имя Фамилия</label>
                <input class="form-control" type="text" name="contactname"
                       ng-model="formData.contactname" required/>
                <br/>
                <label>Компания</label>
                <select class="form-control" name="contactcompany" ng-model="formData.contactcompany">
                    <option ng-repeat="company in companies" value="{{company.id}}">{{company.name}}</option>
                </select>
                <br/>
                <label>Должность</label>
                <input class="form-control" type="text" name="contactposition"
                       ng-model="formData.contactposition"/>
                <br/>
                <label>Тип и номер телефона</label>
                <div class="form-inline">
                    <select class="form-control" style="padding-left: 1%; padding-right: 1%; width: 23%"
                            name="contactphonetype" ng-model="formData.contactphonetype">
                        <option ng-repeat="phoneType in phoneTypes" value="{{phoneType}}">{{phoneType}}</option>
                    </select>
                    <input class="form-control" type="text" style="padding-left: 1%;width: 75%" type="text"
                           name="contactphonenumber" placeholder="Номер телефона"
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
                    <span class="glyphicon"></span> Создать контакт
                </button>
            </fieldset>
        </form>

        <form class="col-md-3" name="companyForm" ng-submit="processForm('newcompany')">
            <fieldset>
                <legend>Прикрепить компанию</legend>
                <label>Выберите компанию</label>
                <select class="form-control" name="dealcompany" ng-model="formData.dealcompany">
                    <option ng-repeat="company in companies" value="{{company.id}}">{{company.name}}</option>

                </select><br/>
                <label>или добавьте новую</label>
                <label>Название компании</label>
                <input class="form-control" type="text" name="companyname"
                       ng-model="formData.companyname" required/>
                <br/>
                <label>Телефон</label>
                <input class="form-control" type="tel" name="companyphone"
                       ng-model="formData.companyphone" required>
                <br/>
                <label>Email</label>
                <input class="form-control" type="email" name="companyemail"
                       ng-model="formData.companyemail" required>
                <br/>
                <label>Web-адрес</label>
                <input class="form-control" type="url" name="companysite"
                       ng-model="formData.companysite" required>
                <br/>
                <label>Адрес</label>
                <textarea class="form-control textarea" name="companyaddress"
                          ng-model="formData.companyaddress"></textarea>
                <br/>
                <button type="submit" ng-hide="companyForm.companyname.$invalid" ng-disabled="companyForm.$invalid"
                        class="btn btn-sm btn-success btn-block">
                    <span class="glyphicon"></span> Создать компанию
                </button>
            </fieldset>
        </form>

        <form class="col-md-3" name="taskForm" ng-submit="processForm('newtask')" ng-init="addTask=false">
            <fieldset>
                <legend>Запланировать задачу</legend>
                <input type="checkbox" ng-model="addTask">Добавить задачу к новой сделке
                <div ng-show="addTask">
                    <label>Период</label>
                    <select class="form-control" name="taskperiod" ng-model="formData.taskperiod">
                        <option>Сегодня</option>
                        <option>Завтра</option>
                        <option>Послезавтра</option>
                    </select>
                    <label>или выбрать дату</label>
                    <input class="form-control" type="datetime-local" name="taskduedate"
                           ng-model="formData.taskduedate" required>
                    <br/>
                    <label>Ответственный</label>
                    <select class="form-control" name="taskuser" ng-model="formData.taskuser" required>
                        <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>
                    </select>
                    <br/>
                    <label>Тип задачи</label>
                    <select class="form-control" name="tasktype" ng-model="formData.tasktype">
                        <option ng-repeat="taskType in taskTypes" value="{{taskType}}">{{taskType}}</option>
                    </select>
                    <br/>
                    <label>Текст задачи</label>
                    <textarea class="form-control textarea" name="taskcomment"
                              ng-model="formData.taskcomment"></textarea>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="row">
        <button ng-disabled="dealForm.$invalid" type="submit" ng-click="processForm('newdeal')"
                class="btn btn-lg btn-primary btn-block">Создать сделку
        </button>
    </div>
</div>
</body>
</html>
