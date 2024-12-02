<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<%@ include file="../head-info.jsp" %>

<body>
    <div id="header">
        <h1>
            Student Management - Home
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

            <div hx-get="${pageContext.request.contextPath}/api/accounts/userinfoform" hx-trigger="load">

            </div>


        </div>
    </div>

<%--    <button hx-post="${pageContext.request.contextPath}/increment-test" hx-target="#count-span" hx-swap="innerHTML">Increment</button>--%>
<%--    <button hx-post="${pageContext.request.contextPath}/create-db" hx-swap="beforeend">Create Tables</button>--%>
</body>
</html>