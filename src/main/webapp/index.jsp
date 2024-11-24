<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <script src="https://unpkg.com/htmx.org@2.0.3"></script>
    <link rel="stylesheet" type="text/css" href="static/style.css"/>
</head>
<body>
<h1>DB connection demo
</h1>
<br/>
    Current count: <span id="count-span"></span>
    <button hx-post="${pageContext.request.contextPath}/increment-test" hx-target="#count-span" hx-swap="innerHTML">Increment</button>
</body>
</html>