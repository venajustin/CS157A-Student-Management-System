<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<%@ include file="../head-info.jsp" %>

<body>
<div id="header">
  <h1>
      Course Management - Your Sections
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
        Your Sections:
    </h3>
      <div hx-get="${pageContext.request.contextPath}/api/professor/schedule" hx-trigger="load"></div>

      <form hx-post="${pageContext.request.contextPath}/api/professor/selectsection" hx-target="#student-list">
          <label>
              Select Section by ID:
              <input type="number" name="sectionid"/> <br>
              <button type="submit">Select</button>
          </label>
      </form>

      <div id="student-list">

      </div>

  </div>
</div>

</body>
</html>