/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dao;

import thunb.dto.CastDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import thunb.dbutil.Util;
import thunb.dto.NotificationDTO;

/**
 *
 * @author Banh Bao
 */
public class CastDAO {

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

    public CastDTO getDTOByID(String castID) throws Exception {
        CastDTO dto = null;
        String name, image, des, phone, email, status;
        int numNoti;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                String sql = "SELECT CastID, CastName, CastImage,"
                        + " CastDes, CastPhone, CastEmail, CastStatus, "
                        + "NumberOfNoti FROM CAST WHERE CastID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, castID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    name = rs.getString("CastName");
                    image = rs.getString("CastImage");
                    des = rs.getString("CastDes");
                    phone = rs.getString("CastPhone");
                    email = rs.getString("CastEmail");
                    status = rs.getString("CastStatus");
                    numNoti = rs.getInt("NumberOfNoti");
                    dto = new CastDTO(castID, name, image, des, phone, email, status, numNoti);
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public CastDTO getDTOByUsername(String username) throws Exception {
        CastDTO dto = null;
        String castID, name, image, des, phone, email, status;
        int numNoti;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                String sql = "SELECT CastID, CastName, CastImage, CastDes, CastPhone, "
                        + "CastEmail, CastStatus, NumberOfNoti FROM CAST "
                        + "WHERE Username = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, username);
                rs = pst.executeQuery();
                if (rs.next()) {
                    castID = rs.getString("castID");
                    name = rs.getString("CastName");
                    image = rs.getString("CastImage");
                    des = rs.getString("CastDes");
                    phone = rs.getString("CastPhone");
                    email = rs.getString("CastEmail");
                    status = rs.getString("CastStatus");
                    numNoti = rs.getInt("NumberOfNoti");
                    dto = new CastDTO(castID, name, image, des, phone, email, status, numNoti);
                    dto.setUsername(username);
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public List<CastDTO> getAllDTO() throws Exception {
        List<CastDTO> result = null;
        CastDTO dto = null;
        String castID, name;
        try {
            String sql = "SELECT CastID, CastName FROM CAST";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                castID = rs.getString("CastID");
                name = rs.getString("CastName");
                dto = new CastDTO(castID, name);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<CastDTO> findByLikeName(String search) throws Exception {
        List<CastDTO> result = null;
        CastDTO dto = null;
        String castID = null;
        String name = null;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                result = new ArrayList<>();

                String sql = "SELECT CastID, CastName FROM CAST "
                        + "WHERE CastName LIKE ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + search + "%");
                rs = pst.executeQuery();

                while (rs.next()) {
                    castID = rs.getString("CastID");
                    name = rs.getString("CastName");
                    dto = new CastDTO(castID, name);
                    result.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public String findIDByName(String search) throws Exception {
        String castID = null;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                String sql = "SELECT CastID FROM CAST "
                        + "WHERE CastName = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, search);
                rs = pst.executeQuery();

                if (rs.next()) {
                    castID = rs.getString("CastID");
                }
            }
        } finally {
            closeConnection();
        }
        return castID;
    }

    public boolean delete(String castID) throws Exception {
        boolean check = false;
        ArrayList<String> listRoleID = new ArrayList<>();
        String username = "";
        try {

            String sql1 = "SELECT RoleID "
                    + "FROM ROLE INNER JOIN CAST ON CAST.CastID = ROLE.CastID "
                    + "WHERE CAST.CastID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql1);
            pst.setString(1, castID);
            rs = pst.executeQuery();
            while (rs.next()) {
                listRoleID.add(rs.getString("RoleID"));
            }
            if (!listRoleID.isEmpty()) {
                RoleDAO daoR = new RoleDAO();
                for (String rID : listRoleID) {
                    daoR.delete(rID);
                }
            }

            NotificationDAO daoN = new NotificationDAO();
            daoN.delete(castID);

            String sql2 = "SELECT CAST.Username FROM dbo.[USER] INNER JOIN CAST "
                    + "ON CAST.Username = [USER].Username WHERE CastID = ?";
            pst = cn.prepareStatement(sql2);
            pst.setString(1, castID);
            rs = pst.executeQuery();
            if (rs.next()) {
                username = rs.getString("Username");
            }

            String sql = "DELETE FROM CAST WHERE CastID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, castID);
            check = pst.executeUpdate() > 0;

            if (!username.equals("")) {
                UserDAO daoU = new UserDAO();
                daoU.delete(username);
            }

        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean update(CastDTO dto) throws Exception {
        boolean check = false;
        try {

            String sql1 = "UPDATE ROLE SET CastN = ? WHERE CastID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql1);
            pst.setString(1, dto.getName());
            pst.setString(2, dto.getCastID());
            pst.executeUpdate();

            String sql = "UPDATE CAST SET CastName = ?, CastImage = ?, "
                    + "CastDes = ?, CastPhone = ?, CastEmail = ?, "
                    + "CastStatus = ?, NumberOfNoti = ? "
                    + "WHERE CastID = ?";
            pst = cn.prepareStatement(sql);

            pst.setString(1, dto.getName());
            pst.setString(2, dto.getImage());
            pst.setString(3, dto.getDes());
            pst.setString(4, dto.getPhone());
            pst.setString(5, dto.getEmail());
            pst.setString(6, dto.getStatus());
            pst.setInt(7, dto.getNumNoti());
            pst.setString(8, dto.getCastID());

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean insert(CastDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO CAST(CastID, Username,"
                    + "CastName ,CastImage ,CastDes ,CastPhone ,"
                    + "CastEmail ,CastStatus ,NumberOfNoti) "
                    + "VALUES  (?,?,?,?,?,?,?,?,?)";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);

            pst.setString(1, dto.getCastID());
            pst.setString(2, "cast" + dto.getCastID());
            pst.setString(3, dto.getName());
            pst.setString(4, dto.getImage());
            pst.setString(5, dto.getDes());
            pst.setString(6, dto.getPhone());
            pst.setString(7, dto.getEmail());
            pst.setString(8, dto.getStatus());
            pst.setInt(9, dto.getNumNoti());

            check = pst.executeUpdate() > 0;
            if (check) {
                NotificationDTO dtoN = new NotificationDTO();
                dtoN = Util.createNotification(dto.getCastID(), "Create", "Create account", "admin");
                NotificationDAO dao = new NotificationDAO();
                dao.insert(dtoN);
            }
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean updateProfile(String username, String name, String image, String phone, String email) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE CAST SET CastName = ?, CastImage = ?, CastPhone = ?,"
                    + " CastEmail = ? WHERE Username = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);

            pst.setString(1, name);
            pst.setString(2, image);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setString(5, username);

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    

}
