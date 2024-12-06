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
          - Leave both blank to view all. <br>
          - Leave number blank to view all courses in a department. <br>
          - Enter dept. and partial number to display all courses starting with that number <br>
          - Enter exact course dept. & number to display available sections.
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

      <h2>
          Add Class:
      </h2>
      <p>
          - Enter Section Code to enroll in that section.
      </p>
      <form hx-post="${pageContext.request.contextPath}/api/add-class" hx-target="#add-section-error">
          <label>
              Section Code:
              <input name="sectionid" type="number" value=""/>
          </label> <br>
          <button type="submit">Add</button>
          <div id="add-section-error"></div>
      </form>

  </div>
</div>

</body>
</html>