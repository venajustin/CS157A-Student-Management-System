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
<%--        <%@ include file="sidebar.jsp"%>--%>
        <div id="content">
            <H2 hx-get="${pageContext.request.contextPath}/api/identify" hx-trigger="load" >
                <a href="${pageContext.request.contextPath}/accounts/signup.jsp">signup</a> <br>
                <a href="${pageContext.request.contextPath}/accounts/login.jsp">login</a>
            </H2>

        </div>
    </div>

</body>
</html>