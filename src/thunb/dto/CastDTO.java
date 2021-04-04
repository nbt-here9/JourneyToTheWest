/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dto;

import java.util.Vector;

/**
 *
 * @author Banh Bao
 */
public class CastDTO {
    private String castID, username, name, 
            image, des, phone, email, status;
    private int numNoti;

    public CastDTO() {
    }
    
    public Vector toVector(){
        Vector v = new Vector();
        v.add(castID);
        v.add(name);
        return v;
    }

    public CastDTO(String castID, String name) {
        this.castID = castID;
        this.name = name;
    }
    
    public CastDTO(String castID, String username, 
            String name, String image, String des, 
            String phone, String email, String status, int numNoti) {
        this.castID = castID;
        this.username = username;
        this.name = name;
        this.image = image;
        this.des = des;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.numNoti = numNoti;
    }

    public CastDTO(String castID, String name, String image, String des, String phone, String email, String status, int numNoti) {
        this.castID = castID;
        this.name = name;
        this.image = image;
        this.des = des;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.numNoti = numNoti;
    }
    
    public String getCastID() {
        return castID;
    }

    public void setCastID(String castID) {
        this.castID = castID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumNoti() {
        return numNoti;
    }

    public void setNumNoti(int numNoti) {
        this.numNoti = numNoti;
    }
    
}
