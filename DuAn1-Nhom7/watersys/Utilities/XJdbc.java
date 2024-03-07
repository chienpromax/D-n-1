/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watersys.Utilities;

import java.sql.*;

/**
 *
 * @author balis
 */
public class XJdbc {

    public static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=QL_BanNuoc";
    public static String username = "sa";
    public static String password = "123";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement preparedStatement(String sql, Object... args) throws SQLException {
        Connection conn = DriverManager.getConnection(dburl, username, password);
        PreparedStatement pstmt = null;
        if (sql.trim().startsWith("{")) {
            pstmt = conn.prepareCall(sql); //proc
        } else {
            pstmt = conn.prepareStatement(sql); //SQL
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement pstmt = preparedStatement(sql, args);
            try {
                return pstmt.executeQuery();
            } finally {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeUpdate(String sql, Object... args) {
        try {
            PreparedStatement pstmt = preparedStatement(sql, args);
            try {
                pstmt.executeUpdate();
            } finally {
                pstmt.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getString(String SELECT_TENSP_SQL, String maHD) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
