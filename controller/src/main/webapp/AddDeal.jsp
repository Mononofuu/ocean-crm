<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Добавить сделку</title>
    <style>
        fieldset {
            background-color: #dddddd;
            border: solid;
            border-width: 2px;
            border-radius: 3px;
            width: 270px;
            padding: 10px;
            margin: 10px;
        }
        input {
            width: 250px;
            height: 30px;
            margin: 2px;
        }
        select {
            width: 250px;
            height: 30px;
            margin: 2px;
        }
        textarea {
            width: 250px;
            height: 70px;
            max-height: 300px;
            margin: 2px;
        }
        #contactscroller {
            overflow: auto;
            width: 250px;
            height: 100px;
        }
    </style>
</head>


<body style="background-color: #9a9a9a">
<p>Добавить сделку</p>

<form action="/deal" id="myForm" method="post" enctype="multipart/form-data">
    <div id="deal" style="float: left">
        <fieldset>
            <legend>Добавить сделку</legend>
            <input type="text" name="dealname" placeholder="Название сделки"/>
            <br/>
            <input type="text" name="dealtags" placeholder="Теги"/>
            <br/>
            <select name="dealresp">
                <option value="" disabled selected>Ответственный</option>
                <%--<c:forEach items="${list.resp}" var="item">--%>
                <%--<option><c:out value="${item}"/></option>--%>
                <%--</c:forEach>--%>
                <option value="6">TEst contact</option>
            </select>
            <br/>
            <input type="text" name="dealbudget" placeholder="Бюджет" pattern="[0-9]*.[0-9]{2}"
                   title="Цифра с плавающей точкой"/>
            <br/>
            <select name="dealphase">
                <option value="" disabled selected>Этап</option>
                <%--<c:forEach items="${list.phase}" var="item">--%>
                <%--<option><c:out value="${item}"/></option>--%>
                <%--</c:forEach>--%>
                <option value="1">Первичный контакт</option>
            </select>
            <br/>
            <textarea name="dealcomment" placeholder="Примечание по сделке"></textarea>
            <br/>

            <div id="selectedFiles"></div>
            <br/>
            <input type="file" name="dealfiles" name="files" multiple><br/>
        </fieldset>
    </div>


    <div id="contact" style="float: left">
        <fieldset>
            <legend>Добавить контакт</legend>
            <div id="contactscroller">
                <c:forEach items="${list.resp}" var="item">
                    <div style="border: solid; border-radius: 14px; border-color: black; border-width: medium">
                        <a href="index.jsp" style="text-align: center"><c:out value="${item}"/></a>
                        <br/>
                        <button type="button">Открепить контакт</button>
                    </div>
                </c:forEach>
                <br/>
            </div>
            <button type="button">Добавить контакт</button>
            <button type="button">Отменить</button>
        </fieldset>
    </div>


    <div id="company" style="float: left">
        <fieldset>
            <legend>Добавить компанию</legend>
            <select>
                <option value="" disabled selected>Выбрать компанию</option>
                <c:forEach items="${list.resp}" var="item">
                    <option><c:out value="${item}"/></option>
                </c:forEach>
            </select>
            <br/>
            <h5 style="text-align: center">или выбрать</h5>
            <br/>
            <input type="text" placeholder="Название компании"/>
            <br/>
            <input type="tel" name="tel" placeholder="Телефон">
            <br/>
            <input type="email" name="email" placeholder="Email">
            <br/>
            <input type="url" name="website" placeholder="Web-адрес">
            <br/>
            <textarea placeholder="Адрес"></textarea>
        </fieldset>
    </div>


    <div id="task" style="float: left">
        <fieldset>
            <legend>Запланировать действие</legend>
            <select>
                <option value="" disabled selected>Период</option>
                <c:forEach items="${list.resp}" var="item">
                    <option><c:out value="${item}"/></option>
                </c:forEach>
            </select>
            <br/>
            <h5 style="text-align: center">или выбрать</h5>
            <br/>
            <input type="datetime-local" name="datetime">
            <br/>
            <select>
                <option value="" disabled selected>Ответственный</option>
                <c:forEach items="${list.resp}" var="item">
                    <option><c:out value="${item}"/></option>
                </c:forEach>
            </select>
            <br/>
            <select>
                <option value="" disabled selected>Тип задачи</option>
                <c:forEach items="${list.resp}" var="item">
                    <option><c:out value="${item}"/></option>
                </c:forEach>
            </select>
            <br/>
            <textarea placeholder="Текст задачи"></textarea>
        </fieldset>
    </div>

    <button type="submit">Добавить</button>
    <br/>
    <button type="button">Отмена</button>


    <script>
        var selDiv = "";
        document.addEventListener("DOMContentLoaded", init, false);
        function init() {
            document.querySelector('#files').addEventListener('change', handleFileSelect, false);
            selDiv = document.querySelector("#selectedFiles");
        }
        function handleFileSelect(e) {
            if (!e.target.files) return;
            selDiv.innerHTML = "";
            var files = e.target.files;
            for (var i = 0; i < files.length; i++) {
                var f = files[i];
                selDiv.innerHTML += f.name + "<br/>";
            }
        }
    </script>

</form>
</body>
</html>