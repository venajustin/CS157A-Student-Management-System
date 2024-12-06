package org.teamjgsk.cs157astudentmanagementsystem;

import java.io.*;

import java.sql.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.naming.NamingException;
import javax.sql.DataSource;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        try {
            var conn = DatabaseConnection.getConnection();

            var stmt = conn.createStatement();
            log("executing query: " + stmt);
            stmt.execute("INSERT INTO test1 " +
                    "VALUES (" +
                    "(SELECT MAX(count) FROM test1) + 1," +
                    "current_time);");

            log("query executed");
            
            conn.close();
            message = "query sent";

            conn.close();
        } catch (Exception e) {
            System.out.println(e);
            message = "exception occurred";
        }




        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}