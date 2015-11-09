<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello from here</title>
</head>
<body>
    hi page <%=((String)request.getAttribute("message"))%>
</body>
</html>
