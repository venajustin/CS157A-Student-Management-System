package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(name = "coursesearch", value = "/api/coursesearch")

public class CourseSearch extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        var out = res.getWriter();

        String request_dept = req.getParameter("department");
        String request_number_str = req.getParameter("number");
        if (request_dept == null || request_dept.isEmpty()) {
            var templatePath = "/WEB-INF/resources/ClassListRow.html";
            templatePath = getServletContext().getRealPath(templatePath);
            String rowtemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

            templatePath = "/WEB-INF/resources/ClassListContainer.html";
            templatePath = getServletContext().getRealPath(templatePath);
            String containertemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

            try {
                var conn = DatabaseConnection.getConnection();
                var pstmt = conn.prepareStatement("SELECT number, dept, name, COALESCE(description, 'N/A'), COALESCE(units, 0) " +
                        "FROM courses");
                pstmt.executeQuery();

                var rs = pstmt.getResultSet();

                int rowloc = containertemplate.indexOf("${rows}");
                out.println(containertemplate.substring(0, rowloc));

                while (rs.next()) {
                    var thistemp = rowtemplate.replace("${courseid}", rs.getString(1));
                    thistemp = thistemp.replace("${abbr}", rs.getString(2));
                    thistemp = thistemp.replace("${name}", rs.getString(3));
                    thistemp = thistemp.replace("${description}", rs.getString(4));
                    thistemp = thistemp.replace("${units}", rs.getString(5));

                    System.out.println(thistemp);

                    out.println(thistemp);
                }

                out.println(containertemplate.substring(rowloc + 7));

            } catch (Exception e) {
                res.sendError(500);
                System.out.println("DB ERROR: " + e.getMessage());
            }
            return;
        }
        request_dept = request_dept.trim();
        if (request_dept.length() > 5) {
            out.println("Invalid department");
        }

        if (request_number_str == null || request_number_str.length() == 0) {
            System.out.println("course list");

            var templatePath = "/WEB-INF/resources/ClassListRow.html";
            templatePath = getServletContext().getRealPath(templatePath);
            String rowtemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

            templatePath = "/WEB-INF/resources/ClassListContainer.html";
            templatePath = getServletContext().getRealPath(templatePath);
            String containertemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

            try {
                System.out.println("searching courses under: " + request_dept);
                var conn = DatabaseConnection.getConnection();
                var pstmt = conn.prepareStatement("SELECT number, dept, name, COALESCE(description, 'N/A'), COALESCE(units, 0) " +
                        "FROM courses WHERE dept = ?");
                pstmt.setString(1, request_dept);
                pstmt.executeQuery();

                var rs = pstmt.getResultSet();

                int rowloc = containertemplate.indexOf("${rows}");
                out.println(containertemplate.substring(0, rowloc));

                while (rs.next()) {
                    var thistemp = rowtemplate.replace("${courseid}", rs.getString(1));
                    thistemp = thistemp.replace("${abbr}", rs.getString(2));
                    thistemp = thistemp.replace("${name}", rs.getString(3));
                    thistemp = thistemp.replace("${description}", rs.getString(4));
                    thistemp = thistemp.replace("${units}", rs.getString(5));

                    System.out.println(thistemp);

                    out.println(thistemp);
                }

                out.println(containertemplate.substring(rowloc + 7));

            } catch (Exception e) {
                res.sendError(500);
                System.out.println("DB ERROR: " + e.getMessage());
            }
        } else {
            int request_number = Integer.parseInt(request_number_str.trim());
            out.println("Work in progress");
        }






    }

}