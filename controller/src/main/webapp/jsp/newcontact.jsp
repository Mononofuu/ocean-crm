<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<style>
    <%@include file='../resources/css/newcontact.css' %>
</style>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Создание контакта</title>
    <jsp:include page="../jsp/menu.jsp"/>
    <link rel="import" href="../jsp/menu.jsp"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/smoothness/jquery-ui.css"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.css"/>

    <link href="../resources/css/newcontact.css" rel="stylesheet" type="text/css"/>
    <script src="../resources//js/tasklist.js" type="text/javascript"></script>
</head>
<body>
<div>
    <form action="/new_contact_add" method="get" enctype="multipart/form-data">
        <div class="floatleft">
            <div class="tablename">Создание контакта</div>
            <div class="frame">
                <input class="field" type="text" name="name" placeholder="Имя Фамилия">
                <input class="field tags" type="text" name="tags" placeholder="Теги">
                <select class="field" name="responsible">
                    <option value="" disabled selected>Ответственный</option>
                    <c:forEach var="user" items="${userslist}">
                        <option value="${user.id}">${user.name}</option>
                    </c:forEach>
                </select>
                <input class="field" type="text" name="position" placeholder="Должность">
                <input class="field phone" type="text" name="phonenumber" placeholder="Номер телефона">
                <select class="field phonetype" name="phonetype">
                    <option value="WORK_PHONE_NUMBER">Рабочий</option>
                    <option value="WORK_DIRECT_PHONE_NUMBER">Раб.прямой</option>
                    <option value="MOBILE_PHONE_NUMBER">Мобильный</option>
                    <option value="FAX_NUMBER">Факс</option>
                    <option value="HOME_PHONE_NUMBER">Домашний</option>
                    <option value="OTHER_PHONE_NUMBER">Другой</option>
                </select>
                <input class="field" type="text" name="email" placeholder="Email">
                <input class="field" type="text" name="skype" placeholder="Skype">
                <textarea class="field bigtext" name="notes" placeholder="Примечание к контакту" rows="4"></textarea>
                <input class="field bigtext" type="file" min="1" max="10" name="file[]" multiple="true">
                <input class="button" type="submit" value="Добавить">
                <a class="button cancel" href="../index.jsp">Отмена</a>
            </div>
        </div>

        <div class="floatleft">
            <div class="tablename">Быстрое добавление сделки</div>
            <div class="frame">
                <input class="field bigtext" type="text" name="newdealname" placeholder="Название сделки">
                <select class="field" name="dealtype">
                    <option value="">Этап</option>
                    <option value="firstcontact">Первичный контакт</option>
                    <option value="makingdeсision">Принимают решение</option>
                    <option value="contractdiscussion">Согласование договора</option>
                    <option value="success">Успешно реализовано</option>
                    <option value="fail">Закрыто и не реализовано</option>
                </select>
                <input class="field budget" type="number" name="budget" placeholder="Бюджет">

                <div class="budget">гр.</div>
                <a class="button addbutton" href="#1">Добавить</a>
            </div>
            <div class="tablename">Запланировать действие</div>
            <div class="frame">
                <select class="field period" name="period">
                    <option value="today">Сегодня</option>
                    <option value="allday">Весь день</option>
                    <option value="tomorow">Завтра</option>
                    <option value="nextweek">Следующая неделя</option>
                    <option value="nextmonth">Следующий месяц</option>
                    <option value="nextyear">Следующий год</option>
                </select>

                <div class="period smaltext">или выбрать</div>
                <input class="period date" type="text" id="datepicker" name="duedate" placeholder="дата">
                <input class="period time" type="text" id="timepicker" name="duetime" placeholder="время">
                <select class="field" name="taskresponsible">
                    <option value="" disabled selected>Ответственный</option>
                    <c:forEach var="user" items="${userslist}">
                        <option value="${user.id}">${user.name}</option>
                    </c:forEach>
                </select>
                <select class="field" name="tasktype">
                    <option value="" disabled selected>Тип задачи</option>
                    <option value="FOLLOW_UP">Follow-up</option>
                    <option value="MEETING">Встреча</option>
                    <option value="OTHER">Другой</option>
                </select>
                <textarea class="field bigtext" name="tasktext" placeholder="Текст задачи" rows="4"></textarea>
                <a class="button addbutton" href="#2">Добавить</a>
            </div>
        </div>

    <div class="floatleft">
        <select class="companyselect" id="companyselect" name="companyid">
            <option value="" disabled selected>Выбор Компании</option>
            <c:forEach var="company" items="${companylist}">
                <option value="${company.id}">${company.name}</option>
            </c:forEach>
        </select>
    </div>
    </form>
    <div class="floatleft">
        <div class="tablename companyadd">Добавить компанию</div>
        <form method="post" id="newcompanyform" action="/new_company">
            <div class="floatleft frame rightframe" id="newcompanyframe">
                <input class="field" type="text" name="newcompanyname" id="newcompanyname" placeholder="Название компании">
                <input class="field" type="text" name="newcompanyphone" id="newcompanyphone" placeholder="Телефон">
                <input class="field" type="text" name="newcompanyemail" id="newcompanyemail" placeholder="Email">
                <input class="field" type="text" name="newcompanywebaddress" id="newcompanywebaddress" placeholder="Web-адрес">
                <input class="field" type="text" name="newcompanyaddress" id="newcompanyaddress" placeholder="Адрес">
                <input name="submit" class="button addbutton" type="submit" value="Добавить Компанию">
            </div>
        </form>
    </div>
</div>

</body>


</html>
