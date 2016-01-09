<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 26.12.2015
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/bootstrap.css' %>
</style>

<html>
<head>
    <title>Редактирование примечания</title>
</head>
<body>

<jsp:useBean id="comment" type="com.becomejavasenior.Comment" scope="request">
<jsp:setProperty name="comment" property="*" />
</jsp:useBean>

<div class="col-xs-2">
    <jsp:include page="menu.jsp"/>
</div>
<div class="col-xs-10">

<form class="form-horizontal" action="commentedit?action=update"  method="post">
    <fieldset>


        <legend>Редактирование примечания</legend>

        <input type="hidden" name="id" value="${comment.id}">
        <input type="hidden" name="subjectid" value="${subjectid}">
        <input type="hidden" name="backurl" value="${backurl}">

        <div class="form-group">
            <label class="col-md-2 control-label" for="commenttext">Текст</label>
            <div class="col-md-4">
                <input id="commenttext" name="commenttext" type="text" placeholder="" class="form-control input-md" required="" value="${comment.text}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="btn_comment_save"></label>
            <div class="col-md-2">
                <button id="btn_comment_save" name="btn_comment_save" class="btn btn-default">Записать</button>
                <button id="btn_comment_cancel" name="btn_comment_cancel" class="btn btn-default" onclick="window.history.back()">Отмена</button>
            </div>
            <div class="col-md-2">
            </div>
        </div>

    </fieldset>
</form>
</div>
</body>
</html>
