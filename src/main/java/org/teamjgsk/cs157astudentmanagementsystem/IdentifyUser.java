package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "identify", value = "/api/identify")
public class IdentifyUser extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        var out = res.getWriter();
        HttpSession session = req.getSession();
        var url = req.getHeader("HX-Current-URL");

        if (session.getAttribute("userid") != null) {
            var uid = (Integer) session.getAttribute("userid");
            try {
                var conn = DatabaseConnection.getConnection();
                var pstmt = conn.prepareStatement("SELECT name FROM Students " +
                        "WHERE studentid = ?");

                pstmt.setInt(1, uid);

                pstmt.executeQuery();

                var rs = pstmt.getResultSet();


                if (rs.next()) {

                    System.out.println("user logged in");

                    var name = rs.getString(1);
                    out.println("redirecting");
                    res.setHeader("HX-Redirect", url + "student/home.jsp");

                } else {

                    System.out.println("user not logged in");

                    out.println("<H2 hx-trigger=\"load\" hx-get=\"/api/identify\">\n" +
                           "<a href=\"" + url +  "/accounts/signup.jsp\">signup</a> <br>\n" +
                           "<a href=\"" + url + "/accounts/login.jsp\">login</a>\n" +
                           "</H2>");
                }

                conn.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                out.println("ERROR in backend");
                res.sendError(400);
                return;
            }
        } else {

            System.out.println("user not logged in");

            out.println("<H2 hx-trigger=\"load\" hx-get=\"/api/identify\">\n" +
                    "<a href=\"" + url +  "/accounts/signup.jsp\">signup</a> <br>\n" +
                    "<a href=\"" + url + "/accounts/login.jsp\">login</a>\n" +
                    "</H2>");
        }
    }
}
