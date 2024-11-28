package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "login", value = "/api/accounts/login")

public class login extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        var out = res.getWriter();


        // TODO: write login, this code is actually the start of signup i put it in the wrong file

        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("password2");
        String email = req.getParameter("email");
        String major = req.getParameter("major");

        if (password.compareTo(passwordConfirm) != 0) {
            out.println("<div class=\"account-error\">" +
                    "Passwords do not match." +
                    "</div>");
            return;
        }

        try {
            var conn = DatabaseConnection.getConnection();
            //var pstmt = conn.prepareStatement("");



        } catch (Exception e) {
            System.out.println(e.getMessage());
            out.println("ERROR in backend");
            res.sendError(400);
            return;
        }
    }
}
