/*

         GENEREATE ENROLLMENTS

         This endpoint generates a bunch of insert statements
         to populate the database with student enrollments
         These statements have already been generated and added
         to the end of initializeData.sql

*/


package org.teamjgsk.cs157astudentmanagementsystem;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Random;

@WebServlet(name = "generate", value = "/api/generate")

public class GenerateStatements extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {
            var conn = DatabaseConnection.getConnection();

            var stmt = conn.createStatement();
            stmt.execute("SELECT studentid FROM students");
            var rs = stmt.getResultSet();

            var students = new ArrayList<Integer>();
            var numenrollments = new ArrayList<Integer>();
            var addedAlready = new ArrayList<ArrayList<Integer>>();

            while (rs.next()) {

                int id = rs.getInt(1);
                students.add(id);
                numenrollments.add(0);
                addedAlready.add(new ArrayList<>());
            }

             stmt = conn.createStatement();
            stmt.execute("SELECT sectioncode, dept, course, starttime FROM sections");
             rs = stmt.getResultSet();

            int count = 0;
            while (rs.next() && count < 11) {
                int section_code = rs.getInt(1);
                String dept = rs.getString(2);
                int course = rs.getInt(3);
                Time time = rs.getTime(4);


                Random r = new Random(123791728);

                for (int i = 0; i < 10 + r.nextInt(4); i++) {
                    int a = r.nextInt(students.size() - 1);
                    while(numenrollments.get(a) > 7 || addedAlready.get(a).contains(section_code)) {
                        a = r.nextInt(students.size() - 1);
                    }
                    var student = students.get(a);

                    addedAlready.get(a).add(section_code);
                    System.out.println("INSERT INTO enrollments VALUES (" +
                            "( SELECT sectioncode FROM sections WHERE " +
                            "dept = '" + dept + "' AND course = " + course + " AND starttime = '" + time + "' " +
                            "LIMIT 1)" +
                            ", " + student + ", " + r.nextInt(4) + ");");
                }
                count++;
            }




        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }
}
