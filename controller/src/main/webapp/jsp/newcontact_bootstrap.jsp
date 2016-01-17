<!DOCTYPE html>
<!--
@author Anton Sakhno <sakhno83@gmail.com>
-->
<!--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
-->
<html>
<head>
    <title>Создание контакта</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="../jsp/menu.jsp"/>
    <link href="../resources/css/tasklist.css" rel="stylesheet" type="text/css"/>

    <script src="../resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../resources//js/tasklist.js" type="text/javascript"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/smoothness/jquery-ui.css"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.css"/>

    <link href="../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="../resources/css/bootstrap-datetimepicker.min.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <form class="form-horizontal" action="/new_contact_add" method="get" enctype="multipart/form-data"
              id="newcontactform">
        <div class="col-xs-4 autowindow">

                <h4>Создание контакта</h4>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input class="form-control" type="text" name="name" id="name" placeholder="Имя Фамилия">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input class="form-control" type="text" name="tags" id="tags" placeholder="Теги">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <select class="form-control" name="responsible" id="responsible">
                            <option value="" disabled selected>Ответственный</option>
                            <c:forEach var="user" items="${userslist}">
                                <option value="${user.id}">${user.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input class="form-control" type="text" name="position" id="position" placeholder="Должность">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-7">
                        <input class="form-control" type="text" name="phonenumber" id="phonenumber"
                               placeholder="Номер телефона">
                    </div>
                    <div class="col-sm-5">
                        <select class="form-control" name="phonetype" id="phonetype">
                            <c:forEach var="phonetype" items="${phonetypelist}">
                                <option value="${phonetype}">${phonetype.toString()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input class="form-control" type="text" name="email" id="email" placeholder="Email">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input class="form-control" type="text" name="skype" id="skype" placeholder="Skype">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <textarea class="form-control" name="notes" id="notes" placeholder="Примечание к контакту"
                                  rows="4"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <input type="file" min="1" max="10" name="file[]" id="file" multiple="true">
                    </div>
                </div>
                <input class="btn btn-primary" type="submit" value="Добавить" id="addcontact">
                <input class="btn resetbutton" type="reset" value="Сбросить" id="resetcontact">

        </div>
        <div class="col-xs-4">
            <div class="col-sm-12 autowindow">
                    <h4>Быстрое добавление сделки</h4>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input class="form-control" type="text" name="newdealname" id="newdealname" placeholder="Название сделки">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <select class="form-control" name="dealtype" id="dealtype">
                                <option value="" disabled selected>Этап</option>
                                <c:forEach var="dealstatus" items="${dealstatuses}">
                                    <option value="${dealstatus.id}">${dealstatus.toString()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-9">
                            <input class="form-control" type="text" name="budget" id="budget" placeholder="Бюджет">
                        </div>
                        <div class="col-sm-2">
                            <label for="budget">Гр.</label>
                        </div>
                    </div>
                    <input class="btn btn-primary" type="submit" value="Добавить" id="adddeal">
            </div>
            <div class="col-sm-12 autowindow">
                    <h4>Запланировать действие</h4>
                    <div class="form-group small-gutter">
                        <div class="col-sm-4">
                            <select class="form-control" name="period" id="period">
                                <option value="today">Сегодня</option>
                                <option value="allday">Весь день</option>
                                <option value="tomorow">Завтра</option>
                                <option value="nextweek">Следующая неделя</option>
                                <option value="nextmonth">Следующий месяц</option>
                                <option value="nextyear">Следующий год</option>
                            </select>
                        </div>
                        <div class="col-sm-1">
                            <label for="period">или</label>
                        </div>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" id="datepicker" name="duedate" placeholder="дата">
                        </div>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" id="timepicker" name="duetime" placeholder="время">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <select class="form-control" name="taskresponsible" id="taskresponsible">
                                <option value="" disabled selected>Ответственный</option>
                                <c:forEach var="user" items="${userslist}">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <select class="form-control" name="tasktype" id="tasktype">
                                <option value="" disabled selected>Тип задачи</option>
                                <c:forEach var="tasktype" items="${tasktypes}">
                                    <option value="${tasktype.ordinal()}">${tasktype.toString()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea class="form-control" name="tasktext" id="tasktext" placeholder="Текст задачи"
                                      rows="2"></textarea>
                        </div>
                    </div>
                    <input class="btn btn-primary" type="submit" value="Добавить" id="addtask">
            </div>
        </div>
        </form>
        <div class="col-xs-2 autowindow">
            <div class="row">
                <div class="form-group">
                    <label for="companyselect">Выбрать компанию</label>
                    <select class="form-control" id="companyselect" name="companyid" form="newcontactform">
                        <option value="" disabled selected>Выбор Компании</option>
                        <c:forEach var="company" items="${companylist}">
                            <option value="${company.id}">${company.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row">
                <form method="post" id="newcompanyform" action="/new_company">
                    <div class="form-group">
                        <label for="newcompanyname">Создайть новую компанию</label>
                        <input class="form-control" type="text" name="newcompanyname" id="newcompanyname"
                               placeholder="Название компании">
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="text" name="newcompanyphone" id="newcompanyphone"
                               placeholder="Телефон">
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="text" name="newcompanyemail" id="newcompanyemail"
                               placeholder="Email">
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="text" name="newcompanywebaddress" id="newcompanywebaddress"
                               placeholder="Web-адрес">
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="text" name="newcompanyaddress" id="newcompanyaddress"
                               placeholder="Адрес">
                    </div>
                    <input name="submit" class="btn btn-primary" type="submit" value="Добавить Компанию">
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>