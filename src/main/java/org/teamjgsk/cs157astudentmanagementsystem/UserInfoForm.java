package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "userinfo", value = "/api/accounts/userinfoform")
public class UserInfoForm  extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        var out = res.getWriter();

        HttpSession session = req.getSession();
        var url = req.getRequestURL();

        if (session.getAttribute("userid") != null) {
            var uid = (Integer) session.getAttribute("userid");
            try {
                var conn = DatabaseConnection.getConnection();
                var pstmt = conn.prepareStatement("SELECT " +
                        "name, " +
                        "email, " +
                        "birthdate, " +
                        "major, " +
                        "minor, " +
                        "gpa, " +
                        "unitscompleted " +
                        "FROM Students " +
                        "WHERE studentid = ?");

                pstmt.setInt(1, uid);

                pstmt.executeQuery();

                var rs = pstmt.getResultSet();

                if (rs.next()) {
                    var name = rs.getString(1);
                    var email = rs.getString(2);
                    var birthdate = rs.getString(3);
                    var major = rs.getString(4);
                    var minor = rs.getString(5);
                    var gpa = rs.getString(6);

                    InitialContext ctx = new InitialContext();
                    var templatePath = "../templates/UserInfoForm.html";

                    try {
                        File f = new File("./");
                        for (var each : f.listFiles()) {
                            System.out.println(each.toPath().toString());
                        }

                        String template = new String(Files.readAllBytes(Paths.get(templatePath)));

                        Map<String, String> placeholders = new HashMap<>();
                        placeholders.put("name", name);
                        placeholders.put("email", email);
                        placeholders.put("birthdate", birthdate);
                        placeholders.put("major", major);
                        placeholders.put("minor", minor);
                        placeholders.put("gpa", gpa);

                        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                            template = template.replace("${" + entry.getKey() + "}", entry.getValue());
                        }

                        out.println(template);
                    } catch (Exception e) {
                        System.out.println("failed to read file" + templatePath);
                        res.sendError(500);
                    }


                } else {
                    res.setHeader("HX-Redirect", url.substring(0, url.indexOf("/api/")));

                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
                out.println("ERROR in backend");
                res.sendError(400);
                return;
            }
        } else {

            res.setHeader("HX-Redirect", url.substring(0, url.indexOf("/api/")));

        }


    }
}
