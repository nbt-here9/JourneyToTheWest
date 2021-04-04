/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dbutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JComboBox;
import thunb.dto.NotificationDTO;
import thunb.dto.TribulationDTO;
import thunb.dao.TribulationDAO;

/**
 *
 * @author Banh Bao
 */
public class Util {

    public static Connection cn;
    public static PreparedStatement pst;
    public static ResultSet rs;

    public static void loadDataForCombobox(String sql, JComboBox cbb) throws Exception {
        try {
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cbb.addItem(rs.getString(1) + " - " + rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

    }

    public static String createIDAutomatic(String table, String field) throws Exception {
        String newID = "";
        String firstChar = "";
        try {
            //String sql = "SELECT TOP 1 " + field + " FROM " + table + " ORDER BY " + field + " DESC";
            String sql = "SELECT " + field
                    + " FROM " + table + 
                    " WHERE " + field + " NOT IN (SELECT TOP (SELECT COUNT(" + field + ")-1 FROM " + table + ") " + field + " FROM " + table + " ORDER BY LEN(" + field + "), " + field + ")";
            cn = thunb.dbutil.MyConnection.getConnection();
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                newID = rs.getString(field);
                firstChar = newID.substring(0, 1);
                int count = Integer.parseInt(newID.substring(1)) + 1;
                newID = firstChar + count;
            } else {
                newID = table.substring(0, 1) + "1";
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return newID;
    }

    public static NotificationDTO createNotification(String castID, String notiAct, String notiDes, String who) throws Exception {
        NotificationDTO dto = null;
        String notiID, timeUp;
        notiID = createIDAutomatic("NOTIFICATION", "NotiID");
        notiDes = notiDes + " (By " + who + ")";
        Calendar cd = Calendar.getInstance();
        Timestamp time = new Timestamp(cd.getTimeInMillis());
        timeUp = time.toString();
        dto = new NotificationDTO(notiID, notiAct, notiDes, castID, timeUp);
        return dto;
    }

    public static void closeConnection() throws Exception {
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

    public static String checkInputName(String inputName) {
        if (inputName.isEmpty()) {
            return "Name can not empty!";
        } else {
            if (!inputName.matches("^[a-zA-Z\\s]*$")) {
                return "Name can not have special character!";
            }
        }
        return "";
    }

    public static String checkInputNameCanEmpty(String inputName) {
        if (!inputName.matches("^[a-zA-Z\\s]*$")) {
            return "Name can not have special character!";
        }
        return "";
    }

    public static String checkInputIntNumber(String name, String inputNumber) {
        if (!inputNumber.trim().equals("")) {

            if (inputNumber.matches("^[0-9]*$")) {
                int inputInt = Integer.parseInt(inputNumber);
                if (inputInt < 0) {
                    return "Data type of " + name + " is positive integer number!";
                }
            } else {
                return "Data type of " + name + " is positive integer number!";
            }
        } else {
            return name + " can not empty!";
        }
        return "";
    }

    public static String checkInputPhone(String inputPhone) {
        if (!inputPhone.trim().equals("")) {
            if (!inputPhone.matches("^[0-9]{10}$")) {
                return "Phone number must have ten numbers!";
            } else {
                return "";
            }
        } else {
            return "Phone number can not empty!";
        }
    }

    public static String checkInputEmail(String inputEmail) {
        if (!inputEmail.trim().equals("")) {
            if (!inputEmail.matches("^[A-Za-z0-9.+-_%]+[^.]+@{1}+[^.][A-Za-z.-]+\\.[A-Za-z]{2,}$")) {
                return "Format of email: abc@def.com";
            } else {
                return "";
            }
        } else {
            return "Email can not empty!";
        }
    }

    public static String checkInputSEDate(String start, String end) throws Exception {
        Date sd = null, ed = null;
        String valid = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            sd = new Date(sdf.parse(start).getTime());
        } catch (ParseException e) {
            valid += "Invalid start date!\n";
        }
        try {
            ed = new Date(sdf.parse(end).getTime());
        } catch (ParseException e) {
            valid += "Invalid end date!\n";
        }
        if (valid.equals("")) {
            if (ed.getTime() < sd.getTime()) {
                valid += "Start date must be after end date!\n";
            }
        }
        return valid;
    }

    public static String checkInputSEDateAtTheSameTime(String start, String end) throws Exception {
        Date sd = null, ed = null, sdCheck = null, edCheck = null;
        String valid = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            sd = new Date(sdf.parse(start).getTime());
        } catch (ParseException e) {
            valid += "Invalid start date!\n";
        }
        try {
            ed = new Date(sdf.parse(end).getTime());
        } catch (ParseException e) {
            valid += "Invalid end date!\n";
        }
        if (valid.equals("")) {
            if (ed.getTime() < sd.getTime()) {
                valid += "Start date must be after end date!\n";
            }
        }
        if (valid.equals("")) {
            TribulationDAO dao = new TribulationDAO();
            List<TribulationDTO> dtoList = dao.getAllSEDateDTO();
            for (TribulationDTO tri : dtoList) {
                sdCheck = new Date(sdf.parse(tri.getStartDate()).getTime());
                edCheck = new Date(sdf.parse(tri.getEndDate()).getTime());
                if (((sdCheck.getTime() <= sd.getTime()) && (sd.getTime() <= edCheck.getTime()))
                        || ((sdCheck.getTime() <= ed.getTime()) && (ed.getTime() <= edCheck.getTime()))) {
                    valid += sd + " to " + ed + " is time to shoot another tribulation!\n";
                    break;
                }
            }
        }

        return valid;
    }

    public static String checkInputSEDateBetween(String sdOld, String edOld, String start, String end) throws Exception {
        Date sd = null, ed = null, sdO = null, edO = null;
        String valid = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        sdO = new Date(sdf.parse(sdOld).getTime());
        edO = new Date(sdf.parse(edOld).getTime());
        try {
            sd = new Date(sdf.parse(start).getTime());
        } catch (ParseException e) {
            valid += "Invalid start date!\n";
        }
        try {
            ed = new Date(sdf.parse(end).getTime());
        } catch (ParseException e) {
            valid += "Invalid end date!\n";
        }

        if (valid.equals("")) {
            if (ed.getTime() < sd.getTime()) {
                valid += "Start date must be after end date!\n";
            }
            TribulationDAO dao = new TribulationDAO();
            List<TribulationDTO> dtoList = dao.getAllSEDateDTO();
            if ((sdO.getTime() <= sd.getTime()) && (sd.getTime() <= edO.getTime())
                    && (sdO.getTime() <= ed.getTime()) && (ed.getTime() <= edO.getTime())) {
                return "";
            }
            Date sdCheck = null, edCheck = null;
            for (TribulationDTO tri : dtoList) {
                sdCheck = new Date(sdf.parse(tri.getStartDate()).getTime());
                edCheck = new Date(sdf.parse(tri.getEndDate()).getTime());
                if (((sdCheck.getTime() <= sd.getTime()) && (sd.getTime() <= edCheck.getTime()))
                        || ((sdCheck.getTime() <= ed.getTime()) && (ed.getTime() <= edCheck.getTime()))) {
                    valid += sd + " to " + ed + " is time to shoot another tribulation!\n";
                    break;
                }
            }
        }

        return valid;
    }

    public static boolean checkDateHistory(String endDate) throws Exception {
        Date ed = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            ed = new Date(sdf.parse(endDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        Timestamp time = new Timestamp(cal.getTimeInMillis());
        if (ed.getTime() < time.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean copyFile(String source, String des) throws Exception {
        byte[] rs = null;
        File f = new File(source);
        FileInputStream fi = null;
        FileOutputStream fo = null;
        if (f.exists() && f.isFile()) {
            fi = new FileInputStream(f);
            int size = fi.available();
            rs = new byte[size];
            fi.read(rs);
            fi.close();
            fo = new FileOutputStream(des);
            fo.write(rs);
            fo.flush();
        }
        return true;
    }

}
