/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dao;

import thunb.dto.NotificationDTO;
import com.sun.org.apache.bcel.internal.classfile.Code;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Banh Bao
 */
public class NotificationDAO implements Serializable {

    private Connection cn;
    private PreparedStatement pst;
    private ResultSet rs;

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

    public List<NotificationDTO> getNotiByCastID(String castID) throws Exception {
        List<NotificationDTO> result = null;
        NotificationDTO dto = null;
        String notiID, notiAct, notiDes, timeUp;

        try {
            String sql = "SELECT NotiID, NotiAction, NotiDes, TimeUp FROM dbo.NOTIFICATION "
                    + "WHERE  CastID = ? ORDER BY LEN(NotiID), NotiID";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, castID);
            rs = pst.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                notiID = rs.getString("NotiID");
                notiAct = rs.getString("NotiAction");
                notiDes = rs.getString("NotiDes");
                timeUp = rs.getString("TimeUp");
                dto = new NotificationDTO(notiID, notiAct, notiDes, castID, timeUp);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean delete(String castID) throws Exception {
        boolean check = false;
        try {
            
            String sql = "DELETE FROM NOTIFICATION WHERE CastID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, castID);
            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean insert(NotificationDTO dto) throws Exception{
        boolean check = false;
        try {
            String sql = "INSERT INTO NOTIFICATION "
                    + "(NotiID, NotiAction, NotiDes, CastID, TimeUp) "
                    + "VALUES  (?,?,?,?,?)";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            
            pst.setString(1, dto.getNotiID());
            pst.setString(2, dto.getNotiAct());
            pst.setString(3, dto.getNotiDes());
            pst.setString(4, dto.getCastID());
            pst.setString(5, dto.getTimeUp());
            
            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    
}
