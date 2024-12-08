package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.crypto.Data;
import java.io.IOException;

@WebServlet(name = "addcourse", value = "/api/professor/createcourse")

public class AddCourse extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html");
        var out = res.getWriter();

        String request_dept = req.getParameter("department");
        String request_number_str = req.getParameter("number");
        String request_name = req.getParameter("name");
        String request_description = req.getParameter("description");
        String request_units_str = req.getParameter("units");

        try {
            var conn = DatabaseConnection.getConnection();
            var st = conn.prepareStatement("SELECT abbr FROM departments WHERE abbr = ?");
            st.setString(1, request_dept);
            st.executeQuery();
            var rs = st.getResultSet();
            conn.close();

            if (!rs.next()) {
                out.println("Enter a valid department");
                return;
            }

        } catch (Exception e) {
            out.println("Invalid input");
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        try {
            var conn = DatabaseConnection.getConnection();
            var st = conn.prepareStatement("SELECT dept, number FROM courses WHERE dept = ? AND number = ?");
            st.setString(1, request_dept);
            st.setInt(2, Integer.parseInt(request_number_str));
            st.executeQuery();
            var rs = st.getResultSet();
            conn.close();

            if (rs.next()) {
                out.println("Course " + request_dept + " " + request_number_str + " already exists");
                return;
            }

        } catch (Exception e) {
            out.println("Invalid input");
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        try {
            var conn = DatabaseConnection.getConnection();
            var st = conn.prepareStatement("INSERT INTO courses " +
                    "(number, dept, name, description, units )" +
                    "VALUES ( " +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "? " +
                    ") ");
            st.setInt(1, Integer.parseInt(request_number_str));
            st.setString(2, request_dept);
            st.setString(3, request_name);
            st.setString(4, request_description);
            st.setInt(5, Integer.parseInt(request_units_str));
            st.executeUpdate();
            conn.close();

            res.setHeader("HX-Redirect", req.getRequestURL().substring(0, req.getRequestURL().indexOf("/api")) + "/professor/classSearch.jsp");

        } catch (Exception e) {
            out.println("Invalid input");
            System.out.println("ERROR: " + e.getMessage());
            return;
        }


    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html");
        var out = res.getWriter();

        String request_department = req.getParameter("department");
        String request_number = req.getParameter("number");

        try {
            var conn = DatabaseConnection.getConnection();
            var ps = conn.prepareStatement("DELETE FROM courses " +
                    "WHERE dept = ? and number = ? ");
            ps.setString(1, request_department);
            ps.setInt(2, Integer.parseInt(request_number));
            ps.executeUpdate();
            conn.close();

            out.println("Course " + request_department + " " + request_number + " deleted successfully");

        } catch (Exception e) {
            out.println("Invalid input");
            System.out.println("ERROR: " + e.getMessage());
            return;
        }
    }
}