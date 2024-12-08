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
        Email:
        <input name="email" placeholder="myemail@email.com" type="text"/>
      </label>
      <br>
      <label>
        Account Type:
        <select name="accounttype" onchange="changeacctype(this);">
          <option value="student">Student</option>
          <option value="professor">Professor</option>
        </select>
      </label>
      <br>
      <label id="dept-input" class="hide">
        Department:
        <input name="department" placeholder="MATH" type="text"/>
      </label>

      <label id="major-input">
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
