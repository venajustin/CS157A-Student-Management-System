package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(name = "departments", value = "/api/departments")

public class Departments extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        var out = res.getWriter();

        var templatePath = "/WEB-INF/resources/DepartmentRow.html";
        templatePath = getServletContext().getRealPath(templatePath);
        String template = new String(Files.readAllBytes(Paths.get(templatePath)));

        try {
            var conn = DatabaseConnection.getConnection();
            var pstmt = conn.prepareStatement("SELECT * FROM departments");
            pstmt.executeQuery();

            var rs = pstmt.getResultSet();

            while (rs.next()) {
                var abbrev = rs.getString(1);
                var name = rs.getString(2);

                var thistemp = template.replace("${abbr}", abbrev);
                thistemp = thistemp.replace("${name}", name);

                out.println(thistemp);
            }
            conn.close();
        } catch (Exception e) {
            res.sendError(500);
            System.out.println("DB ERROR: " + e.getMessage());
        }




    }

}
