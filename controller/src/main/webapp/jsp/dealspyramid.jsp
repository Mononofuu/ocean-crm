<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/bootstrap.css' %>
    <%@include file='../css/dealspyramid.css' %>
</style>
<!DOCTYPE html>
<html>
<fmt:bundle basename="app">
    <head>
        <title><fmt:message key="pyramid"/></title>
    </head>
    <body>
    <div class="col-lg-2">
        <jsp:include page="menu.jsp"/>
    </div>
    <div class="col-lg-10">
        <div class="row">
            <button class="btn btn-success" onclick="location.href= '/dealspyramid'"><fmt:message
                    key="pyramid"/></button>
            <button class="btn btn-default" onclick="location.href= '/dealslist'"><fmt:message key="list"/></button>
        </div>
        <div class="row">
            <div class="col-md-3 border ">
                <p style="text-align: center"><fmt:message key="filters"/></p>
                <form action="/dealspyramid" method="post">
                    <select class="form-control" name="selectedfilter" title=<fmt:message key="filtersstandart"/>>
                        <option value="" disabled selected><fmt:message key="filterselect"/></option>
                        <option value="open"><fmt:message key="opendeals"/></option>
                        <option value="my"><fmt:message key="mydeals"/></option>
                        <option value="success"><fmt:message key="completeddeals"/></option>
                        <option value="fail"><fmt:message key="unrealizeddeals"/></option>
                        <option value="notask"><fmt:message key="dealswotasks"/></option>
                        <option value="expired"><fmt:message key="dealswoverduetasks"/></option>
                        <option value="deleted"><fmt:message key="deleted"/></option>
                        <c:forEach items="${filters}" var="item">
                            <option value="${item.id}">${item.name}</option>
                        </c:forEach>
                    </select><br/>
                    <button class="btn btn-primary" type="submit" name="action" value="applyfilter"><fmt:message
                            key="applyfilter"/>
                    </button>
                    <br/><br/>
                    <input class="form-control" type="text" name="name" placeholder=<fmt:message key="name"/>/>
                    <select class="form-control" id="when" name="when" title=<fmt:message key="when"/>>
                        <option value="" disabled selected><fmt:message key="when"/></option>
                        <c:forEach items="${filterperiod}" var="item">
                            <option value="${item}">${item.toString()}</option>
                        </c:forEach>
                    </select><br>
                    <p><fmt:message key="from"/>Дата с</p>
                    <input type="date" class="form-control" name="date_from" min="2015-12-01"><br/>
                    <p><fmt:message key="to"/>Дата до</p>
                    <input type="date" class="form-control" name="date_to" max="2020-01-01"><br>
                    <select class="form-control" name="phase">
                        <option value="" disabled selected><fmt:message key="phase"/></option>
                        <c:forEach items="${deals_map}" var="item">
                            <option value="${item.key.id}">${item.key.name}</option>
                        </c:forEach>
                    </select><br>
                    <select class="form-control" name="managers">
                        <option value="" disabled selected><fmt:message key="responsible"/></option>
                        <c:forEach items="${managers}" var="item">
                            <option value="${item.id}">${item.name}</option>
                        </c:forEach>
                    </select><br>
                    <select class="form-control" name="tasks" title=<fmt:message key="tasks"/>>
                        <option value="" disabled selected><fmt:message key="tasks"/></option>
                        <c:forEach items="${filtertask}" var="item">
                            <option value="${item}">${item.toString()}</option>
                        </c:forEach>
                    </select><br>
                    <input class="form-control" type="text" name="tags" placeholder=<fmt:message key="tags"/>/><br>
                    <button class="btn btn-default" type="submit" name="action" value="savefilter"><fmt:message
                            key="save"/></button>
                </form>

            </div>
            <div class="col-md-9 border">


                <c:forEach items="${deals_map}" var="entry">
                    <c:set var="total" value="${0}"/>
                    <fieldset class="col-xs-2 border" id="${entry.key.name}"
                              style="background-color: ${entry.key.color}">
                        <legend class="h6 text-uppercase">${entry.key.name}</legend>
                        <c:forEach items="${entry.value}" var="deal">
                            <c:set var="total" value="${total + deal.budget}"/>
                        </c:forEach>
                        <p class="text-center"><fmt:message key="dealcount"/>
                            - ${fn:length(entry.value)}: ${total}USD</p>
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
</fmt:bundle>
</html>
