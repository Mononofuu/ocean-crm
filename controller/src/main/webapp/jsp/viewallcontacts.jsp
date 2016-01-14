<!DOCTYPE html>
<!--
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
-->
<html>
<head>
	<title>Контакты</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="../jsp/menu.jsp" />
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
				<h4>Фильтры</h4>
				<form class="form-horizontal" action="contactlist" method="post">
					<div class="form-group">
						<div class="col-sm-12">
							<select class="form-control" size="4" name="filtername" id="filtername">
								<option value="allcontacts" selected>Полный список контактов</option>
								<option value="tasklesscontacts">Контакты без задач</option>
								<option value="overduetaskcontacts">Контакты с просроченными задачами</option>
								<option value="dellitedcontacts" selected>Удаленные</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="formlable">
							<label for="datepicker" class="control-label">Когда</label>
						</div>
						<div class="col-sm-7">
							<select class="form-control" name="period" id="period">
								<option value="all">За все время</option>
								<option value="today">За сегодня</option>
								<option value="3days">За 3 дня</option>
								<option value="week">За неделю</option>
								<option value="month">За месяц</option>
								<option value="quarter">За квартал</option>
								<option value="period">Период</option>
							</select>
						</div>
						<div class="col-sm-5">
							<input class="form-control" type="text" id="datepicker" name="duedate" placeholder="дата">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<label class="radio-inline"><input type="radio" name="periodtype" value="created" checked="checked">созданы</label>
							<label class="radio-inline"><input type="radio" name="periodtype" value="edited">изменены</label>
						</div>
					</div>
					<div class="form-group col-sm-12">
						<div class="formlable">
							<label class="control-label">Этапы</label>
						</div>
						<select class="multipleselect col-sm-12" multiple="multiple" name="dealfilters" id="dealfilters">
							<option value="withoutdeal">Без сделок</option>
							<option value="withoutopendeal">Без открытых сделок</option>
							<option value="firstcontact">Первичный контакт</option>
							<option value="discussion">Переговоры</option>
							<option value="takingdesision">Принимают решение</option>
							<option value="agreement">Согласование договора</option>
							<option value="success">Успешно реализованно</option>
							<option value="closedandnotrealised">Закрыто и не реализованно</option>
						</select>
					</div>
					<div class="form-group">
						<div class="col-sm-10">
							<label for="users">Менеджеры</label>
							<select class="form-control" name="user" id="users">
								<c:forEach var="user" items="${users}">
									<option value="user.id">${user.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="formlable">
							<label for="tasks" class="control-label">Задачи</label>
						</div>
						<div class="col-sm-12">
							<select class="form-control" name="tasks" id="tasks">
								<option value="donotmention">Не учитывать</option>
								<option value="today">На сегодня</option>
								<option value="tomorow">На завтра</option>
								<option value="thisweek">На  этой неделе</option>
								<option value="thismonth">В этом месяце</option>
								<option value="thisquoter">В этом квартале</option>
								<option value="notasks">Нет задач</option>
								<option value="overdue">Просроченны</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<label for="users">Теги</label>
							<select class="multipleselect col-sm-12" multiple="multiple">
								<c:forEach var="tag" items="${tags}">
									<option value="tag.id">${tag.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<input class="btn btn-primary" type="submit" value="Применить">
					<input class="btn resetbutton" type="reset" value="Сбросить">
				</form>
			</div>
			<div class="col-md-7">
				<ul class="nav nav-pills">
					<li class="active"><a href="#all" data-toggle="tab">Все</a></li>
					<li><a href="#contacts" data-toggle="tab">Контакты</a></li>
					<li><a href="#companies" data-toggle="tab">Компании</a></li>
					<a class="btn btn-success addtask" href="new_contact_prepare">Добавить контакт</a>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active container-fluid" id="all">
						<table id="tableData1" class="table table-striped">
							<thead>
							<tr>
								<th>Наименование</th>
								<th>Компания</th>
								<th>Телефон</th>
								<th>E-mail</th>
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
								<th>Наименование</th>
								<th>Компания</th>
								<th>Телефон</th>
								<th>E-mail</th>
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
								<th>Наименование</th>
								<th>Телефон</th>
								<th>E-mail</th>
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

	<script type="text/javascript" src="/js/pagination.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			$('#tableData1').paging({limit: 13});
			$('#tableData2').paging({limit: 13});
			$('#tableData3').paging({limit: 13});
		});
	</script>
</body>
</html>

