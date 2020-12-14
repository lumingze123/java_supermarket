package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc_Conn {
    public Connection conn = null;

    protected void jdbc() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://8.129.121.90:3306/test?useSSL=false";
            String user = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("111"+e.getMessage());
        }
    }
}
