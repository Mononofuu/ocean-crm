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
    <%@include file='../css/dealslist.css' %>
</style>

<html>
<link href="../css/dealslist.css" rel="stylesheet" type="text/css">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/smoothness/jquery-ui.css"/>
    <script type="text/javascript">
        $(function () {
            $("#datepicker").datepicker({
                inline: true,
                language: 'ru',
                changeYear: true,
                changeMonth: true
            });
        });
        $(function () {
            $("#datepicker2").datepicker({
                inline: true,
                language: 'ru',
                changeYear: true,
                changeMonth: true
            });
        });
    </script>

</head>
<body>

<divbutton>
    <table>
        <tr>
            <td><input class="button" type="submit" value="Воронка"></td>
            <td><input class="button list" type="submit" value="Список"></td>
        </tr>
    </table>
</divbutton>

<div class="row">
    <div class="tablename"><b><h2>Отображение сделок</h2></b></div>
    <div class="tablename center"><b><h1>Список</h1></b></div>
    <div class="row2">
        <form method="POST" action='dealedit' name="frmDealEdit">
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
<%--
                                    <td><input type="submit" name="frmDealEdit" value=${deal.id}>${deal.name}</td>
--%>
                                    <td>${deal.name}</td>
                                    <td>${deal.mainContact.name}</td>
                                    <td>${deal.mainContact.company.name}</td>
                                    <td>${deal.status.name}</td>
                                    <td>${deal.budget} ${deal.currency.name}</td>
                                </tr>
                            </c:forEach>

                            </tbody>

                        </table>
                        </form>
                    </div>
                    <div class="row3">

                        <form method="POST" action='dealslist' name="frmDealsListFilter">

                        <div class="tablename companyadd center"><b><h2>Фильтры</h2></b></div>
                        <div class="frame rightframe">

                       <select class="field" name="selectedfilter" title="Стандартные фильтры" size="7">
                            <option value="" disabled selected>Выберите фильтр</option>
                            <option value="open">Открытые сделки</option>
                            <option value="my">Только мои сделки</option>
                            <option value="success">Успешно завершенные</option>
                            <option value="fail">Нереализованные сделки</option>
                            <option value="notask">Сделки без задач</option>
                            <option value="expired">Сделки c просроченными задачами</option>
                            <option value="deleted">Удаленные</option>
                        </select>

                        <div class="interval">
                             <input class="period" type="text" id="datepicker" name="datebegin">
                             <input class="period" type="text" id="datepicker2" name="dateend">
                        </div>

                        <select class="field" name="dealstatus">
                            <option value="" disabled selected>Все этапы</option>
                            <c:forEach items="${deals_statuses}" var="status">
                                <option value=${status.id}>${status.name}</option>
                            </c:forEach>
                        </select>

                        <select class="field" name="user">
                            <option value="" disabled selected><b>Все менеджеры</b></option>
                            <c:forEach items="${users}" var="user">
                                <option value=${user.id}>${user.name}</option>
                            </c:forEach>
                        </select>
                        <select class="field" name="tasks">
                            <option value="" disabled selected>Задачи</option>
                            <option value="">Не учитывать</option>
                            <option value="">На сегодня</option>
                            <option value="">На завтра</option>
                            <option value="">На этой неделе</option>
                            <option value="">В этом месяце</option>
                            <option value="">В этом квартале</option>
                            <option value="">Нет задач</option>
                            <option value="">Просрочены</option>
                        </select>


                        <input class="field tags" type="text" name="tags" placeholder="Теги">
                        <input class="field button" type="submit" value="Сохранить">

                        </div>
                        </form>
                    </div>


                </div>


                </body>
                </html>
