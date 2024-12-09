<%--
  Created by IntelliJ IDEA.
  User: venaj
  Date: 11/27/2024
  Time: 7:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<%@ include file="../head-info.jsp"%>
<body>

<div id="header">
    <h1>
        Sign in
    </h1>
</div>
<div class="index-page-content">
    <div class="center-box">
<form hx-post="${pageContext.request.contextPath}/api/accounts/login"
      hx-swap="innerHTML"
      hx-target="#account-error"
>
    <label>
        Email:
        <input name="email" placeholder="myemail@email.com" type="text"/>
    </label>
    <br>
    <label>
        Password:
        <input name="password" type="password"/>
    </label>
    <br>
    <button type="submit">Login</button>
</form>
<div id="account-error">
</div>
    </div>
</div>

</body>
</html>
