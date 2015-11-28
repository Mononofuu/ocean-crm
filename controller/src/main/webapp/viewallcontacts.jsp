<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<title>viewallcontacts</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha256-MfvZlkHCEqatNoGiOXveE8FIwMzZg4W85qfrfIFBfYc= sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ=="
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/viewallcontacts.js" type="text/javascript"></script>
<style>
    <%@include file='css/viewallcontacts.css' %>
</style>

<link type='text/css' href='css/viewallcontacts.css' rel='stylesheet' />
</head>
<body>
	<h4>CRM-Ocean</h4>
	<div id="header">
		<button type="submit" value="addcontact">Добавить контакт</button>
	</div>
	<div id="left">
		<div id="filter">
			<button type="submit" value="all">Все</button>
			<button type="submit" value="contacts">Контакты</button>
			<button type="submit" value="companies">Компании</button>
		</div>
		<select id="deffilt" name="defaultfilters" multiple size="4">
			<option value="fulllist">Польный список контактов</option>
			<option value="withouttasks">Контакты без задач</option>
			<option value="expiredtasks">Контакты с простроченными
				задачами</option>
			<option value="deletedtasks">Удаленные</option>
		</select> <br>
		<br>
		<br>
		<br>

		<p>Когда</p>
		<select name="periodfilter">
			<option value="alltime">За все время</option>
			<option value="fortoday">За сегодня</option>
			<option value="forthreedays">За 3 дня</option>
			<option value="forweek">За неделю</option>
			<option value="forquarter">За квартал</option>
			<option value="setperiod">Период</option>
		</select>
		<div id="radio">
			<input type="radio" name="one" value="created"> созданы <input
				type="radio" name="one" value="changed"> изменены
		</div>
		<p>Этапы</p>
		<a href="javascript://">Выбрать этапы</a>
		<div id="multiSelect">
			<span> <input type="checkbox" value="nodeals" /> Без сделок
			</span> <span> <input type="checkbox" value="noopendeals" /> Без
				отрытых сделок
			</span> <span> <input type="checkbox" value="primarycontact" />
				Первичный контакт
			</span> <span> <input type="checkbox" value="negotiations" />
				Переговоры
			</span> <span> <input type="checkbox" value="makingdecision" />
				Принимают решение
			</span> <span> <input type="checkbox" value="agreement" />
				Согласование договора
			</span> <span> <input type="checkbox" value="successrealize" />
				Успешно реализовано
			</span> <span> <input type="checkbox" value="closed" /> Закрыто
			</span> <span> <input type="checkbox" value="notrealized" /> Не
				реализовано
			</span>
		</div>
		<p>Менеджеры</p>
		<select name="managers">
			<option>Some manager</option>
		</select>
		<p>Задачи</p>
		<select name="tasksfilter">
			<option value="ignore">не учитывать</option>
			<option value="fortoday">на сегодня</option>
			<option value="fortommorow">на завтра</option>
			<option value="forthisweek">на этой неделе</option>
			<option value="forthismonth">в этом месяце</option>
			<option value="forthisquater">в этом квартале</option>
			<option value="notasks">нет задач</option>
			<option value="expired">просрочены</option>
		</select>
		<p>Теги</p>
		<select>
			<option>Some tag1</option>
			<option>Some tag2</option>
		</select>
		<button id="applybuttons" type="submit" name="apply">Применить</button>
		<button id="clearbuttons" type="submit" name="clear">Очистить</button>
	</div>
	<div id="viewallcontacts">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Наименование</th>
					<th>Компания</th>
					<th>Телефон</th>
					<th>E-mail</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Абрамов Абрам</td>
					<td>Дотнет</td>
					<td>+380444578954</td>
					<td>abrabr@dotnet.ua</td>
				</tr>
				<tr>
					<td>Иванов Иван</td>
					<td>Джава</td>
					<td>+380507894561</td>
					<td>ivoivo@javacom.ua</td>
				</tr>
				<tr>
					<td>Петров Петр</td>
					<td>Руби</td>
					<td>+380934567894</td>
					<td>pitpit@rubycom.ua</td>
				</tr>
				<tr>
					<td>Сидоров Сергей</td>
					<td>Питон</td>
					<td>+380787885689</td>
					<td>sidrs@pythoncom.ua</td>
				</tr>
				<tr>
					<td>Томсон Том</td>
					<td>Тим</td>
					<td>+445068454521</td>
					<td>tt@teamcom.com</td>
				</tr>
			</tbody>
		</table>
</body>
</html>

