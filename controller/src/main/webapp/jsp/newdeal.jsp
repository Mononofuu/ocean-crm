<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/newdeal.css' %>
</style>
<script>
    <%@include file="../js/files.js"%>
</script>
<!DOCTYPE html>
<html>
<title>Добавить сделку</title>
</head>
<body>
<form action="/deal" method="post">
    <fieldset>
        <legend>Добавить сделку</legend>
        <input class="input" type="text" name="dealname" placeholder="Название сделки"/>
        <br/>
        <input class="input" type="text" name="dealtags" placeholder="Теги"/>
        <br/>
        <select class="input" name="dealresp">
            <option value="" disabled selected>Ответственный</option>
            <c:forEach items="${contacts}" var="item">
                <option value="${item.id}">${item.name}</option>
            </c:forEach>
        </select>
        <br/>
        <input class="input" type="text" name="dealbudget" placeholder="Бюджет" pattern="[0-9]*"
               title="Цифра с плавающей точкой"/>
        <br/>
        <select class="input" name="dealphase">
            <option value="" disabled selected>Этап</option>
            <c:forEach items="${phases}" var="item">
                <option value="${item.id}">${item.name}</option>
            </c:forEach>
        </select>
        <br/>
        <textarea class="input textarea" name="dealcomment" placeholder="Примечание по сделке"></textarea>
        <br/>

        <div id="selectedFiles"></div>
        <br/>
        <input class="input" type="file" name="dealfiles" id="files" multiple><br/>
    </fieldset>


    <fieldset>
        <legend>Добавить контакт</legend>
        <div id="contactscroller">
            <c:if test="${empty dealcontacts}">
                Сделка без контактов
            </c:if>
            <c:if test="${not empty dealcontacts}">
                <c:forEach items="${dealcontacts}" var="item">
                    <div id="contactitem">
                        <a href="../index.jsp"><c:out value="${item.name}"/></a>
                        <br/>
                        <button type="button">Открепить контакт</button>
                    </div>
                </c:forEach>
            </c:if>
            <br/>
        </div>
        <br/>
        <select class="input" name="dealcontact">
            <option value="" disabled selected>Добавить из существующих</option>
            <c:forEach items="${contacts}" var="item">
                <option value="${item.id}">${item.name}</option>
            </c:forEach>
        </select>
        <br/>
        <h5 class="center">или добавить новый</h5>
        <input class="input" type="text" name="contactname" placeholder="Имя Фамилия"/>
        <br/>
        <select class="input" name="contactcompany">
            <option value="" disabled selected>Выбрать компанию</option>
            <c:forEach items="${companies}" var="item">
                <option value="${item.id}">${item.name}</option>
            </c:forEach>
        </select>
        <br/>
        <input class="input" type="text" name="contactposition" placeholder="Должность (Название должности)"/>
        <br/>
        <select class="input" name="contactphonetype" id="contacttype">
            <option value="" disabled selected>Тип</option>
            <c:forEach items="${phoneTypes}" var="item">
                <option value="${item}">${item}</option>
            </c:forEach>
        </select>
        <input class="input" type="text" id="contactnumber" name="contactphonenumber" placeholder="Номер телефона"/>
        <br/>
        <input class="input" type="text" name="contactemail" placeholder="Email"/>
        <br/>
        <input class="input" type="text" name="contactskype" placeholder="Skype"/>
        <br/>
        <button type="submit" name="action" value="newcontact">Создать контакт</button>
    </fieldset>

    <fieldset>
        <legend>Добавить компанию</legend>
        <select class="input" name="dealcompany">
            <option value="" disabled selected>Выбрать компанию</option>
            <c:forEach items="${companies}" var="item">
                <option value="${item.id}">${item.name}</option>
            </c:forEach>
        </select>
        <br/>
        <h5 class="center">или добавить новую</h5>
        <input class="input" type="text" name="companyname" placeholder="Название компании"/>
        <br/>
        <input class="input" type="tel" name="companyphone" placeholder="Телефон">
        <br/>
        <input class="input" type="email" name="companyemail" placeholder="Email">
        <br/>
        <input class="input" type="url" name="companysite" placeholder="Web-адрес">
        <br/>
        <textarea class="input textarea" name="companyaddress" placeholder="Адрес"></textarea>
        <br/>
        <button type="submit" name="action" value="newcompany">Создать компанию</button>
    </fieldset>

    <fieldset>
        <legend>Запланировать задачу</legend>
        <select class="input" name="taskperiod">
            <option value="" disabled selected>Период</option>
            <c:forEach items="${list.resp}" var="item">
                <option><c:out value="${item}"/></option>
            </c:forEach>
        </select>
    <br/>
        <h5 class="center">или выбрать</h5>
        <input class="input" type="datetime-local" name="taskduetime">
        <br/>
        <select class="input">
            <option value="" disabled selected>Ответственный</option>
            <c:forEach items="${list.resp}" var="item">
                <option><c:out value="${item}"/></option>
            </c:forEach>
        </select>
        <br/>
        <select class="input">
            <option value="" disabled selected>Тип задачи</option>
            <c:forEach items="${list.resp}" var="item">
                <option><c:out value="${item}"/></option>
            </c:forEach>
        </select>
        <br/>
        <textarea class="input textarea" placeholder="Текст задачи"></textarea>
        <br/>
        <button type="submit">Создать задачу</button>
    </fieldset>
    <br/>

    <div id="submit">
        <button class="button" type="submit" name="action" value="newdeal">Добавить</button>
        <button class="button" type="button" onclick="location.href = '../index.jsp'">Отмена</button>
    </div>
</form>
</body>
</html>
