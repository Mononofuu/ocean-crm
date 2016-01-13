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
    <%@include file='../css/bootstrap.css' %>
    <%@include file='../css/bootstrap-theme.css' %>
</style>

<html>
<!--<link href="../css/dealslist.css" rel="stylesheet" type="text/css">-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Список сделок</title>
    <jsp:include page="../jsp/menu.jsp" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/smoothness/jquery-ui.css"/>
    <script type="text/javascript">
        $(function() {
            var dates = $( "#from, #to" ).datepicker({
                onSelect: function( selectedDate ) {
                    var option = this.id == "from" ? "minDate" : "maxDate",
                            instance = $( this ).data( "datepicker" ),
                            date = $.datepicker.parseDate(
                                    instance.settings.dateFormat ||
                                    $.datepicker._defaults.dateFormat,
                                    selectedDate, instance.settings );
                    dates.not( this ).datepicker( "option", option, date );
                }
            });
        });
    </script>

</head>

<body>



<div class="left-container">


    <form action="dealspyramid" class="form-inline col-xs-10">
        <div class="form-group">
            <input class="btn btn-default navbar-btn" type="submit" value="Воронка">
            <!--<input class="btn btn-default navbar-btn" type="submit" value="Список">-->
        </div>
    </form>


    <div class="table-responsive col-xs-7">
        <h3>Список сделок</h3>
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
        <tr>
            <th>Название сделки</th>
            <th>Основной контакт</th>
            <th>Компания</th>
            <th>Этап сделки</th>
            <th>Бюджет</th>
        </tr>
        </thead>
            <tbody>
            <c:forEach items="${deals}" var="deal">
                <tr>
                    <td><a href="/dealedit?action=edit&id=${deal.id}">${deal.name}</a></td>
                    <td>${deal.mainContact.name}</td>
                    <td>${deal.dealCompany.name}</td>
                    <td>${deal.status.name}</td>
                    <td>${deal.budget} ${deal.currency.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <form action="/dealslist" method="post" class="well col-xs-3">
        <h3>Фильтры</h3>
        <div class="form-group">
            <select class="form-control" name="selectedfilter" title="Стандартные фильтры" size="8">
                <option value="" selected>Без стандартных фильтров</option>
                <option value="open">Открытые сделки</option>
                <option value="my">Только мои сделки</option>
                <option value="success">Успешно завершенные</option>
                <option value="fail">Нереализованные сделки</option>
                <option value="notask">Сделки без задач</option>
                <option value="expired">Сделки c просроченными задачами</option>
                <option value="deleted">Удаленные</option>
            </select>
        </div>

        <div class="form-group">
            <button class="btn btn-default" type="submit" name="action" value="applyfilter">Применить фильтр</button>
        </div>

        <div class="form-group">
            <input class="form-control" type="text" name="name" placeholder="Название фильтра"/>
            <select class="form-control" id="when" name="when" title="Когда">
                <c:forEach items="${filterperiod}" var="item">
                    <option value="${item}">${item.toString()}</option>
                </c:forEach>
            </select><br>
        </div>

        <div class="form-group form-control">
            <div class="col-xs-6">
                <input class="form-control" type="text" id="from" name="date_from">
            </div>
            <div class="col-xs-6">
                <input class="form-control" type="text" id="to" name="date_to">
            </div>
        </div>

        <div class="form-group">
            <select class="form-control" name="phase">
                <option value="">Все этапы</option>
                <c:forEach items="${deals_map}" var="item">
                    <option value=${item.id}>${item.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <select class="form-control" name="manager">
                <option value=""><b>Все менеджеры</b></option>
                <c:forEach items="${managers}" var="manager">
                    <option value=${manager.id}>${manager.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <select class="form-control" name="tasks">
                <c:forEach items="${filtertask}" var="item">
                    <option value="${item}">${item.toString()}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <input type="text" class="form-control" name="tags" placeholder="Теги">
        </div>
        <div class="form-group">
            <button class="btn btn-default" type="submit" name="action" value="savefilter">Сохранить фильтр</button>
        </div>

    </form>

</div>

</body>
</html>
