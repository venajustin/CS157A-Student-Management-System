package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "enroll", value = "/api/add-class")

public class Enroll extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html");
        var out = res.getWriter();

        HttpSession session = req.getSession();
        var url = req.getRequestURL();

        var sectionstr = req.getParameter("sectionid");

        if (session.getAttribute("userid") != null && sectionstr != null) {
            var uid = (Integer) session.getAttribute("userid");
            var sectionid = Integer.parseInt(sectionstr.trim());
            try {
                var conn = DatabaseConnection.getConnection();

                var stmt0 = conn.prepareStatement("SELECT studentid FROM students WHERE studentid = ?");
                stmt0.setInt(1, uid);
                 stmt0.executeQuery();
                var rs0 = stmt0.getResultSet();

                if (!rs0.next()) {
                    // There is no results, so no student has this id
                    conn.close();
                    out.println("Cannot add a class if you are not a student");
                    return;
                }


                var stmt = conn.prepareStatement("INSERT INTO enrollments " +
                        "VALUES (?, ?)");
                stmt.setInt(1, sectionid);
                stmt.setInt(2, uid);

                try {
                    stmt.executeUpdate();
                    res.setHeader("HX-Redirect",
                            url.substring(0, url.indexOf("/api/"))
                            + "/student/currentSchedule.jsp");
                } catch (SQLException sqle) {
                    System.out.println("ERROR adding class: " + sqle);
                    out.println("Error adding class.");
                }
                conn.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                out.println("ERROR in backend");
                res.sendError(400);

                return;
            }
        } else {

            res.setHeader("HX-Redirect", url.substring(0, url.indexOf("/api/")));

        }

    }

}
