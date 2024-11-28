<%--
  Created by IntelliJ IDEA.
  User: venaj
  Date: 11/27/2024
  Time: 7:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--<%@ include file="../head-info.jsp"%>--%>
<head>
  <title>Student Management</title>
  <script src="https://unpkg.com/htmx.org@2.0.3"></script>
  <script src="https://unpkg.com/htmx-ext-response-targets@2.0.0/response-targets.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style.css"/>
  <script src="${pageContext.request.contextPath}/static/main.js"></script>
</head>

<body>

<div id="header">
  <h1>
      Create an Account
  </h1>
</div>

    <form hx-post="${pageContext.request.contextPath}/api/accounts/signup"
          hx-swap="innerHTML"
          hx-target="#account-error"
    >
      <label>
        Your Name:
        <input name="name" placeholder="Firstname Lastname" type="text" />
      </label>
      <br>
      <label>
        Password:
        <input name="password" type="password"/>
      </label>
      <br>
      <label>
        Repeat Password:
        <input name="password2" type="password"/>
      </label>
      <br>
      <label>
        Personal Email: (optional)
        <input name="email" placeholder="myemail@email.com" type="text"/>
      </label>
      <br>
      <label>
        Major of Study:
        <input name="major" placeholder="Computer Science" type="text"/>
      </label>
      <br>
      <button type="submit">Create Account</button>
    </form>
    <div id="account-error">
    </div>

</body>
</html>
