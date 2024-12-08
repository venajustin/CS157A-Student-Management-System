package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(name = "selectsection", value = "/api/professor/selectsection")

public class SelectSection extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        var out = res.getWriter();

        HttpSession session = req.getSession();
        var url = req.getRequestURL();

        if (session.getAttribute("userid") != null ) {
            var uid = (Integer) session.getAttribute("userid");


            var templatePath = "/WEB-INF/resources/StudentListRow.html";
            templatePath = getServletContext().getRealPath(templatePath);
            String rowtemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

            templatePath = "/WEB-INF/resources/StudentListContainer.html";
            templatePath = getServletContext().getRealPath(templatePath);
            String containertemplate = new String(Files.readAllBytes(Paths.get(templatePath)));


            try {
                var conn = DatabaseConnection.getConnection();
                var st = conn.prepareStatement("SELECT " +
                        "id, " +
                        "name, " +
                        "students.major, " +
                        "grade_letter(enrollments.grade) " +
                        "FROM accounts" +
                        "     INNER JOIN students ON id = studentid " +
                        "     INNER JOIN enrollments ON student = id " +
                        "WHERE enrollments.sectionid = ?");
                st.setInt(1, Integer.parseInt(req.getParameter("sectionid")));


                st.executeQuery();

                var rs = st.getResultSet();

                int rowloc = containertemplate.indexOf("${rows}");
                out.println(containertemplate.substring(0, rowloc));

                while (rs.next()) {

                    var id = rs.getString(1);
                    if (id == null) id = "N/A";
                    var name = rs.getString(2);
                    if (name == null) name = "N/A";
                    var major = rs.getString(3);
                    if (major == null) major = "N/A";
                    var grade = rs.getString(4);
                    if (grade == null) grade = "N/A";

                    var thistemp = rowtemplate.replace("${id}", id);
                    thistemp = thistemp.replace("${name}", name);
                    thistemp = thistemp.replace("${major}", major);
                    thistemp = thistemp.replace("${grade}", grade);

                    out.println(thistemp);
                }
                var secondHalfContainer = containertemplate.substring(rowloc + 7).replace(
                  "${sectionid}", req.getParameter("sectionid")
                );
                secondHalfContainer = secondHalfContainer.replace("${url}", req.getRequestURL().substring(0, req.getRequestURL().indexOf("/api")));
                out.println(secondHalfContainer);

                conn.close();

            } catch (Exception e ) {
                out.println("INVALID ID");
                System.out.println("ERROR: " + e.getMessage());

            }

        }
    }
}
