package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(name = "getSchedule", value = "/api/student/schedule")
public class CurrentSchedule extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        var out = res.getWriter();

        HttpSession session = req.getSession();
        var url = req.getRequestURL();

        if (session.getAttribute("userid") != null ) {
            var uid = (Integer) session.getAttribute("userid");

            var templatePath = "/WEB-INF/resources/CurrentScheduleRow.html";
            templatePath = getServletContext().getRealPath(templatePath);
            String rowtemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

            templatePath = "/WEB-INF/resources/CurrentScheduleContainer.html";
            templatePath = getServletContext().getRealPath(templatePath);
            String containertemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

            try {
                var conn = DatabaseConnection.getConnection();
                var pstmt = conn.prepareStatement(
                        "SELECT courses.dept, " +
                        "   courses.number, " +
                        "   courses.name, " +
                        "   courses.units, " +
                        "   sections.days, " +
                        "   to_char(sections.starttime, 'HH12:MI AM'), " +
                        "   to_char(sections.endtime, 'HH12:MI AM'), " +
                        "   professors.name " +
                        "FROM courses INNER JOIN sections " +
                        "   ON sections.dept = courses.dept " +
                        "   AND sections.course = courses.number " +
                        "   INNER JOIN enrollments " +
                        "   ON enrollments.sectionid = sections.sectioncode " +
                        "   INNER JOIN professors " +
                        "   ON professors.employeeid = sections.teacher " +
                        "WHERE " +
                        "   enrollments.student = ? ");
                pstmt.setInt(1, uid);
                pstmt.executeQuery();

                var rs = pstmt.getResultSet();

                int rowloc = containertemplate.indexOf("${rows}");
                out.println(containertemplate.substring(0, rowloc));

                var count = 0;
                while (rs.next()) {
                    count++;
                    var thistemp = rowtemplate.replace("${abbr}", rs.getString(1));
                    thistemp = thistemp.replace("${courseid}", rs.getString(2));
                    thistemp = thistemp.replace("${name}", rs.getString(3));
                    thistemp = thistemp.replace("${units}", rs.getString(4));
                    thistemp = thistemp.replace("${days}", rs.getString(5));
                    thistemp = thistemp.replace("${starttime}", rs.getString(6));
                    thistemp = thistemp.replace("${endtime}", rs.getString(7));
                    thistemp = thistemp.replace("${instructor}", rs.getString(8));

                    out.println(thistemp);
                }

                out.println(containertemplate.substring(rowloc + 7));

                conn.close();

            } catch (Exception e) {
                res.sendError(500);
                System.out.println("DB ERROR: " + e.getMessage());
            }

        }

    }
}