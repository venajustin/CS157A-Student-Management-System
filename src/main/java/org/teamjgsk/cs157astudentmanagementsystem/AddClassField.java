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

@WebServlet(name = "addclassfield", value = "/api/addclassfield")

public class AddClassField extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html");
        var out = res.getWriter();

        HttpSession session = req.getSession();
        var url = req.getRequestURL();

        if (session.getAttribute("userid") != null) {
            var uid = (Integer) session.getAttribute("userid");

            try {
                var conn = DatabaseConnection.getConnection();
                var pstmt = conn.prepareStatement("SELECT accounts.account_type " +
                        "FROM accounts " +
                        "WHERE id = ?");
                pstmt.setInt(1, uid);

                var rs = pstmt.getResultSet();

                rs.next();
                var acctype = rs.getString(1);
                if (acctype.compareTo("student") == 0) {

                    var templatePath = "/WEB-INF/resources/AddClassForm.html";
                    templatePath = getServletContext().getRealPath(templatePath);
                    String rowtemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

                    rowtemplate = rowtemplate.replace("${url}", req.getRequestURL().substring(0, req.getRequestURL().indexOf("/api")));

                    out.println(rowtemplate);

                } else {
                    out.println("Work in progress");
                }

            } catch (Exception e ) {
                System.out.println("ERROR: " + e.getMessage());
            }



        }
    }
}