/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dbutil;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Banh Bao
 */
public class MyConnection implements Serializable {

    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection cn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=PRJ_ASSIGNMENT", "sa", "123456");
        return cn;
    }
}
