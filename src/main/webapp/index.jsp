<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<%@ include file="head-info.jsp" %>

<body>
    <div id="header">
        <h1>
            Student Management
        </h1>
    </div>
    <div id="page-container">
        <%@ include file="sidebar.jsp"%>
        <div id="content">
            Test
            <a href="${pageContext.request.contextPath}/accounts/signup.jsp">signup</a>
            <a href="${pageContext.request.contextPath}/accounts/login.jsp">login</a>
        </div>
    </div>

<%--    <button hx-post="${pageContext.request.contextPath}/increment-test" hx-target="#count-span" hx-swap="innerHTML">Increment</button>--%>
<%--    <button hx-post="${pageContext.request.contextPath}/create-db" hx-swap="beforeend">Create Tables</button>--%>
</body>
</html>