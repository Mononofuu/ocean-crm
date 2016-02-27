<!DOCTYPE html>
<!--
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
-->
<html>
<head>
	<title><spring:message code="label.contacts"/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="menu.jsp" />
	<link href="../resources/css/tasklist.css" rel="stylesheet" type="text/css"/>

	<script src="../resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="../resources/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="../resources//js/tasklist.js" type="text/javascript"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>


	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/smoothness/jquery-ui.css"/>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.8.7/jquery.timepicker.css"/>

	<link href="../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="../resources/css/bootstrap-datetimepicker.min.css" />

	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.1/css/select2.min.css" rel="stylesheet" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.1/js/select2.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-3 window">
				<h4><spring:message code="label.filters"/></h4>
				<form class="form-horizontal" action="/contacts" method="post">
					<div class="form-group">
						<div class="col-sm-12">
							<select class="form-control" size="4" name="filtername" id="filtername">
								<option value="allcontacts" selected><spring:message code="label.fullcontactlist"/></option>
								<option value="tasklesscontacts"><spring:message code="label.contactwithouttasks"/></option>
								<option value="overduetaskcontacts"><spring:message code="label.contactswithoverduetasks"/></option>
								<option value="dellitedcontacts"><spring:message code="label.deleted"/></option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="formlable">
							<label for="datepicker" class="control-label"><spring:message code="label.when"/></label>
						</div>
						<div class="col-sm-7">
							<select class="form-control" name="period" id="period">
								<option value="all"><spring:message code="label.duringalltime"/></option>
								<option value="today"><spring:message code="label.forallday"/></option>
								<option value="3days"><spring:message code="label.for3days"/></option>
								<option value="week"><spring:message code="label.forweek"/></option>
								<option value="month"><spring:message code="label.formonth"/></option>
								<option value="quarter"><spring:message code="label.forquartal"/></option>
								<option value="period"><spring:message code="label.period"/></option>
							</select>
						</div>
						<div class="col-sm-5">
							<input class="form-control" type="text" id="datepicker" name="duedate" placeholder="<spring:message code="label.date"/>">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<label class="radio-inline"><input type="radio" name="periodtype" value="created" checked="checked"><spring:message code="label.created"/></label>
							<label class="radio-inline"><input type="radio" name="periodtype" value="updated"><spring:message code="label.edited"/></label>
						</div>
					</div>
					<div class="form-group col-sm-12">
						<div class="formlable">
							<label class="control-label">Этапы</label>
						</div>
						<select class="multipleselect col-sm-12" multiple="multiple" name="dealfilters" id="dealfilters">
							<option value="withoutdeal"><spring:message code="label.withoutdeals"/></option>
							<option value="withoutopendeal"><spring:message code="label.withoutopendeals"/></option>
							<option value="firstcontact"><spring:message code="label.dealphase.primary"/></option>
							<option value="discussion"><spring:message code="label.dealphase.conversation"/></option>
							<option value="takingdesision"><spring:message code="label.dealphase.makedecision"/></option>
							<option value="agreement"><spring:message code="label.dealphase.approvalcontract"/></option>
							<option value="success"><spring:message code="label.dealphase.success"/></option>
							<option value="closedandnotrealised"><spring:message code="label.dealphase.closedandnotimpl"/></option>
						</select>
					</div>
					<div class="form-group">
						<div class="col-sm-10">
							<label for="users"><spring:message code="label.managers"/></label>
							<select class="form-control" name="user" id="users">
								<option value=""><spring:message code="label.notspecified"/></option>
								<c:forEach var="user" items="${users}">
									<option value="${user.id}">${user.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="formlable">
							<label for="tasks" class="control-label"><spring:message code="label.tasks"/></label>
						</div>
						<div class="col-sm-12">
							<select class="form-control" name="tasks" id="tasks">
								<option value=""><spring:message code="label.notspecified"/></option>
								<option value="today"><spring:message code="label.fortoday"/></option>
								<option value="tomorow"><spring:message code="label.fortomorow"/></option>
								<option value="thisweek"><spring:message code="label.thisweek"/></option>
								<option value="thismonth"><spring:message code="label.thismonth"/></option>
								<option value="thisquoter"><spring:message code="label.label.thisquartal"/></option>
								<option value="notasks"><spring:message code="label.notasks"/></option>
								<option value="overdue"><spring:message code="label.overduetasks"/></option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<label for="tags"><spring:message code="label.tags"/></label>
							<select class="multipleselect col-sm-12" multiple="multiple" name="tags" id="tags">
								<c:forEach var="tag" items="${tags}">
									<option value="${tag.id}">${tag.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<input class="btn btn-primary" type="submit" value="<spring:message code="label.submit"/>">
					<input class="btn resetbutton" type="reset" value="<spring:message code="label.reset"/>">
				</form>
			</div>
			<div class="col-md-7">
				<ul class="nav nav-pills">
					<li class="active"><a href="#all" data-toggle="tab"><spring:message code="label.all"/></a></li>
					<li><a href="#contacts" data-toggle="tab"><spring:message code="label.contacts"/></a></li>
					<li><a href="#companies" data-toggle="tab"><spring:message code="label.companyes"/></a></li>
					<a class="btn btn-success addtask" href="/contacts/add"><spring:message code="label.addcontact"/></a>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active container-fluid" id="all">
						<table id="tableData1" class="table table-striped">
							<thead>
							<tr>
								<th><spring:message code="label.name"/></th>
								<th><spring:message code="label.company"/></th>
								<th><spring:message code="label.phonenumber"/></th>
								<th><spring:message code="label.email"/></th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="contact" items="${contactlist}">
								<tr>
									<td><a href="#">${contact.name}</a></td>
									<td>${contact.company.name}</td>
									<td>${contact.phone}</td>
									<td>${contact.email}</td>
								</tr>
							</c:forEach>
							<c:forEach var="company" items="${companylist}">
								<tr>
									<td><a href="#">${company.name}</a></td>
									<td></td>
									<td>${company.phoneNumber}</td>
									<td>${company.email}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="tab-pane container-fluid" id="contacts">
						<table id="tableData2" class="table table-striped">
							<thead>
							<tr>
								<th><spring:message code="label.name"/></th>
								<th><spring:message code="label.company"/></th>
								<th><spring:message code="label.phonenumber"/></th>
								<th><spring:message code="label.email"/></th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="contact" items="${contactlist}">
								<tr>
									<td><a href="#">${contact.name}</a></td>
									<td>${contact.company.name}</td>
									<td>${contact.phone}</td>
									<td>${contact.email}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="tab-pane container-fluid" id="companies">
						<table id="tableData3" class="table table-striped">
							<thead>
							<tr>
								<th><spring:message code="label.name"/></th>
								<th><spring:message code="label.phonenumber"/></th>
								<th><spring:message code="label.email"/></th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="company" items="${companylist}">
								<tr>
									<td><a href="#">${company.name}</a></td>
									<td>${company.phoneNumber}</td>
									<td>${company.email}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="/resources/js/pagination.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			$('#tableData1').paging({limit: 13});
			$('#tableData2').paging({limit: 13});
			$('#tableData3').paging({limit: 13});
		});
	</script>
</body>
</html>

