package com.example.movieappschool.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {

    public boolean doesUserExist(String mUsername, String mPassword) {
        String connectionUrl = "jdbc:jtds:sqlserver://aei-sql2.avans.nl:1443/CinemaApplicationDB";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);

            String SQL = "SELECT Username, Password FROM User WHERE Username = '" + mUsername + "' AND Password = '" + mPassword + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                rs.close();
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
        return false;
    }
}
