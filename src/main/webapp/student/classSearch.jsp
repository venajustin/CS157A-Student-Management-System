<%--
  Created by IntelliJ IDEA.
  User: venaj
  Date: 12/1/2024
  Time: 11:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<%@ include file="../head-info.jsp" %>

<body>
<div id="header">
  <h1>
    Student Management - Class Search
  </h1>
  <span class="header-name">
            Logged in as:
            <span hx-trigger="load" hx-get="${pageContext.request.contextPath}/api/username">
            </span>

            <button HX-Post="${pageContext.request.contextPath}/api/accounts/logout">Logout</button>
        </span>

</div>
<div id="page-container">
  <%@ include file="../sidebar.jsp"%>
  <div id="content">

        <h2>
            Departments:
        </h2>
      <div class="scroll-table">
        <table >
            <tr hx-trigger="load" hx-get="${pageContext.request.contextPath}/api/departments" hx-swap="afterend">
                <th>Abreviation</th>
                <th>Department</th>
            </tr>
        </table>
      </div>
      <br><br>
      <h2>
          Class Search:
      </h2>
      <p>
          - Enter department abbreviation with blank course number to
          view all classes listed under department. <br>
          - Enter department and exact course number to view course info
      </p>
      <form hx-post="${pageContext.request.contextPath}/api/coursesearch" hx-target="#course-search-results">
          <label>
              Department:
              <input name="department" type="text" value=""/>
          </label> <br>
          <label>
              Course Number:
              <input name="number" type="number"/>
          </label><br>
          <button type="submit">Search</button>
      </form>

      <div id="course-search-results">

      </div>

  </div>
</div>

</body>
</html>