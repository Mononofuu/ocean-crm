<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CRM_OCEAN - Registration page</title>
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="../resources/css/crm-ocean.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form class="form-horizontal" role="form" method="post" enctype="multipart/form-data">
        <div>
            <h2>Registration Form</h2>
            <div class="form-group">
                <label for="firstName" class="col-sm-3 control-label">User Name</label>
                <div class="col-sm-9">
                    <input name="userName" type="text" id="firstName" placeholder="Full Name" class="form-control"
                           autofocus>
                </div>
            </div>
            <div class="form-group">
                <label for="email" class="required col-sm-3 control-label">Login</label>
                <div class="col-sm-9">
                    <input name="login" type="text" id="login" placeholder="Email" class="form-control"
                           required="required">
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="required col-sm-3 control-label">Password</label>
                <div class="col-sm-9">
                    <input name="password" type="password" id="password" placeholder="Password" class="form-control"
                           required="required">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label" for="photo">Photo</label>
                <div class="col-sm-9">
                    <input id="photo" name="photo" type="file">
                </div>
            </div>
            <div class="form-group">
                <label for="email" class="col-sm-3 control-label">Email</label>
                <div class="col-sm-9">
                    <input name="email" type="email" id="email" placeholder="Email" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="phoneMob" class="col-sm-3 control-label">Phone mob.</label>
                <div class="col-sm-9">
                    <input name="phoneMob" type="phone" id="phoneMob" placeholder="Phone mob." class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="phoneWork" class="col-sm-3 control-label">Phone work</label>
                <div class="col-sm-9">
                    <input name="phoneWork" type="tel" id="phoneWork" placeholder="Phone work" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="lang" class="col-sm-3 control-label">Language</label>
                <div class="col-sm-9">
                    <select name="language" id="lang" class="form-control">
                        <option value=""></option>
                        <option value="EN">English</option>
                        <option value="RU">Rus</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="comment" class="col-sm-3 control-label">Comment</label>
                <div class="col-sm-9">
                    <input name="comment" type="text" id="comment" placeholder="Comment" class="form-control" autofocus>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-9 col-sm-offset-3">
                    <button type="submit" class="btn btn-primary btn-block">Register</button>
                </div>
            </div>
        </div>
        <div>
            <%
                List<String> errors  = (List<String>)request.getAttribute("error");
                if (errors != null && errors.size() > 0){ %>
            <ul>
                <%
                    for (String error: errors) {
                %>
                <li><div class="alert alert-warning">
                        <%=error%></div></li>
                <%
                    }
                %>
            </ul>
            <% }%>
        </div>
    </form>
</div>

</body>
</html>
