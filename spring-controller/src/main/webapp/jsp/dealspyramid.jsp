<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../resources/css/bootstrap.min.css' %>
    <%@include file='../resources/css/dealspyramid.css' %>
</style>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="label.pyramid"/></title>
</head>
<body>
<div class="col-lg-2">
    <jsp:include page="menu.jsp"/>
</div>
<div class="col-lg-10">
    <div class="row">
        <button class="btn btn-success" onclick="location.href= '/deals/pyramid'"><spring:message code="label.pyramid"/></button>
        <button class="btn btn-default" onclick="location.href= '/deals'"><spring:message code="label.list"/></button>
    </div>
    <div class="row">
        <div class="col-md-3 border ">
            <p style="text-align: center"><spring:message code="label.filters"/></p>
            <form action="/dealspyramid" method="post">
                <select class="form-control" name="selectedfilter" title=<spring:message code="label.filtersstandart"/>>
                    <option value="" disabled selected><spring:message code="label.filterselect"/></option>
                    <option value="open"><spring:message code="label.opendeals"/></option>
                    <option value="my"><spring:message code="label.mydeals"/></option>
                    <option value="success"><spring:message code="label.completeddeals"/></option>
                    <option value="fail"><spring:message code="label.unrealizeddeals"/></option>
                    <option value="notask"><spring:message code="label.dealswotasks"/></option>
                    <option value="expired"><spring:message code="label.dealswoverduetasks"/></option>
                    <option value="deleted"><spring:message code="label.deleted"/></option>
                    <c:forEach items="${filters}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select><br/>
                <button class="btn btn-primary" type="submit" name="action" value="applyfilter"><spring:message code="label.applyfilter"/>
                </button>
                <br/><br/>
                <input class="form-control" type="text" name="name" placeholder=<spring:message code="label.name"/>/>
                <select class="form-control" id="when" name="when" title=<spring:message code="label.when"/>>
                    <option value="" disabled selected><spring:message code="label.when"/></option>
                    <c:forEach items="${filterperiod}" var="item">
                        <option value="${item}">${item.toString()}</option>
                    </c:forEach>
                </select><br>
                <p><spring:message code="label.from"/>Дата с</p>
                <input type="date" class="form-control" name="date_from" min="2015-12-01"><br/>
                <p><spring:message code="label.to"/>Дата до</p>
                <input type="date" class="form-control" name="date_to" max="2020-01-01"><br>
                <select class="form-control" name="phase">
                    <option value="" disabled selected><spring:message code="label.phase"/></option>
                    <c:forEach items="${deals_map}" var="item">
                        <option value="${item.key.id}">${item.key.name}</option>
                    </c:forEach>
                </select><br>
                <select class="form-control" name="managers">
                    <option value="" disabled selected><spring:message code="label.responsible"/></option>
                    <c:forEach items="${managers}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select><br>
                <select class="form-control" name="tasks" title=<spring:message code="label.tasks"/>>
                    <option value="" disabled selected><spring:message code="label.tasks"/></option>
                    <c:forEach items="${filtertask}" var="item">
                        <option value="${item}">${item.toString()}</option>
                    </c:forEach>
                </select><br>
                <input class="form-control" type="text" name="tags" placeholder=<spring:message code="label.tags"/>/><br>
                <button class="btn btn-default" type="submit" name="action" value="savefilter"><spring:message code="label.save"/></button>
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
                    <p class="text-center"><spring:message code="label.dealcount"/>
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
</html>
