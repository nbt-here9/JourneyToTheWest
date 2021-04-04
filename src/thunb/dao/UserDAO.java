/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dao;

import thunb.dto.UserDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Banh Bao
 */
public class UserDAO implements Serializable {

    private Connection cn;
    private PreparedStatement pst;
    private ResultSet rs;

    public UserDAO() {
    }

    //Close connection
    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (cn != null) {
            cn.close();
        }
    }

    //Check login
    public String checkLogin(String username, String password) throws Exception {
        String actor = "failed";
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            String sql = "SELECT Actor FROM dbo.[USER] "
                    + "WHERE Username=? AND Password=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
            //4. Tra ve ket qua
            if (rs.next()) {
                actor = rs.getString("Actor");
            }
        } finally {
            closeConnection();
        }
        return actor;
    }

//    public String getCastIDByUsername(String username) throws Exception {
//        String result = null;
//        try {
//            cn = thunb.dbutil.MyConnection.getConnection();
//            String sql = "SELECT Password FROM dbo.[USER] WHERE Username = ?";
//            pst = cn.prepareStatement(sql);
//            pst.setString(1, username);
//            rs = pst.executeQuery();
//
//            if (rs.next()) {
//                result = rs.getString("Password");
//            }
//        } finally {
//            closeConnection();
//        }
//        return result;
//    }

    public boolean delete(String username) throws Exception {
        boolean check = false;
        try {
            String sql = "DELETE FROM dbo.[USER] WHERE Username = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, username);
            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean update(String username, String pass) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE dbo.[USER] SET Password = ? WHERE Username = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, pass);
            pst.setString(2, username);

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean insert(UserDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO dbo.[USER] ( Username, Password, Actor ) "
                    + "VALUES (?,?,?)";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, dto.getUsername());
            pst.setString(2, dto.getPassword());
            pst.setString(3, dto.getActor());

            check = pst.executeUpdate() > 0;

        } finally {
            closeConnection();
        }
        return check;
    }
}
