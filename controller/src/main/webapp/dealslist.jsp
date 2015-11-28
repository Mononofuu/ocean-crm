<%--
  Created by IntelliJ IDEA.
  User: kramar
  Date: 19.11.15
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
    <%@include file='dealslist.css' %>
</style>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
</head>
<body>

<divbutton>
    <table>
        <tr>
            <td><input class="button" type="submit" value="Воронка"></td>
            <td><input class="button" type="submit" value="Список"></td>
        </tr>
    </table>
</divbutton>

<div class="row">
    <div class="tablename"><b>Отображение сделок</b></div>
    <div class="tablename">Список</div>
    <div class="row2">
        <table class="table">
            <thead>
            <tr>
                <th width="150">Название сделки</th>
                <th width="150">Основной контакт</th>
                <th width="150">Компания контакта</th>
                <th width="150">Этап сделки</th>
                <th width="150">Бюджет</th>
            </tr>
            </thead>
            <tbody>


            <c:forEach items="${deals}" var="deal">
                <tr>
                    <td>${deal.name}</td>
                    <td>${deal.mainContact.name}</td>
                    <td>${deal.mainContact.company.name}</td>
                    <td>${deal.status.name}</td>
                    <td>${deal.budget} ${deal.currency.name}</td>
                </tr>
            </c:forEach>

            </tbody>

        </table>
    </div>
    <div class="row3">
        <div class="tablename">Фильтры</div>
        <select class="field" name="deal_status_" size="7">
            <option value="deal_open=">Открытые сделки</option>
            <option value="deal_my">Только мои сделки</option>
            <option value="deal_finished=">Успешно завершенные</option>
            <option value="deal_failed=">Нереализованные сделки</option>
            <option value="deal_without_task">Сделки без задач</option>
            <option value="deal_time_passed">Сделки с просроченными задачами</option>
            <option value="deal_deleted">Удаленные</option>
        </select>


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
        <select class="field" name="stages">
            <option value="">Этапы</option>
            <option value="value1">Этап1</option>
            <option value="value2">Этап2</option>
        </select>
        <select class="field" name="responsible">
            <option value="">Менеджеры</option>
            <option value="value1">Пользователь1</option>
            <option value="value2">Пользователь2</option>
        </select>
        <select class="field" name="tasks">
            <option value="">Задачи</option>
            <option value="value1">Задача1</option>
            <option value="value2">Задача2</option>
        </select>


        <input class="field tags" type="text" name="tags" placeholder="Теги">
        <td><input class="button" type="submit" value="Сохранить"></td>

    </div>


</div>


</body>
</html>
