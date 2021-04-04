/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import thunb.dto.PropsUsedDTO;

/**
 *
 * @author Banh Bao
 */
public class PropsUsedDAO {

    private Connection cn;
    private PreparedStatement pst;
    private ResultSet rs;

    public Vector getPropsUsedInfoForStatictis(PropsUsedDTO dto) throws Exception {
        Vector result = new Vector();
        String propsName = null, triName = null, startdate = null, enddate = null;
        int quantity = 0;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            String sql1 = "SELECT PropsName FROM PROPS WHERE PropsID = ?";
            pst = cn.prepareStatement(sql1);
            pst.setString(1, dto.getPropsID());
            rs = pst.executeQuery();
            if (rs.next()) {
                propsName = rs.getString("PropsName");
            }

            String sql3 = "SELECT TriName, StartDate, EndDate FROM TRIBULATION WHERE TriID = ?";
            pst = cn.prepareStatement(sql3);
            pst.setString(1, dto.getTriID());
            rs = pst.executeQuery();
            if (rs.next()) {
                triName = rs.getString("TriName");
                startdate = rs.getString("StartDate");
                enddate = rs.getString("EndDate");
            }

            result.add(dto.getPropsID());
            result.add(propsName);
            result.add(dto.getQuantity());
            result.add(triName);
            result.add(startdate);
            result.add(enddate);

        } finally {
            closeConnection();
        }
        return result;
    }

    public List<PropsUsedDTO> getAllDTO() throws Exception {
        List<PropsUsedDTO> result = null;
        PropsUsedDTO dto = null;
        String triID, propsID;
        int quantityUse;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            String sql = "SELECT TriID, PropsID, QuantityUse FROM PROPSUSED";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                triID = rs.getString("TriID");
                propsID = rs.getString("PropsID");
                quantityUse = rs.getInt("QuantityUse");
                dto = new PropsUsedDTO(triID, propsID, quantityUse);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getQuantityUsedByTriIDPropsID(String triID, String propsID) throws Exception {
        PropsUsedDTO dto = null;
        int quantityUse = 0;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            String sql = "SELECT QuantityUse FROM PROPSUSED WHERE TriID = ? AND PropsID = ?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, triID);
            pst.setString(2, propsID);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                quantityUse = rs.getInt("QuantityUse");
            }
        } finally {
            closeConnection();
        }
        return quantityUse;
    }
    
    public List<PropsUsedDTO> getAllDTOByTriID(String triID) throws Exception {
        List<PropsUsedDTO> result = null;
        PropsUsedDTO dto = null;
        String propsID;
        int quantityUse;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            String sql = "SELECT PropsID, QuantityUse FROM PROPSUSED WHERE TriID = ? ";
            pst = cn.prepareStatement(sql);
            pst.setString(1, triID);
            rs = pst.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                propsID = rs.getString("PropsID");
                quantityUse = rs.getInt("QuantityUse");
                dto = new PropsUsedDTO(triID, propsID, quantityUse);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public String getPropsNameByPropsID(String propsID) throws Exception {
        String name = "";
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                String sql = "SELECT PropsName FROM PROPS WHERE PropsID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, propsID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    name = rs.getString("PropsName");
                }
            }
        } finally {
            closeConnection();
        }
        return name;
    }
    
    public boolean delete(String triID, String propsID) throws Exception {
        boolean check = false;
        try {
            String sql1 = "DELETE FROM PROPSUSED WHERE TriID = ? AND PropsID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql1);
            pst.setString(1, triID);
            pst.setString(2, propsID);
            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean update(PropsUsedDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE PROPSUSED SET QuantityUse = ? WHERE TriID = ? AND PropsID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setInt(1, dto.getQuantity());
            pst.setString(2, dto.getTriID());
            pst.setString(3, dto.getPropsID());

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean insert(PropsUsedDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO PROPSUSED ( TriID, PropsID, QuantityUse ) VALUES  (?,?,?)";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);

            pst.setString(1, dto.getTriID());
            pst.setString(2, dto.getPropsID());
            pst.setInt(3, dto.getQuantity());

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

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

}
