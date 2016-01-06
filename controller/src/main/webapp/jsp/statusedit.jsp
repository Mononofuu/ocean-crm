<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/bootstrap.css' %>
</style>
<!DOCTYPE html>
<html>
<head>
    <title>Edit status</title>
</head>
<body>
<div class="col-lg-2">
    <jsp:include page="menu.jsp"/>
</div>
<div class="col-lg-10">
    <section>
        <h3>Edit status</h3>
        <hr>
        <jsp:useBean id="status" type="com.becomejavasenior.DealStatus" scope="request"/>
        <form method="post" action="deal_status?action=edit">
            <input type="hidden" name="id" value="${status.id}">
            <dl>
                <dt>Name:</dt>
                <dd><input type="text" value="${status.name}" name="name"></dd>
            </dl>
            <dl>
                <dt>Color:</dt>
                <dd>
                    <select name="color" title="Цвет">
                        <option value="${status.color}" disabled selected
                                style="background-color: ${status.color}"></option>
                        <option value="#0040ff" style="background-color: #0040ff"></option>
                        <option value="#7f00ff" style="background-color: #7f00ff"></option>
                        <option value="#ffff00" style="background-color: #ffff00"></option>
                        <option value="#80ff00" style="background-color: #80ff00"></option>
                        <option value="#00ff00" style="background-color: #00ff00"></option>
                        <option value="#ff0000" style="background-color: #985f0d"></option>
                        <option value="#ff0000" style="background-color: #8b211e"></option>
                        <option value="#ff0000" style="background-color: #2aabd2"></option>
                        <option value="#ff0000" style="background-color: #28a4c9"></option>
                        <option value="#ff0000" style="background-color: #419641"></option>
                        <option value="#ff0000" style="background-color: #800080"></option>
                        <option value="#ff0000" style="background-color: #8a6d3b"></option>
                        <option value="#ff0000" style="background-color: #a24230"></option>
                        <option value="#ff0000" style="background-color: #232452"></option>
                        <option value="#ff0000" style="background-color: #425324"></option>
                        <option value="#ff0000" style="background-color: #113532"></option>
                        <option value="#ff0000" style="background-color: #880028"></option>
                        <option value="#ff0000" style="background-color: #000099"></option>
                        <option value="#ff0000" style="background-color: #00fff0"></option>
                    </select>
                </dd>
            </dl>
            <button type="submit" class="btn btn-success">Save</button>
            <button class="btn btn-default" onclick="window.history.back()">Cancel</button>
        </form>
    </section>
</div>
</body>
</html>
