<%--
  Author: Vladislav Lybachevskiy
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CRM_OCEAN - Dashboard</title>
    <link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<div class="row-fluid">
    <div style="float:left;width:80%;height: 100%;">
        <fieldset class="scheduler-border">
            <legend class="scheduler-border"><strong>Виджеты</strong></legend>
            <div class="row-fluid">
                <div id="widgetsInLeft" style="float:left;width:50%;height: auto;">
                    <div id="widgetDeals">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"><strong>Сделки</strong></legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;">Всего сделок:</div>
                                <div style="float: right;alignment: right;">5000</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Бюджет:</div>
                                <div style="float: right;alignment: right;">2000000</div>
                            </div>
                        </fieldset>
                    </div>
                    <div id="widgetDealsWithoutTasks">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"><strong>Сделки без задач</strong></legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;">Сделок без задач:</div>
                                <div style="float: right;alignment: right;">50</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Сделок с задачами:</div>
                                <div style="float: right;alignment: right;">20</div>
                            </div>
                        </fieldset>
                    </div>
                    <div id="widgetSuccessDeals">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"><strong>Успешные сделки</strong></legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;">Успешных сделок:</div>
                                <div style="float: right;alignment: right;">50</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Нереализовано:</div>
                                <div style="float: right;alignment: right;">20</div>
                            </div>
                        </fieldset>
                    </div>

                </div>
                <div id="widgetsIntRight" style="float:left;width:50%;height: auto;">
                    <div id="widgetTasksInProgressAndClosed">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"><strong>Задачи в работе и выполненые</strong></legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;">Задачи в работе:</div>
                                <div style="float: right;alignment: right;">5000</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Выполненные:</div>
                                <div style="float: right;alignment: right;">2000</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Просроченные:</div>
                                <div style="float: right;alignment: right;">200</div>
                            </div>
                        </fieldset>
                    </div>
                    <div id="widgetAllContactsAllCompanies">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"><strong>Всего контактов и Всего компаний</strong></legend>
                            <div class="row-fluid" style="width: 100%;">
                                <div style="float: left;alignment: left;">Контактов:</div>
                                <div style="float: right;alignment: right;">50</div>
                            </div>
                            <br>

                            <div class="row-fluid" style="width: 100%;">
                                <div style="float:left; alignment: left;">Компаний:</div>
                                <div style="float: right;alignment: right;">20</div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
    <div style="float:left;width:20%;height: 100%;">
        <fieldset class="scheduler-border" style="height: auto;">
            <legend class="scheduler-border"><strong>Последние события</strong></legend>
            <fieldset class="scheduler-border">
                <small><a href="">2015-11-22 15:30</a><br>
                    <a href="">Vladislav</a><br>
                    Добавление нового контакта:<br>
                    <a href="">Vasian Petrovich</a><br></small>
            </fieldset>
            <fieldset class="scheduler-border">
                <small><a href="">2015-11-22 15:30</a><br>
                    <a href="">Vladislav</a><br>
                    Добавление нового контакта:<br>
                    <a href="">Vasian Petrovich</a><br></small>
            </fieldset>
            <fieldset class="scheduler-border">
                <small><a href="">2015-11-22 15:30</a><br>
                    <a href="">Vladislav</a><br>
                    Добавление нового контакта:<br>
                    <a href="">Vasian Petrovich</a><br></small>
            </fieldset>
            <fieldset class="scheduler-border">
                <small><a href="">2015-11-22 15:30</a><br>
                    <a href="">Vladislav</a><br>
                    Добавление нового контакта:<br>
                    <a href="">Vasian Petrovich</a><br></small>
            </fieldset>
            <fieldset class="scheduler-border">
                <small><a href="">2015-11-22 15:30</a><br>
                    <a href="">Vladislav</a><br>
                    Добавление нового контакта:<br>
                    <a href="">Vasian Petrovich</a><br></small>
            </fieldset>
            <fieldset class="scheduler-border">
                <small><a href="">2015-11-22 15:30</a><br>
                    <a href="">Vladislav</a><br>
                    Добавление нового контакта:<br>
                    <a href="">Vasian Petrovich</a><br></small>
            </fieldset>

        </fieldset>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>
