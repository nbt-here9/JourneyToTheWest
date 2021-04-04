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
import thunb.dto.RoleDistributionDTO;

/**
 *
 * @author Banh Bao
 */
public class RoleDistributionDAO {

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
    
    public List<RoleDistributionDTO> getAllDTOOfTri(String triID) throws Exception {
        List<RoleDistributionDTO> result = null;
        RoleDistributionDTO dto = null;
        String roleID;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                String sql = "SELECT TriID, RoleID FROM ROLE_DISTRIBUTE "
                        + "WHERE TriID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, triID);
                rs = pst.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    roleID = rs.getString("RoleID");
                    dto = new RoleDistributionDTO(triID, roleID);
                    result.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean delete(String triID, String roleID) throws Exception {
        boolean check = false;
        try {
            String sql = "DELETE FROM ROLE_DISTRIBUTE WHERE TriID = ? AND RoleID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, triID);
            pst.setString(2, roleID);
            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean insert(RoleDistributionDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO ROLE_DISTRIBUTE (TriID, RoleID) "
                    + "VALUES (?,?)";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);

            pst.setString(1, dto.getTriID());
            pst.setString(2, dto.getRoleID());

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
}
