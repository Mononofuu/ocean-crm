<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    <%@include file='css/newcontact.css' %>
</style>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Создание контакта</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/smoothness/jquery-ui.css"/>
    <%--<link href="newcontact.css" rel="stylesheet" type="text/css"/>--%>
    <script type="text/javascript">
        $(function () {
            $("#datepicker").datepicker();
        });
    </script>
</head>
<body>
<form action="newContactServlet" method="get" enctype="multipart/form-data">


    <div class="tablename">Создание контакта</div>
    <div class="frame">
        <input class="field" type="text" name="name" placeholder="Имя Фамилия">
        <input class="field tags" type="text" name="tags" placeholder="Теги">
        <select class="field" name="responsible">
            <option value="">Ответственный</option>
            <option value="value1">Пользователь1</option>
            <option value="value2">Пользователь2</option>
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
    </div>

    <div class="tablename">Примечания</div>
    <div class="frame">
        <textarea class="field bigtext" name="notes" placeholder="Примечание к контакту" rows="4"></textarea>

        <input class="field bigtext" type="file" min="1" max="10" name="file[]" multiple="true">
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
        <input class="period date" type="text" id="datepicker" name="date">
        <select class="period time" name="time">
            <option value="00:00">00:00</option>
            <option value="00:30">00:30</option>
            <option value="01:00">01:00</option>
            <option value="01:30">01:30</option>
            <option value="00:00">Надо переделать</option>
        </select>
        <select class="field" name="responsible">
            <option value="">Ответственный</option>
            <option value="value1">Пользователь1</option>
            <option value="value2">Пользователь2</option>
        </select>
        <select class="field" name="tasktype">
            <option value="">Тип задачи</option>
            <option value="followup">Follow-up</option>
            <option value="meeting">Встреча</option>
            <option value="other">Другой</option>
        </select>
        <textarea class="field bigtext" name="tasktext" placeholder="Текст задачи" rows="4"></textarea>
    </div>


    <div class="right">
        <select class="field tablename" name="companyname">
            <option value="">Выбор компании</option>
            <option value="company1">Компания1</option>
            <option value="company2">Компания2</option>
            <option value="company3">Компания3</option>
        </select>

        <div class="tablename companyadd">Добавить компанию</div>
        <div class="frame rightframe">
            <input class="field" type="text" name="newcompanyname" placeholder="Название компании">
            <input class="field" type="text" name="newcompanyphone" placeholder="Телефон">
            <input class="field" type="text" name="newcompanyemail" placeholder="Email">
            <input class="field" type="text" name="newcompanywebaddress" placeholder="Web-адрес">
            <input class="field" type="text" name="newcompanyaddress" placeholder="Адрес">
        </div>

        <div class="tablename">Быстрое добавление сделки</div>
        <div class="frame rightframe">
            <input class="field" type="text" name="newdialname" placeholder="Название сделки">
            <select class="field" name="dialtype">
                <option value="">Этап</option>
                <option value="firstcontact">Первичный контакт</option>
                <option value="makingdeсision">Принимают решение</option>
                <option value="contractdiscussion">Согласование договора</option>
                <option value="success">Успешно реализовано</option>
                <option value="fail">Закрыто и не реализовано</option>
            </select>
            <input class="field budget" type="number" name="budget" placeholder="Бюджет">

            <div class="budget">гр.</div>
        </div>

        <input class="frame rightframe button" type="submit" value="Добавить">

        <a class="frame rightframe button cancel" href="index.jsp">Отмена</a>
    </div>
</form>
</body>


</html>
