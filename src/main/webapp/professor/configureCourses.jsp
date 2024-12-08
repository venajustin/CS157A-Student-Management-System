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
          Create Course:
      </h3>
      <form hx-post="${pageContext.request.contextPath}/api/professor/createcourse" hx-target="#courseerr">
          <label>
              Department:
              <input type="text" name="department" placeholder="MATH"/>
          </label> <br>
          <label>
              Number:
              <input type="number" name="number" placeholder="100"/>
          </label> <br>
          <label>
              Name:
              <input type="text" name="name" placeholder="Calculus"/>
          </label> <br>
          <label>
              Description:
              <input type="text" name="description" placeholder="..."/>
          </label> <br>
          <label>
              Units:
              <input type="number" name="units" placeholder="3"/>
          </label><br>
          <button type="submit">Submit</button>
      </form>
      <div id="courseerr"></div>
      <br> <br>
      <h3>
          Create Section: (you will be the teacher)
      </h3>
      <form hx-post="${pageContext.request.contextPath}/api/professor/createsection" hx-target="#sectionerr">
          Course:
          <label>
              Dept:
              <input type="text" name="department" placeholder="MATH"/>
          </label>
          <label>
              &nbsp #:
              <input type="number" name="number" placeholder="100"/>
          </label><br>
          <label>
              Days:
              <input type="text" name="days" placeholder="MW"/>
              <br> M - Monday, T - Tuesday, W - Wednesday, R - Thursday, F - Friday, S - Saturday, U - Sunday
              <br> Examples: MW - Monday & Wednesday, TRF - Tudesday, Thursday & Friday
          </label><br>
          <label>
              Start Time:
              <input type="text" name="starttime" placeholder="14:30:00"/>
          </label><br>
          <label>
              End Time:
              <input type="text" name="endtime" placeholder="15:45:00"/>
              <br> Enter in 24hr time
          </label><br>
          <button type="submit">Submit</button>
      </form>
      <div id="sectionerr"></div>

      <br> <br>
      <h3>
          Delete Course:
      </h3>
      <form hx-delete="${pageContext.request.contextPath}/api/professor/createcourse" hx-target="#deletecourseerr">
          <label>
              Course Department:
              <input type="text" name="department" placeholder="MATH"/>
          </label> <br>
          <label>
              Course Number:
              <input type="number" name="number" placeholder="100"/>
          </label> <br>
          <button type="submit">Submit</button>
      </form>
      <div id="deletecourseerr"></div>
      <br> <br>
      <h3>
          Delete Section:
      </h3>
      <form hx-delete="${pageContext.request.contextPath}/api/professor/createsection" hx-target="#deletesectionerr">
          <label>
              Section Code:
              <input type="number" name="sectionid"/>
          </label> <br>
          <button type="submit">Submit</button>
      </form>
      <div id="deletesectionerr"></div>


  </div>
</div>

</body>
</html>