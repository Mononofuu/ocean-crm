<%--
  Created by IntelliJ IDEA.
  User: Peter
  Date: 26.12.2015
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
    <%@include file='../css/bootstrap.css' %>
</style>

<html>
<head>
    <title><spring:message code="label.commentedit"/></title>
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


        <legend><spring:message code="label.commentedit"/></legend>

        <input type="hidden" name="id" value="${comment.id}">
        <input type="hidden" name="subjectid" value="${subjectid}">
        <input type="hidden" name="backurl" value="${backurl}">

        <div class="form-group">
            <label class="col-md-2 control-label" for="commenttext"><spring:message code="label.comment"/></label>
            <div class="col-md-4">
                <input id="commenttext" name="commenttext" type="text" placeholder="" class="form-control input-md" required="" value="${comment.text}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="btn_comment_save"></label>
            <div class="col-md-4">
                <button id="btn_comment_save" name="btn_comment_save" class="btn btn-default"><spring:message code="label.save"/></button>
                <button id="btn_comment_cancel" name="btn_comment_cancel" class="btn btn-default" onclick="window.history.back()"><spring:message code="label.cancel"/></button>
            </div>
        </div>

    </fieldset>
</form>
</div>
</body>
</html>
