package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "signup", value = "/api/accounts/signup")
public class signup extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        var out = res.getWriter();

        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("password2");
        String email = req.getParameter("email");
        String acctype = req.getParameter("accounttype");
        //String major = req.getParameter("major");

        if (password.compareTo(passwordConfirm) != 0) {
            out.println(
                    "Passwords do not match."
                    );
            return;
        }
        if (password.isEmpty())  {
            out.println("Password required");
            return;
        }

        try {
            var conn = DatabaseConnection.getConnection();

            // Checking if another user uses that email
            if (!email.isEmpty()) {
                var pstmtCheckExists = conn.prepareStatement("SELECT COUNT(email)" +
                        " FROM students WHERE email LIKE ?");

                pstmtCheckExists.setString(1, email);
                pstmtCheckExists.executeQuery();

                ResultSet results = pstmtCheckExists.executeQuery();
                results.next();
                int countUsersWithEmail = results.getInt(1);

                if (countUsersWithEmail > 0) {
                    out.println("user with that email already exists");
                    conn.close();
                    return;
                }
            } else {
                out.println("Email Required");
                conn.close();
                return;
            }

            if (password.length() > 30 || password.length() < 8) {
                out.println("Please choose a password with a length between 8 and 30 (inclusive)");
                conn.close();
                return;
            }

            // for now just supports adding students
            var pstmtAddUser = conn.prepareStatement("INSERT INTO Accounts (" +
                    "name, " +
                    "email, " +
                    "password, " +
                    "account_type ) " +
                    "VALUES ( " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "? " +
                    ") ");
            pstmtAddUser.setString(1, name);
            pstmtAddUser.setString(2, email);
            pstmtAddUser.setString(3, password);
            pstmtAddUser.setString(4, acctype);
            try {
                pstmtAddUser.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
                out.println("There has been an error, please try different credentials.");
                conn.close();
                return;
            }

            System.out.println("Name: " + name + " CREATED AN ACCOUNT");
            //System.out.println(req.getRequestURL().substring(0, req.getRequestURL().indexOf("/api/accounts/")));
            res.setHeader("HX-Redirect",req.getRequestURL().substring(0, req.getRequestURL().indexOf("/api/accounts/")));

            //res.sendRedirect(req.getRequestURL().substring(0, req.getRequestURL().indexOf("/api/accounts/")));

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            out.println("ERROR in backend");
            res.sendError(400);
            return;
        }
    }
}
