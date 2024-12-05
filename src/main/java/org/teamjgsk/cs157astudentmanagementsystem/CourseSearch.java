package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "coursesearch", value = "/api/coursesearch")

public class CourseSearch extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html");
        var out = res.getWriter();

        String request_dept = req.getParameter("department");
        String request_number_str = req.getParameter("number");

        // Getting templates for preparing returned html
        var templatePath = "/WEB-INF/resources/ClassListRow.html";
        templatePath = getServletContext().getRealPath(templatePath);
        String rowtemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

        templatePath = "/WEB-INF/resources/ClassListContainer.html";
        templatePath = getServletContext().getRealPath(templatePath);
        String containertemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

        // If there is no department we just display all courses
        if (request_dept == null || request_dept.isEmpty()) {

            try {
                var conn = DatabaseConnection.getConnection();
                var pstmt = conn.prepareStatement("SELECT number, dept, name, COALESCE(description, 'N/A'), COALESCE(units, 0) " +
                        "FROM courses");
                pstmt.executeQuery();

                var rs = pstmt.getResultSet();

                sendCourseList(req, containertemplate, out, rs, rowtemplate);


            } catch (Exception e) {
                res.sendError(500);
                System.out.println("DB ERROR: " + e.getMessage());
            }
            return;
        }

        request_dept = request_dept.trim();
        request_dept = request_dept.toUpperCase();
        if (request_dept.length() > 5) {
            out.println("Invalid department");
        }

        // If there is no course number specified, we display all courses under that department
        if (request_number_str == null || request_number_str.length() == 0) {

            try {
                var conn = DatabaseConnection.getConnection();
                var pstmt = conn.prepareStatement("SELECT number, dept, name, COALESCE(description, 'N/A'), COALESCE(units, 0) " +
                        "FROM courses WHERE dept = ?");
                pstmt.setString(1, request_dept);
                pstmt.executeQuery();

                var rs = pstmt.getResultSet();

                sendCourseList(req, containertemplate, out, rs, rowtemplate);

            } catch (Exception e) {
                res.sendError(500);
                System.out.println("DB ERROR: " + e.getMessage());
            }
        } else {
            int request_number = Integer.parseInt(request_number_str.trim());
            out.println("Work in progress");
        }


        var request_number = -1;
        try {
            request_number = Integer.parseInt(request_number_str.trim());
        } catch (NumberFormatException nfe) {
            res.sendError(400);
            return;
        }

        // Searching for exact class
        if (request_number != -1 && !request_dept.isEmpty()) {

            try {
                var conn = DatabaseConnection.getConnection();
                var pstmt = conn.prepareStatement("SELECT number, dept, name, COALESCE(description, 'N/A'), COALESCE(units, 0) " +
                        "FROM courses WHERE dept = ? AND number = ?");
                pstmt.setString(1, request_dept);
                pstmt.setInt(2, request_number);
                pstmt.executeQuery();

                var rs = pstmt.getResultSet();

                sendCourseList(req, containertemplate, out, rs, rowtemplate);

                pstmt = conn.prepareStatement("SELECT sectioncode, dept, course, days, starttime, endtime "+
                        "FROM sections WHERE dept = ? AND course = ? AND session IN ( " +
                        "SELECT sessionid FROM sessions WHERE firstday < current_date AND lastday > current_date" +
                        " )");
                pstmt.setString(1, request_dept);
                pstmt.setInt(2, request_number);
                pstmt.executeQuery();

                rs = pstmt.getResultSet();

            } catch (Exception e) {
                res.sendError(500);
                System.out.println("DB ERROR: " + e.getMessage());
            }
        } else {
            int request_number = Integer.parseInt(request_number_str.trim());
            out.println("Work in progress");
        }

    }

    private static void sendCourseList(HttpServletRequest req, String containertemplate, PrintWriter out, ResultSet rs, String rowtemplate) throws SQLException {
        int rowloc = containertemplate.indexOf("${rows}");
        out.println(containertemplate.substring(0, rowloc));

        while (rs.next()) {
            var thistemp = rowtemplate.replace("${courseid}", rs.getString(1));
            thistemp = thistemp.replace("${abbr}", rs.getString(2));
            thistemp = thistemp.replace("${name}", rs.getString(3));
            thistemp = thistemp.replace("${description}", rs.getString(4));
            thistemp = thistemp.replace("${units}", rs.getString(5));

            thistemp = thistemp.replace("${url}", req.getRequestURL());

            out.println(thistemp);
        }

        out.println(containertemplate.substring(rowloc + 7));
    }

}