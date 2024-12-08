package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.DirectoryManager;
import javax.sql.DataSource;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "userinfo", value = "/api/accounts/userinfoform")
public class UserInfoForm  extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        var out = res.getWriter();

        HttpSession session = req.getSession();
        var url = req.getRequestURL();

        if (session.getAttribute("userid") != null) {
            var uid = (Integer) session.getAttribute("userid");
            try {
                var conn = DatabaseConnection.getConnection();
                var stmt = conn.prepareStatement("SELECT account_type FROM accounts where id = ?");
                stmt.setInt(1, uid);
                stmt.executeQuery();
                var rs = stmt.getResultSet();
                rs.next();
                var acctype = rs.getString(1);
                conn.close();

                if (acctype.compareTo("professor") == 0 ) {
                    fetchAndSendProfessorDataForm(res, uid, out, url);

                } else {
                    fetchAndSendUserDataForm(res, uid, out, url);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                out.println("ERROR in backend");
                res.sendError(400);

                return;
            }
        } else {

            out.println("no user data found");
        }


    }

    private void fetchAndSendUserDataForm(HttpServletResponse res, Integer uid, PrintWriter out, StringBuffer url) throws NamingException, SQLException, IOException {
        var conn = DatabaseConnection.getConnection();
        var pstmt = conn.prepareStatement("SELECT " +
                "id, name, email " +
                "FROM accounts " +
                "WHERE id = ?"
        );

        pstmt.setInt(1, uid);

        pstmt.executeQuery();
        var rs = pstmt.getResultSet();

        if (rs.next()) {
            var id = rs.getString(1);
            var name = rs.getString(2);
            var email = rs.getString(3);
            String major = null;

            pstmt = conn.prepareStatement("SELECT " +
                    "major " +
                    "FROM students " +
                    "WHERE studentid = ?"
            );

            pstmt.setInt(1, uid);

            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            if (rs.next()) {
                major = rs.getString(1);
            }

            pstmt = conn.prepareStatement("SELECT " +
                    "avg(Enrollments.grade) AS gpa, " +
                    "SUM(Courses.units) AS unitscompleted " +
                    "FROM accounts " +
                    "INNER JOIN students ON students.studentid = accounts.id " +
                    "LEFT JOIN Enrollments ON Students.studentid = Enrollments.student " +
                    "INNER JOIN Sections ON Enrollments.sectionid = Sections.sectioncode " +
                    "INNER JOIN Courses ON Sections.dept = Courses.dept and Sections.course = Courses.number " +
                    "WHERE accounts.id = ? " +
                    "GROUP BY  ( " +
                    "accounts.id ) "
            );

            pstmt.setInt(1, uid);

            pstmt.executeQuery();

            String gpa = null;
            String units = null;
            rs = pstmt.getResultSet();
            if (rs.next()) {

                gpa = rs.getString(1);
                units = rs.getString(2);
            }

            InitialContext ctx = new InitialContext();
            var templatePath = "/WEB-INF/resources/UserInfoForm.html";
            templatePath = getServletContext().getRealPath(templatePath);
            try {

                String template = new String(Files.readAllBytes(Paths.get(templatePath)));

                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("id", id);
                placeholders.put("name", name);
                placeholders.put("email", email);
                placeholders.put("major", major);
                placeholders.put("gpa", gpa);
                placeholders.put("totalunits", units);

                for(Map.Entry<String, String> entry : placeholders.entrySet()) {
                    if (entry.getValue() == null) {
                        entry.setValue("N/A");
                    }
                }

                for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                    template = template.replace("${" + entry.getKey() + "}", entry.getValue());
                }
                template = template.replace("${post-destination}", url);

                out.println(template);
            } catch (IOException e) {
                System.out.println("failed to read file" + templatePath);
                System.out.println(e.getMessage());
                res.sendError(500);
            }


        } else {
            out.println("no user data found");

        }
        conn.close();

    }

    private void fetchAndSendProfessorDataForm(HttpServletResponse res, Integer uid, PrintWriter out, StringBuffer url) throws NamingException, SQLException, IOException {
        var conn = DatabaseConnection.getConnection();
        var pstmt = conn.prepareStatement("SELECT " +
                "id, name, email " +
                "FROM accounts " +
                "WHERE id = ?"
        );

        pstmt.setInt(1, uid);

        pstmt.executeQuery();
        var rs = pstmt.getResultSet();

        if (rs.next()) {
            var id = rs.getString(1);
            var name = rs.getString(2);
            var email = rs.getString(3);
            String dept = null;

            pstmt = conn.prepareStatement("SELECT " +
                    "dept " +
                    "FROM professors " +
                    "WHERE employeeid = ?"
            );

            pstmt.setInt(1, uid);

            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            if (rs.next()) {
                dept = rs.getString(1);
            }
            var templatePath = "/WEB-INF/resources/ProfessorInfoForm.html";
            templatePath = getServletContext().getRealPath(templatePath);
            try {

                String template = new String(Files.readAllBytes(Paths.get(templatePath)));

                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("id", id);
                placeholders.put("name", name);
                placeholders.put("email", email);
                placeholders.put("dept", dept);

                for(Map.Entry<String, String> entry : placeholders.entrySet()) {
                    if (entry.getValue() == null) {
                        entry.setValue("N/A");
                    }
                }

                for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                    template = template.replace("${" + entry.getKey() + "}", entry.getValue());
                }
                template = template.replace("${post-destination}", url);

                out.println(template);
            } catch (IOException e) {
                System.out.println("failed to read file" + templatePath);
                System.out.println(e.getMessage());
                res.sendError(500);
            }


        } else {
            out.println("no user data found");

        }
        conn.close();

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        var out = resp.getWriter();

        HttpSession session = req.getSession();
        var url = req.getRequestURL();

        if (session.getAttribute("userid") != null) {
            var uid = (Integer) session.getAttribute("userid");
            try {
                var conn = DatabaseConnection.getConnection();
                var stmt = conn.prepareStatement("SELECT account_type FROM accounts where id = ?");
                stmt.setInt(1, uid);
                stmt.executeQuery();
                var rs = stmt.getResultSet();
                rs.next();
                var acctype = rs.getString(1);
                conn.close();

                if (acctype.compareTo("professor") == 0 ) {


                    var name = req.getParameter("name");
                    var email = req.getParameter("email");
                    var dept = req.getParameter("dept");

                    conn = DatabaseConnection.getConnection();
                    conn.setAutoCommit(false);
                    var pstmt = conn.prepareStatement("UPDATE Accounts SET " +
                            "name = ?, " +
                            "email = ? " +
                            "WHERE id = ?");

                    pstmt.setString(1, name);
                    pstmt.setString(2, email);
                    pstmt.setInt(3, uid);

                    pstmt.executeUpdate();

                    pstmt = conn.prepareStatement("UPDATE Professors SET " +
                            "dept = ? " +
                            "WHERE employeeid = ?");
                    pstmt.setString(1, dept);

                    pstmt.setInt(2, uid);

                    try {
                        pstmt.executeUpdate();
                    } catch (Exception e ) {
                        conn.rollback();
                        conn.close();
                        fetchAndSendProfessorDataForm(resp, uid, out, url);
                        out.println("Invalid department");
                        return;
                    }
                    conn.commit();
                    conn.setAutoCommit(true);
                    // sending the same form again so that they see the updated changes
                    fetchAndSendProfessorDataForm(resp, uid, out, url);

                    conn.close();

                } else {

                    var name = req.getParameter("name");
                    var email = req.getParameter("email");
                    var major = req.getParameter("major");

                    conn = DatabaseConnection.getConnection();
                    var pstmt = conn.prepareStatement("UPDATE Accounts SET " +
                            "name = ?, " +
                            "email = ? " +
                            "WHERE id = ?");

                    pstmt.setString(1, name);
                    pstmt.setString(2, email);
                    pstmt.setInt(3, uid);

                    pstmt.executeUpdate();

                    pstmt = conn.prepareStatement("UPDATE Students SET " +
                            "major = ? " +
                            "WHERE studentid = ?");
                    pstmt.setString(1, major);

                    pstmt.setInt(2, uid);

                    pstmt.executeUpdate();


                    // sending the same form again so that they see the updated changes
                    fetchAndSendUserDataForm(resp, uid, out, url);

                    conn.close();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                out.println("ERROR in backend");
                resp.sendError(400);

                return;
            }
        } else {

            out.println("no user data found");

        }

    }
}
