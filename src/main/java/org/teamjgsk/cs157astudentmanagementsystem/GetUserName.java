package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "username", value = "/api/username")
public class GetUserName extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        var out = res.getWriter();

        HttpSession session = req.getSession();
        var url = req.getRequestURL();

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
                    var name = rs.getString(1);
                    out.println(name);

                } else {
                    res.setHeader("HX-Redirect", url.substring(0, url.indexOf("/api/")));

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
