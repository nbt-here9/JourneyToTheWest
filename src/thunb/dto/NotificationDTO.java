package thunb.dto;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Banh Bao
 */
public class NotificationDTO {
//implements Comparable<NotificationDTO>{
    String notiID, notiAct, notiDes, castID, timeUp;
    
    
    public Vector toVector(){
        Vector v=new Vector();
        v.add(notiID);
        v.add(timeUp);
        v.add(notiDes);
        return v;
    }

    public NotificationDTO() {
    }
    
    public NotificationDTO(String notiID, String notiDes, String castID, String timeUp) {
        this.notiID = notiID;
        this.notiDes = notiDes;
        this.castID = castID;
        this.timeUp = timeUp;
    }

    public NotificationDTO(String notiID, String notiAct, String notiDes, String castID, String timeUp) {
        this.notiID = notiID;
        this.notiAct = notiAct;
        this.notiDes = notiDes;
        this.castID = castID;
        this.timeUp = timeUp;
    }

    public String getNotiAct() {
        return notiAct;
    }

    public void setNotiAct(String notiAct) {
        this.notiAct = notiAct;
    }

    public String getNotiID() {
        return notiID;
    }

    public void setNotiID(String notiID) {
        this.notiID = notiID;
    }

    public String getNotiDes() {
        return notiDes;
    }

    public void setNotiDes(String notiDes) {
        this.notiDes = notiDes;
    }

    public String getCastID() {
        return castID;
    }

    public void setCastID(String castID) {
        this.castID = castID;
    }

    public String getTimeUp() {
        return timeUp;
    }

    public void setTimeUp(String timeUp) {
        this.timeUp = timeUp;
    }

//    @Override
//    public int compareTo(NotificationDTO t) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = null, dt = null;
//        try {
//            d = new Date(sdf.parse(timeUp).getTime());
//            dt = new Date(sdf.parse(t.getTimeUp()).getTime());
//        } catch (ParseException ex) {
//            Logger.getLogger(NotificationDTO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        if (d!=null) 
//        return (d.getTime() < dt.getTime());
//    }
    
}
