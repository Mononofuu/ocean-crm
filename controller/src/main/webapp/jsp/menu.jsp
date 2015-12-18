<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<style>
    <%@include file='../resources/css/menu.css' %>
</style>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Меню</title>
    <link href="../resources/css/menu.css" rel="stylesheet" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
    <script src="../resources/js/menu.js"></script>
</head>
<body>
        <ul class="menu">
            <li> 
                <a class="logo" href="#1">CRM-OCEAN</a>
            </li>
            <li>
                <a href="#2">Рабочий стол</a>
            </li>
            <li>
                <a href="../dealslist?action=add">Сделки</a>
            </li>
            <li>
                <a href="#4">Контакты</a>
            </li>
            <li>
                <a href="#5">Задачи</a>
            </li>
            <li> 
                <a href="#6">Аналитика</a>
            </li>
            <li>
                <a href="#7">Настройки</a>
                <ul class="sub-menu">
                    <li><a class="header" href="#">Настройки</a></li>
                    <li><a href="#">Общие настройки</a></li>
                    <li><a href="#">Этапы продаж</a></li>
                    <li><a href="#">Пользователи и права</a></li>
                    <li><a href="#">Редакторы полей</a></li>
                    <li><a href="#">Бизнес процессы</a></li>
                    <li><a href="#">История посещений</a></li>
                    <li><a href="#">Безопасность</a></li>
                </ul>
            </li>
        </ul>
</body>
</html>
