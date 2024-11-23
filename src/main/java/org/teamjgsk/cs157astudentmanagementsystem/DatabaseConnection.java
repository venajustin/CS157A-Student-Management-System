package org.teamjgsk.cs157astudentmanagementsystem;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DatabaseConnection {
    public static Connection getConnection() throws NamingException, SQLException {
        InitialContext ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MyPostgresDB");
        return ds.getConnection();
    }
}
