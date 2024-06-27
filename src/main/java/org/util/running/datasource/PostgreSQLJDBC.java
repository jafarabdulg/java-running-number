package org.util.running.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLJDBC {

    public static Connection getDataSource() throws Exception {
        Connection conn = null;
        final String url = "URL";
        final String user = "USER";
        final String password = "PASSWORD";
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return conn;
    }
}
