<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Добавление новой компании в систему">
    <title>Добавление новой компании</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="http://harvesthq.github.io/chosen/chosen.jquery.js"></script>
</head>
<body class="myBackground">

<p/>
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">Добавить компанию</div>
                    <div class="panel-body">
                        <form>
                            <div class="form-group">
                                <label>Название компании</label>
                                <input type="text" class="form-control" placeholder="Введите название...">
                            </div>
                            <%@include file="bootstrap-chosen-master/choose-user-for-company.jsp"%>
                            <div class="form-group" >
                                <label>Номер телефона</label>
                                <div class="form-inline">
                                    <input type="text" class="form-control" id="phoneNumber" placeholder="+7 - (___) - ___ - __ - __">
                                    <select class="form-control" id="phoneType">
                                        <option>Рабочий</option>
                                        <option>2</option>
                                        <option>3</option>
                                        <option>4</option>
                                        <option>5</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Email адрес</label>
                                <input type="email" class="form-control" placeholder="ВВедите email...">
                            </div>
                            <div class="form-group">
                                <label>Web-адрес</label>
                                <input type="text" class="form-control" placeholder="Введите web-адрес...">
                            </div>
                            <div class="form-group">
                                <label>Адрес</label>
                                <input type="text" class="form-control" placeholder="Введите адрес...">
                            </div>
                            <div class="form-group">
                                <label>Примечания</label>
                                <textarea class="form-control" rows="3" placeholder="Введите текст..."></textarea>
                            </div>
                            <div class="form-group">
                                <label>Прикрепить файл</label>
                                <input type="file">
                            </div>
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                                    Сохранить
                            </button>
                            <button type="submit" class="btn btn-default">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    Отмена
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">Добавить сделки</div>
                    <div class="panel-body">
                        <div class="form-group">
                            <label>Сделок: <span class="badge">5</span> на сумму: <span class="badge">5000$</span> </label>
                            <select multiple class="form-control">
                                <option>Сделка1, Этап 1, Сумма 1000</option>
                                <option>Сделка2, Этап 1, Сумма 1000</option>
                                <option>Сделка3, Этап 1, Сумма 1000</option>
                                <option>Сделка4, Этап 1, Сумма 1000</option>
                                <option>Сделка5, Этап 1, Сумма 1000</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Быстрое добавление сделки</label>
                            <input type="text" class="form-control" placeholder="Введите название сделки...">
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Первичный контакт</option>
                                <option>Переговоры</option>
                                <option>3</option>
                                <option>4</option>
                                <option>5</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">Запланировать действие</div>
                    <div class="panel-body">
                        Panel content
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">Добавить контакты</div>
                    <div class="panel-body">
                        Panel content
                    </div>
                </div>
            </div>
        </div>
    </div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
