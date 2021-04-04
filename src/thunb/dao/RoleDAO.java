/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import thunb.dto.RoleDTO;

/**
 *
 * @author Banh Bao
 */
public class RoleDAO {

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

    public RoleDTO getDTOByRoleID(String roleID) throws Exception {
        RoleDTO dto = null;
        String roleName, castID, castName, roleDes;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                String sql = "SELECT RoleN, RoleDes, CastID, CastN FROM ROLE "
                        + "WHERE RoleID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, roleID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    roleName = rs.getString("RoleN");
                    castID = rs.getString("CastID");
                    castName = rs.getString("CastN");
                    roleDes = rs.getString("RoleDes");
                    dto = new RoleDTO(roleID, roleName, castID, castName, roleDes);
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public List<RoleDTO> getDTOByCastID(String castID) throws Exception {
        List<RoleDTO> result = null;
        RoleDTO dto = null;
        String roleID, roleName, castName, roleDes;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                result = new ArrayList<>();
                String sql = "SELECT RoleID, RoleN, RoleDes, CastN FROM ROLE "
                        + "WHERE CastID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, castID);
                rs = pst.executeQuery();
                while (rs.next()) {
                    roleID = rs.getString("RoleID");
                    roleName = rs.getString("RoleN");
                    castName = rs.getString("CastN");
                    roleDes = rs.getString("RoleDes");
                    dto = new RoleDTO(roleID, roleName, castID, castName, roleDes);
                    result.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<RoleDTO> getAllDTO() throws Exception {
        List<RoleDTO> result = null;
        RoleDTO dto = null;
        String roleID, roleName, castID, castName, roleDes;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                String sql = "SELECT RoleID, RoleN, RoleDes, CastID, CastN FROM ROLE";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    roleID = rs.getString("RoleID");
                    roleName = rs.getString("RoleN");
                    castID = rs.getString("CastID");
                    castName = rs.getString("CastN");
                    roleDes = rs.getString("RoleDes");
                    dto = new RoleDTO(roleID, roleName, castID, castName, roleDes);
                    result.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<RoleDTO> findByLikeName(String search) throws Exception {
        List<RoleDTO> result = null;
        RoleDTO dto = null;
        String roleID = null;
        String name = null;
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            if (cn != null) {
                result = new ArrayList<>();

                String sql = "SELECT RoleID, RoleN FROM ROLE "
                        + "WHERE RoleN LIKE ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + search + "%");
                rs = pst.executeQuery();

                while (rs.next()) {
                    roleID = rs.getString("RoleID");
                    name = rs.getString("RoleN");
                    dto = new RoleDTO(roleID, name);
                    result.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean delete(String roleID) throws Exception {
        boolean check = false;
        try {
            String sql1 = "DELETE FROM ROLE_DISTRIBUTE WHERE RoleID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql1);
            pst.setString(1, roleID);
            pst.executeUpdate();

            String sql = "DELETE FROM ROLE WHERE RoleID = ?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, roleID);
            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean update(RoleDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE ROLE SET RoleN = ?, "
                    + "RoleDes = ?, CastID = ?, CastN = ? "
                    + "WHERE RoleID = ?";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);

            pst.setString(1, dto.getRoleName());
            pst.setString(2, dto.getRoleDes());
            if (dto.getCastID().equals("")) {
                pst.setNull(3, java.sql.Types.VARCHAR);
                pst.setNull(4, java.sql.Types.VARCHAR);
            } else {
                pst.setString(3, dto.getCastID());
                pst.setString(4, dto.getCastName());
            }
            pst.setString(5, dto.getRoleID());

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean insert(RoleDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO ROLE (RoleID, RoleN, RoleDes, CastID, CastN) "
                    + "VALUES (?,?,?,?,?)";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);

            pst.setString(1, dto.getRoleID());
            pst.setString(2, dto.getRoleName());
            pst.setString(3, dto.getRoleDes());
            if (dto.getCastID().equals("")) {
                pst.setNull(4, java.sql.Types.VARCHAR);
                pst.setNull(5, java.sql.Types.VARCHAR);
            } else {
                pst.setString(4, dto.getCastID());
                pst.setString(5, dto.getCastName());
            }

            check = pst.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public String loadRoleDesFromFile(String filepath) {
        String description = "";
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(filepath);
            br = new BufferedReader(fr);
            String s = "";
            while ((s = br.readLine()) != null) {
                description += s + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return description;
    }

    public void exportRoleDesFile(String path, String des) {
        PrintWriter w = null;
        try {
            w = new PrintWriter(path);
            w.println(des);
            w.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (w != null) {
                    w.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//    
//    public boolean copyRoleDesFile(String inputPath, String downloadPath) throws IOException{
//        boolean check = false;
//        InputStream inStream = null;
//        OutputStream outStream = null;
// 
//        try {
//            inStream = new FileInputStream(new File(inputPath));
//            outStream = new FileOutputStream(new File(downloadPath));
//            int length;
//            byte[] buffer = new byte[1024];
//            // copy the file content in bytes
//            while ((length = inStream.read(buffer)) > 0) {
//                outStream.write(buffer, 0, length);
//            }
//            check = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            inStream.close();
//            outStream.close();
//        }
//        return check;
//    }
//    
    public boolean cpyFile(String source, String des, JProgressBar prbUpload) throws Exception{
        byte[] rs = null;
        File f = new File(source);
        FileInputStream fi = null;
        FileOutputStream fo = null;
        if(f.exists() && f.isFile())
        {
            fi = new FileInputStream(f);
            int size = fi.available();
            rs = new byte[size];
            fi.read(rs);
            fi.close();
            fo = new FileOutputStream(des);
            fo.write(rs);
            fo.flush();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 101; i++) {
                            int value = prbUpload.getValue();
                            if (value < 100) {
                                prbUpload.setValue(value + 1);
                            } else {
                                JOptionPane.showMessageDialog(null, "Done!");
                            }
                            Thread.sleep(5);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
            t.start();
            fo.close();
        }
            
        return true;
    }
    
}
