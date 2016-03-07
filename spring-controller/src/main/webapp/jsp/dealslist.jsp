<%--
  Created by IntelliJ IDEA.
  User: kramar
  Date: 19.11.15
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
    <%@include file='../resources/css/bootstrap.min.css' %>
    <%@include file='../resources/css/bootstrap-theme.min.css' %>
</style>

<html>
<!--<link href="../css/dealslist.css" rel="stylesheet" type="text/css">-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="label.dealslist"/></title>
    <jsp:include page="menu.jsp" />
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


        <div class="form-group">
            <button class="btn btn-default" onclick="location.href= '/deals/pyramid'"><spring:message code="label.pyramid"/></button>
            <button class="btn btn-default" onclick="location.href= '/deals/add'"><spring:message code="label.adddeal"/></button>
        </div>



    <form action="/dealslist" method="post" class="well col-xs-3">
        <h3><spring:message code="label.filters"/></h3>
        <div class="form-group">
            <select class="form-control" name="selectedfilter" title=<spring:message code="label.filtersstandart"/> size="8">
                <option value="" selected><spring:message code="label.filterswithoutstandart"/></option>
                <option value="open"><spring:message code="label.opendeals"/></option>
                <option value="my"><spring:message code="label.mydeals"/></option>
                <option value="success"><spring:message code="label.completeddeals"/></option>
                <option value="fail"><spring:message code="label.unrealizeddeals"/></option>
                <option value="notask"><spring:message code="label.dealswotasks"/></option>
                <option value="expired"><spring:message code="label.dealswoverduetasks"/></option>
                <option value="deleted"><spring:message code="label.deleted"/></option>
            </select>
        </div>

        <div class="form-group">
            <input class="form-control" type="text" name="name" placeholder=<spring:message code="label.filtername"/>/>
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
            <input type="text" class="form-control" name="tags" placeholder=<spring:message code="label.tags"/>>
        </div>

        <div class="form-group">
            <button class="btn btn-default" type="submit" name="action" value="applyfilter"><spring:message code="label.applyfilter"/></button>
            <button class="btn btn-default" type="submit" name="action" value="savefilter"><spring:message code="label.save"/></button>
        </div>

    </form>

    <div class="table-responsive col-xs-7">
        <h3><spring:message code="label.dealslist"/></h3>
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
            <tr>
                <th><spring:message code="label.dealname"/></th>
                <th><spring:message code="label.maincontact"/></th>
                <th><spring:message code="label.company"/></th>
                <th><spring:message code="label.dealphasename"/></th>
                <th><spring:message code="label.budget"/></th>
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

</div>

</body>
</html>
