/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dao;

import thunb.dto.TribulationDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Banh Bao
 */
public class TribulationDAO {

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

    public TribulationDTO getDTOByID(String triID) throws Exception {
        TribulationDTO dto = null;
        String name, des, locate, startDate, endDate;
        int numNG;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                String sql = "SELECT TriName, TriDes, TriLocate, "
                        + "StartDate, EndDate, NG FROM TRIBULATION "
                        + "WHERE TriID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, triID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    name = rs.getString("TriName");
                    des = rs.getString("TriDes");
                    locate = rs.getString("TriLocate");
                    startDate = rs.getString("StartDate");
                    endDate = rs.getString("EndDate");
                    numNG = rs.getInt("NG");
                    dto = new TribulationDTO(triID, name, des, locate, startDate, endDate, numNG);
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public List<TribulationDTO> getAllDTO() throws Exception {
        List<TribulationDTO> result = null;
        TribulationDTO dto = null;
        String triID, name;
        try {
            String sql = "SELECT TriID, TriName FROM TRIBULATION";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                triID = rs.getString("TriID");
                name = rs.getString("TriName");
                dto = new TribulationDTO(triID, name);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<TribulationDTO> getAllSEDateDTO() throws Exception {
        List<TribulationDTO> result = null;
        TribulationDTO dto = null;
        String triID, startD, endD;
        try {
            String sql = "SELECT TriID, StartDate, EndDate FROM TRIBULATION";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                triID = rs.getString("TriID");
                startD = rs.getString("StartDate");
                endD = rs.getString("EndDate");
                dto = new TribulationDTO(triID, startD, endD);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<TribulationDTO> findByLikeName(String search) throws Exception {
        List<TribulationDTO> result = null;
        TribulationDTO dto = null;
        String triID = null;
        String name = null;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                result = new ArrayList<>();

                String sql = "SELECT TriID, TriName FROM TRIBULATION "
                        + "WHERE TriName LIKE ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + search + "%");
                rs = pst.executeQuery();

                while (rs.next()) {
                    triID = rs.getString("TriID");
                    name = rs.getString("TriName");
                    dto = new TribulationDTO(triID, name);
                    result.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean delete(String triID) throws Exception {
        boolean check = false;
        try {
            String sql1 = "DELETE FROM PROPSUSED WHERE TriID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql1);
            pst.setString(1, triID);
            pst.executeUpdate();
            
            String sql2 = "DELETE FROM ROLE_DISTRIBUTE WHERE TriID = ?";
            pst = cn.prepareStatement(sql2);
            pst.setString(1, triID);
            pst.executeUpdate();
            
            String sql = "DELETE FROM TRIBULATION WHERE TriID = ?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, triID);
            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean update(TribulationDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE TRIBULATION SET TriName = ?, "
                    + "TriDes = ?, TriLocate = ?, StartDate = ?,"
                    + " EndDate = ?, NG = ? WHERE TriID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            
            pst.setString(1, dto.getName());
            pst.setString(2, dto.getDes());
            pst.setString(3, dto.getLocate());
            pst.setString(4, dto.getStartDate());
            pst.setString(5, dto.getEndDate());
            pst.setInt(6, dto.getNumNG());
            pst.setString(7, dto.getTriID());

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean insert(TribulationDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO TRIBULATION (TriID,TriName, "
                    + "TriDes, TriLocate, StartDate, EndDate, NG) "
                    + "VALUES (?,?,?,?,?,?,?)";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            
            pst.setString(1, dto.getTriID());
            pst.setString(2, dto.getName());
            pst.setString(3, dto.getDes());
            pst.setString(4, dto.getLocate());
            pst.setString(5, dto.getStartDate());
            pst.setString(6, dto.getEndDate());
            pst.setInt(7, dto.getNumNG());

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
}
