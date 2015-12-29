<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/bootstrap.css' %>
    <%@include file='../css/dealspyramid.css' %>
</style>
<!DOCTYPE html>
<html>
<head>
    <title>Воронка</title>
</head>
<body>
<div class="col-lg-2">
    <jsp:include page="menu.jsp"/>
</div>
<div class="col-lg-10">
    <div class="row">
        <button class="btn btn-success" onclick="location.href= '/dealspyramid'">Воронка</button>
        <button class="btn btn-default" onclick="location.href= '/dealslist'">Список</button>
    </div>
    <div class="row">
        <div class="col-md-3 border ">
            <p style="text-align: center">Фильтры</p>
            <form action="/dealspyramid" method="post">
                <select class="form-control" name="selectedfilter" title="Стандартные фильтры">
                    <option value="" disabled selected>Выберите фильтр</option>
                    <option value="open">Открытые сделки</option>
                    <option value="my">Только мои сделки</option>
                    <option value="success">Успешно завершенные</option>
                    <option value="fail">Нереализованные сделки</option>
                    <option value="notask">Сделки без задач</option>
                    <option value="expired">Сделки c просроченными задачами</option>
                    <option value="deleted">Удаленные</option>
                    <c:forEach items="${filters}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select><br/>
                <button class="btn btn-primary" type="submit" name="action" value="applyfilter">Применить фильтр
                </button>
                <br/><br/>
                <input class="form-control" type="text" name="name" placeholder="Название фильтра"/>
                <select class="form-control" id="when" name="when" title="Когда">
                    <option value="" disabled selected>Когда</option>
                    <c:forEach items="${filterperiod}" var="item">
                        <option value="${item}">${item.toString()}</option>
                    </c:forEach>
                </select><br>
                <p>Дата с</p>
                <input type="date" class="form-control" name="date_from" min="2015-12-01"><br/>
                <p>Дата до</p>
                <input type="date" class="form-control" name="date_to" max="2020-01-01"><br>
                <select class="form-control" name="phase">
                    <option value="" disabled selected>Этапы</option>
                    <c:forEach items="${deals_map}" var="item">
                        <option value="${item.key.id}">${item.key.name}</option>
                    </c:forEach>
                </select><br>
                <select class="form-control" name="managers">
                    <option value="" disabled selected>Менеджеры</option>
                    <c:forEach items="${contacts}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select><br>
                <select class="form-control" name="tasks" title="Задачи">
                    <option value="" disabled selected>Задачи</option>
                    <c:forEach items="${filtertask}" var="item">
                        <option value="${item}">${item.toString()}</option>
                    </c:forEach>
                </select><br>
                <input class="form-control" type="text" name="tags" placeholder="Теги"/><br>
                <button class="btn btn-default" type="submit" name="action" value="savefilter">Сохранить фильтр</button>
            </form>

        </div>
        <div class="col-md-9 border">


            <c:forEach items="${deals_map}" var="entry">
                <c:set var="total" value="${0}"/>
                <fieldset class="col-xs-2 border" id="${entry.key.name}" style="background-color: ${entry.key.color}">
                    <legend class="h6 text-uppercase">${entry.key.name}</legend>
                    <c:forEach items="${entry.value}" var="deal">
                        <c:set var="total" value="${total + deal.budget}"/>
                    </c:forEach>
                    <p class="text-center">${fn:length(entry.value)} сделок: ${total}USD</p>
                    <c:forEach items="${entry.value}" var="deal">
                        <div class="rectangle">
                            <p><a href="http://www.w3schools.com/html/">${deal.name}</a></p>
                            <p>${deal.budget}$</p>
                            <p>${deal.dealCompany.name}</p>
                            <c:forEach items="${deal.contacts}" var="contact">
                                <a>${contact}</a>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </fieldset>
            </c:forEach>

        </div>

    </div>

</div>

</body>
</html>
