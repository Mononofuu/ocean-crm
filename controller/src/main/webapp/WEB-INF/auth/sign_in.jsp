<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CRM_OCEAN - Login page</title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/crm-ocean.css" rel="stylesheet">
</head>
<body>
<div class="container">

    <form class="form-signin" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only">Email address</label>
        <input name="login" type="text" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button><button class="btn btn-lg btn-primary btn-block" onclick="location.href='/registration'" type="button">Registration</button>

        <%
        List<String> errors  = (List<String>)request.getAttribute("errors");
        if (errors != null && errors.size() > 0){ %>

            <%
                for (String error: errors) {
            %>
            <div class="alert alert-warning"><%=error%></div>
            <%
                }
            %>
            <% }%>

    </form>

</div>
</body>
</html>
