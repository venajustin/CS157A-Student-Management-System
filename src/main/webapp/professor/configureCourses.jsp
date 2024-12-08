<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<%@ include file="../head-info.jsp" %>

<body>
<div id="header">
  <h1>
      Course Management - Add or Update Courses
  </h1>
  <span class="header-name">
            Logged in as:
            <span hx-trigger="load" hx-get="${pageContext.request.contextPath}/api/username">
            </span>

            <button HX-Post="${pageContext.request.contextPath}/api/accounts/logout">Logout</button>
        </span>

</div>
<div id="page-container">
  <%@ include file="./sidebar.jsp"%>
  <div id="content">

    <h3>
        Current Schedule:
    </h3>
      <div hx-get="${pageContext.request.contextPath}/api/student/schedule" hx-trigger="load"></div>

  </div>
</div>

</body>
</html>