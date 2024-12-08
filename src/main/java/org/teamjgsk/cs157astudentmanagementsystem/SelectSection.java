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
import java.util.ArrayList;

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
                        "     INNER JOIN enrollments ON student = id" +
                        "     INNER JOIN sections ON enrollments.sectionid = sections.sectioncode " +
                        "WHERE enrollments.sectionid = ? AND sections.teacher = ?");
                st.setInt(1, Integer.parseInt(req.getParameter("sectionid")));
                st.setInt(2, uid);

                st.executeQuery();

                var rs = st.getResultSet();
                conn.close();

                class studentrow {
                    public String id;
                    public String name;
                    public String major;
                    public String grade;

                }

                var studentlist = new ArrayList<studentrow>();
                int count = 0;
                while(rs.next()){
                    count++;
                    studentrow sr = new studentrow();
                    sr.id = rs.getString(1);
                    if (sr.id == null) sr.id = "N/A";
                    sr.name = rs.getString(2);
                    if (sr.name == null) sr.name = "N/A";
                    sr.major = rs.getString(3);
                    if (sr.major == null) sr.major = "N/A";
                    sr.grade = rs.getString(4);
                    if (sr.grade == null) sr.grade = "N/A";
                    studentlist.add(sr);
                }
                if (count == 0) {
                    out.println("Invalid course or no students enrolled");
                    return;
                }

                int rowloc = containertemplate.indexOf("${rows}");
                out.println(containertemplate.substring(0, rowloc));

                for (var row : studentlist) {

                   var thistemp = rowtemplate.replace("${id}", row.id);
                    thistemp = thistemp.replace("${name}", row.name);
                    thistemp = thistemp.replace("${major}", row.major);
                    thistemp = thistemp.replace("${grade}", row.grade);

                    out.println(thistemp);
                }
                var secondHalfContainer = containertemplate.substring(rowloc + 7).replace(
                  "${sectionid}", req.getParameter("sectionid")
                );
                secondHalfContainer = secondHalfContainer.replace("${url}", req.getRequestURL().substring(0, req.getRequestURL().indexOf("/api")));
                out.println(secondHalfContainer);


            } catch (Exception e ) {
                out.println("INVALID ID");
                System.out.println("ERROR: " + e.getMessage());

            }

        }
    }
}
