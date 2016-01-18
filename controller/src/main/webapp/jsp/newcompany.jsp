<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/newcompany.css' %>
    <%@include file='../css/bootstrap.css' %>
</style>
<!DOCTYPE html>

<html ng-app="newCompany">

<head>
    <title>Добавить компанию</title>
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
                <legend>Создать компанию</legend>
                <label>Название компании</label>
                <input class="form-control" type="text" name="companyname" placeholder="Название компании"
                       ng-model="formData.companyname" required/>
                <br/>
                <label>Теги</label>
                <input class="form-control" type="text" name="companytags"
                       ng-model="formData.companytags"/>
                <br/>
                <label>Ответственный</label>
                <select title="Ответственный" class="form-control" name="companyresp" ng-model="formData.companyresp">
                    <option ng-repeat="user in users" value="{{user.id}}">{{user.name}}</option>
                </select>
                <label>Тип и номер телефона</label>
                <div class="form-inline">
                    <select class="form-control" style="padding-left: 1%; padding-right: 1%; width: 23%"
                            name="companyphonetype" ng-model="formData.companyphonetype">
                        <option ng-repeat="phoneType in phoneTypes" value="{{phoneType}}">{{phoneType}}</option>
                    </select>
                    <input class="form-control" type="tel" style="padding-left: 1%;width: 75%"
                           name="companyphone" placeholder="Номер телефона"
                           ng-model="formData.companyphone"/>
                </div>
                <br/>
                <label>Email</label>
                <input class="form-control" type="email" name="companyemail" placeholder="Email"
                       ng-model="formData.companyemail" required>
                <br/>
                <label>Web-адрес</label>
                <input class="form-control" type="url" name="companysite" placeholder="Web-адрес"
                       ng-model="formData.companysite">
                <br/>
                <label>Адрес</label>
                <textarea class="form-control textarea" name="companyaddress" placeholder="Адрес"
                          ng-model="formData.companyaddress"></textarea>
                <br/>
                <label>Примечание по компании</label>
                <textarea class="form-control textarea" name="companycomment"
                          placeholder="Примечание по компании"
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
                <label>Имя Фамилия</label>
                <input class="form-control" type="text" name="contactname" placeholder="Имя Фамилия"
                       ng-model="formData.contactname" required/>
                <br/>
                <label>Должность</label>
                <input class="form-control" type="text" name="contactposition"
                       placeholder="Должность (Название должности)"
                       ng-model="formData.contactposition"/>
                <br/>
                <label>Тип и номер телефона</label>
                <div class="form-inline">
                    <select class="form-control" style="padding-left: 1%; padding-right: 1%; width: 23%"
                            name="contactphonetype" ng-model="formData.contactphonetype">
                        <option ng-repeat="phoneType in phoneTypes" value="{{phoneType}}">{{phoneType}}</option>
                    </select>
                    <input class="form-control" style="padding-left: 1%;width: 75%" type="text"
                           name="contactphonenumber" placeholder="Номер телефона"
                           ng-model="formData.contactphonenumber"/>
                </div>
                <br/>
                <label>Email</label>
                <input class="form-control" type="email" name="contactemail" placeholder="Email"
                       ng-model="formData.contactemail"/>
                <br/>
                <label>Skype</label>
                <input class="form-control" type="text" name="contactskype" placeholder="Skype"
                       ng-model="formData.contactskype"/>
                <br/>
                <button type="submit" ng-hide="contactForm.contactname.$invalid" ng-disabled="contactForm.$invalid"
                        class="btn btn-sm btn-success btn-block">
                    <span class="glyphicon"></span> Создать контакт
                </button>
            </fieldset>
        </form>
        <form class="col-md-3" name="dealForm" ng-submit="addDeal()" novalidate>
            <fieldset>
                <legend>Добавить сделку</legend>
                <div ng-show="addedDeals.length>0">
                    <label>Количество сделок: {{addedDeals.length}}</label>
                    <label>Общая сумма: {{getTotal()}}</label>
                    <a ng-click="removeDeal(deal)" class="btn-info btn btn-block" ng-repeat="deal in addedDeals"
                    >Название:{{deal.name}}<br>Этап:{{deal.status}}<br>Бюджет:{{deal.budget}}</a>
                </div>
                <br/>
                <label>Название сделки</label>
                <input class="form-control" type="text" name="dealname" placeholder="Название сделки"
                       ng-model="formData.dealname" required/>
                <br/>
                <label>Этап</label>
                <select class="form-control" name="dealstatus" ng-model="formData.dealstatus" required>
                    <option ng-repeat="status in dealStatuses" value="{{status.id}}">{{status.name}}</option>
                </select>
                <br/>
                <label>Бюджет</label>
                <input class="form-control" type="text" name="dealbudget" placeholder="Бюджет" pattern="[0-9]*"
                       title="Цифра с плавающей точкой" ng-model="formData.dealbudget" required/>
                <button type="submit" ng-hide="dealForm.dealname.$invalid" ng-disabled="dealForm.$invalid"
                        class="btn btn-sm btn-success btn-block">
                    <span class="glyphicon"></span> Добавить сделку
                </button>
            </fieldset>
        </form>
        <form class="col-md-3" name="taskForm" ng-submit="processForm('newtask')" ng-init="addTask=false">
            <fieldset>
                <legend>Запланировать задачу</legend>
                <input type="checkbox" ng-model="formData.addTask">Добавить задачу к компании
                <div ng-show="formData.addTask">
                    <label>Период</label>
                    <select class="form-control" name="taskperiod" ng-model="formData.taskperiod">
                        <option value="today">Сегодня</option>
                        <option value="allday">Весь день</option>
                        <option value="tomorow">Завтра</option>
                        <option value="nextweek">Следующая неделя</option>
                        <option value="nextmonth">Следующий месяц</option>
                        <option value="nextyear">Следующий год</option>
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
                    <textarea class="form-control textarea" name="taskcomment" placeholder="Текст задачи"
                              ng-model="formData.taskcomment"></textarea>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="row">
        <button ng-disabled="companyForm.$invalid" type="submit" ng-click="processForm('newcompany')"
                class="btn btn-lg btn-primary btn-block">Создать компанию
        </button>
    </div>
</div>
</body>
</html>