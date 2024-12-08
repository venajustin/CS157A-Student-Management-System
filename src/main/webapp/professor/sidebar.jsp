
<div id="sidebar" class="wide">
  <div class="sidebar-option sidebar-title">
    <div class="sidebar-option-text">
      <h2>Menu</h2>
    </div>
    <button id="collapse-sidebar" onclick="collapse_sidebar()">
      <
    </button>
  </div>

  <a href="${pageContext.request.contextPath}/professor/home.jsp">
    <div class="sidebar-option">
      <img
              src="${pageContext.request.contextPath}/static/icons/house.png"
              alt="HOME"
              width="40"
              height="40"
      />
      <div class="sidebar-option-text">
        Home
      </div>
    </div>
  </a>
  <a href="${pageContext.request.contextPath}/professor/currentSchedule.jsp">
    <div class="sidebar-option">
      <img
              src="${pageContext.request.contextPath}/static/icons/calender.png"
              alt="HOME"
              width="40"
              height="40"
      />
      <span class="sidebar-option-text">
                        Current Schedule
                    </span>
    </div>
  </a>
  <a href="${pageContext.request.contextPath}/professor/classSearch.jsp">
    <div class="sidebar-option">
      <img
              src="${pageContext.request.contextPath}/static/icons/search.png"
              alt="HOME"
              width="40"
              height="40"
      />
      <span class="sidebar-option-text">
                        Search Classes
                    </span>
    </div>
  </a>
  <a href="${pageContext.request.contextPath}/professor/configureCourses.jsp">
    <div class="sidebar-option">
      <img
              src="${pageContext.request.contextPath}/static/icons/paperpen.png"
              alt="HOME"
              width="40"
              height="40"
      />
      <span class="sidebar-option-text">
                        Manage Courses
                    </span>
    </div>
  </a>



</div>

