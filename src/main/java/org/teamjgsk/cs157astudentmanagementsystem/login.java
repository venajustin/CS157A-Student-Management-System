package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "login", value = "/api/accounts/login")

public class login extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        var out = res.getWriter();
        HttpSession session = req.getSession();
        var uid = session.getAttribute("userid");
        if (uid != null) {
            out.println("USER ALREADY LOGGED IN");
            System.out.println("USER ALREADY LOGGED IN");
            res.sendError(400);
            return;
        }


        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email.isEmpty() || password.isEmpty()) {
            out.println("Please provide email and password.");
            return;
        }

        try {
            var conn = DatabaseConnection.getConnection();
            var pstmt = conn.prepareStatement("SELECT studentId FROM Students " +
                    "WHERE email LIKE ? AND " +
                    "password LIKE crypt(?, password)");


            pstmt.setString(1, email);
            pstmt.setString(2, password);

            pstmt.executeQuery();

            var rs = pstmt.getResultSet();
            int count = 0;
            int studentId = -1;
            while (rs.next()) {
                studentId = rs.getInt("studentId");
                count++;
            }
            if (count != 1 || studentId == -1) {
                out.println("Incorrect email or password");
                return;
            }

            // correct information was entered
            session.setAttribute("userid", (Integer)studentId);
            System.out.println("User: " + studentId + " LOGGED IN");

            // Redirecting to home page
            out.println("Successfully logged in");
            var url = req.getRequestURL().substring(0, req.getRequestURL().indexOf("/api/accounts/"));
            res.setHeader("HX-Redirect", url);
            //res.sendRedirect(url);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            out.println("ERROR in backend");
            res.sendError(400);
            return;
        }
    }
}
