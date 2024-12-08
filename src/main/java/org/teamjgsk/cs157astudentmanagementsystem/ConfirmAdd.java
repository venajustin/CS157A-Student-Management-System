package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(name = "confirmadd", value = "/api/coursesearch/add")

public class ConfirmAdd extends HttpServlet {


///  maybe add this later but scrapping for now

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        var out = res.getWriter();

        var courseid = req.getParameter("courseid");

        if (courseid == null) {
            out.println("");
            return;
        }

        var sectionid = Integer.parseInt(courseid);
        try {
            var conn = DatabaseConnection.getConnection();

            var pstmt = conn.prepareStatement("" +
                    "SELECT sections.sectioncode, " +
                    "courses.dept, " +
                    "courses.number, " +
                    "courses.name, " +
                    "accounts.name " +
                    "FROM sections INNER JOIN professors ON sections.teacher = professors.employeeid " +
                    " INNER JOIN courses ON sections.dept = courses.dept AND sections.course = courses.number " +
                    "INNER JOIN accounts ON professors.employeeid = accounts.id " +
                    " WHERE sections.sectioncode = ?");
            pstmt.setInt(1, sectionid);

            pstmt.executeQuery();

            var rs = pstmt.getResultSet();

            var templatePath = "/WEB-INF/resources/ConfirmAddClass.html";
            templatePath = getServletContext().getRealPath(templatePath);
            String template = new String(Files.readAllBytes(Paths.get(templatePath)));

            while (rs.next()) {
                template = template.replace("${sectionid}", rs.getString(1));
                template = template.replace( "${abbr}", rs.getString(2));
                template = template.replace( "${courseid}", rs.getString(3));
                template = template.replace( "${coursename}", rs.getString(4));
                template = template.replace( "${teacher}", rs.getString(5));
                template = template.replace("${url}", req.getRequestURL());
                out.print(template);
            }

            conn.close();
        }catch (Exception e) {
            res.sendError(400);

        }


    }
}
