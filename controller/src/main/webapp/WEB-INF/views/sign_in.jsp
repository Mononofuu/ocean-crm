<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
    <body>
        login page
        <form method="post">
            Name: <input type="text" name="login"/>
            Password: <input type="password" name="password"/>
            <input type="submit"/>
        </form>
        <%
            List<String> errors  = (List<String>)request.getAttribute("errors");
            if (errors != null && errors.size() > 0){ %>
        <ul>
            <%
                for (String error: errors) {
             %>
                 <li><%=error%></li>
            <%
                }
            %>
        </ul>
           <% }%>
    </body>
</html>
