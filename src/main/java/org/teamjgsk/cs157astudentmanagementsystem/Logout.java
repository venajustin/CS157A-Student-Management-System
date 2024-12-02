package org.teamjgsk.cs157astudentmanagementsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "logout", value = "/api/accounts/logout")
public class Logout extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        var out = res.getWriter();
        HttpSession session = req.getSession();
        session.removeAttribute("userid");
        var url = req.getRequestURL();
        res.setHeader("HX-Redirect", url.substring(0, url.indexOf("/api")));
    }

}
