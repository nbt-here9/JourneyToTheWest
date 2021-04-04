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
import thunb.dto.ScheduleDTO;

/**
 *
 * @author Banh Bao
 */
public class ScheduleDAO {

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

    public List<ScheduleDTO> getAllDTOByCastID(String castID) throws Exception {
        List<ScheduleDTO> result =null;
        ScheduleDTO dto = null;
        String triName, roleName, triLocate, startDate, endDate;
        try {
            
            String sql = "SELECT TriName, RoleN, TriLocate, StartDate ,　EndDate　"
                    + "FROM TRIBULATION INNER JOIN ROLE_DISTRIBUTE "
                    + "ON ROLE_DISTRIBUTE.TriID = TRIBULATION.TriID "
                    + "INNER JOIN ROLE ON ROLE.RoleID = ROLE_DISTRIBUTE.RoleID "
                    + "WHERE  CastID =  ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, castID);
            rs = pst.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                triName = rs.getString("TriName");
                roleName = rs.getString("RoleN");
                triLocate = rs.getString("TriLocate");
                startDate = rs.getString("StartDate");
                endDate = rs.getString("EndDate");
                dto = new ScheduleDTO(triName, roleName, triLocate, startDate, endDate);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
