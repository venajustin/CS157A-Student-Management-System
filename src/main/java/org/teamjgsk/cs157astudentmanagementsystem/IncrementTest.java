package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Time;

@WebServlet(name = "incrementTest", value = "/increment-test")
public class IncrementTest extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        try {
            var conn = DatabaseConnection.getConnection();

            var stmt = conn.createStatement();
            stmt.execute("INSERT INTO test1 " +
                    "VALUES (" +
                    "(SELECT MAX(count) FROM test1) + 1," +
                    "current_time);");

            stmt.execute("SELECT value FROM test1" +
                    "ORDER BY test1.time DESC " +
                    "LIMIT 1");
            ResultSet rs = stmt.getResultSet();
            rs.next();

            int value = rs.getInt("count");

            out.println(value);

            conn.close();

        } catch (Exception e) {
            System.out.println(e);
            out.println("error");
        }


    }

    public void destroy() {
    }

}
