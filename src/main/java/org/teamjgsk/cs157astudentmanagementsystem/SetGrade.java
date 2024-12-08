package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.xml.crypto.Data;
import java.io.IOException;

@WebServlet(name = "setgrade", value = "/api/professor/setgrade")

public class SetGrade extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        var out = res.getWriter();

        HttpSession session = req.getSession();
        var url = req.getRequestURL();

        try {
            var conn = DatabaseConnection.getConnection();
            var st = conn.prepareStatement("UPDATE enrollments " +
                    "SET grade = ? " +
                    "WHERE sectionid = ? " +
                    "AND student = ? " );
            
            var grade = req.getParameter("newgrade");
            var sectionid = req.getParameter("sectionid");
            var student = req.getParameter("student");

            System.out.println("grade = " + grade);
            System.out.println("sectionid = " + sectionid);
            System.out.println("student = " + student);
            
            st.setFloat(1, Float.parseFloat(grade));
            st.setInt(2, Integer.parseInt(sectionid));
            st.setInt(3, Integer.parseInt(student));

            st.executeUpdate();

            conn.close();
            res.setHeader("HX-Refresh", "true");

        } catch (Exception e) {
            out.println("Error, please check input");
            System.out.println("ERROR: " + e.getMessage());
        }

    }
}
