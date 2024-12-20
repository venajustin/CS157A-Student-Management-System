package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Time;

@WebServlet(name = "addsection", value = "/api/professor/createsection")

public class AddSection extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html");
        var out = res.getWriter();

        var session = req.getSession();
        var uid = (int) session.getAttribute("userid");

        String request_dept = req.getParameter("department");
        String request_number_str = req.getParameter("number");
        String request_days = req.getParameter("days");
        String request_starttime = req.getParameter("starttime");
        String request_endtime = req.getParameter("endtime");

        try {
            var conn = DatabaseConnection.getConnection();
            var st = conn.prepareStatement("SELECT dept, number FROM courses WHERE dept = ? AND number = ?");
            st.setString(1, request_dept);
            st.setInt(2, Integer.parseInt(request_number_str));
            st.executeQuery();
            var rs = st.getResultSet();
            conn.close();

            if (!rs.next()) {
                out.println("Course " + request_dept + " " + request_number_str + " does not exist");
                out.println("Enter a valid department and course number");
                return;
            }

        } catch (Exception e) {
            out.println("Invalid input");
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        try {
            var conn = DatabaseConnection.getConnection();
            var st = conn.prepareStatement("INSERT INTO sections " +
                    "(dept, course, session, days, starttime, endtime, teacher)" +
                    "VALUES ( " +
                    "?," +
                    "?," +
                    "current_session()," +
                    "?," +
                    "?," +
                    "?," +
                    "? " +
                    ") ");
            st.setString(1, request_dept);
            st.setInt(2, Integer.parseInt(request_number_str));
            st.setString(3, request_days);
            st.setTime(4, Time.valueOf(request_starttime));
            st.setTime(5, Time.valueOf(request_endtime));
            st.setInt(6, uid);
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

        String req_id = req.getParameter("sectionid");

        try {
            var conn = DatabaseConnection.getConnection();
            var ps = conn.prepareStatement("DELETE FROM sections " +
                    "WHERE sectioncode = ? ");
            ps.setInt(1, Integer.parseInt(req_id));
            ps.executeUpdate();
            conn.close();

            out.println("Section " + req_id + " deleted successfully");

        } catch (Exception e) {
            out.println("Invalid input");
            System.out.println("ERROR: " + e.getMessage());
            return;
        }
    }
}