/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dao;

import thunb.dto.PropsDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Banh Bao
 */
public class PropstDAO implements Serializable {

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

    public PropsDTO getDTOByID(String propsID) throws Exception {
        PropsDTO dto = null;
        String name, des, image;
        int quantity;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                String sql = "SELECT PropsID, PropsName, PropsDes, "
                        + "PropsImage, PropsQuantity FROM PROPS "
                        + "WHERE PropsID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, propsID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    name = rs.getString("PropsName");
                    des = rs.getString("PropsDes");
                    image = rs.getString("PropsImage");
                    quantity = rs.getInt("PropsQuantity");
                    dto = new PropsDTO(propsID, name, des, image, quantity);
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public List<PropsDTO> getAllDTO() throws Exception {
        List<PropsDTO> result = null;
        PropsDTO dto = null;
        String propsID, name;
        int quantity;
        try {
            String sql = "SELECT PropsID, PropsName, PropsQuantity FROM PROPS";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                propsID = rs.getString("PropsID");
                name = rs.getString("PropsName");
                quantity = rs.getInt("PropsQuantity");
                dto = new PropsDTO(propsID, name, quantity);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<PropsDTO> findByLikeName(String search) throws Exception {
        List<PropsDTO> result = null;
        PropsDTO dto = null;
        String propsID = null;
        String name = null;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                result = new ArrayList<>();

                String sql = "SELECT PropsID, PropsName "
                        + "FROM PROPS WHERE PropsName LIKE ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + search + "%");
                rs = pst.executeQuery();

                while (rs.next()) {
                    propsID = rs.getString("PropsID");
                    name = rs.getString("PropsName");
                    dto = new PropsDTO(propsID, name);
                    result.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<PropsDTO> getAllDTONotUsed() throws Exception {
        List<PropsDTO> result = null;
        PropsDTO dto = null;
        String propsID, name;
        int quantity;
        try {
            String sql = "SELECT PropsID, PropsName, PropsQuantity FROM PROPS \n"
                    + "WHERE PropsID NOT IN (SELECT PropsID FROM PROPSUSED)";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                propsID = rs.getString("PropsID");
                name = rs.getString("PropsName");
                quantity = rs.getInt("PropsQuantity");
                dto = new PropsDTO(propsID, name, quantity);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean delete(String propsID) throws Exception {
        boolean check = false;
        try {

            String sql1 = "DELETE FROM PROPSUSED WHERE PropsID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql1);
            pst.setString(1, propsID);
            pst.executeUpdate();

            String sql = "DELETE  FROM PROPS WHERE PropsID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, propsID);
            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean update(PropsDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql1 = "DELETE FROM PROPSUSED WHERE PropsID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql1);
            pst.setString(1, dto.getPropsID());
            pst.executeUpdate();

            String sql = "UPDATE PROPS SET PropsName = ?, "
                    + "PropsDes = ?, PropsImage = ?, "
                    + "PropsQuantity = ? WHERE PropsID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);

            pst.setString(1, dto.getName());
            pst.setString(2, dto.getDes());
            pst.setString(3, dto.getImage());
            pst.setInt(4, dto.getQuantity());
            pst.setString(5, dto.getPropsID());

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean insert(PropsDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO PROPS (PropsID, PropsName, "
                    + "PropsDes, PropsImage, PropsQuantity) "
                    + "VALUES (?,?,?,?,?)";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);

            pst.setString(1, dto.getPropsID());
            pst.setString(2, dto.getName());
            pst.setString(3, dto.getDes());
            pst.setString(4, dto.getImage());
            pst.setInt(5, dto.getQuantity());

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
}
