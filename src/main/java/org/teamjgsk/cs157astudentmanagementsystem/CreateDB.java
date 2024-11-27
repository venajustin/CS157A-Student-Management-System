package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "createDB", value = "/create-db")

public class CreateDB extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            var conn = DatabaseConnection.getConnection();

            var stmt = conn.createStatement();
            stmt.execute("CREATE TABLE test1 " +
                    "(count int, time TIME)");
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }
}
