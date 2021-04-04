/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dto;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Banh Bao
 */
public class TribulationDTO {

    private String triID, name, des, locate, startDate, endDate;
    private int numNG;

    public Vector toVector() {
        Vector v = new Vector();
        v.add(triID);
        v.add(name);
        return v;
    }

    public TribulationDTO(String triID, String name) {
        this.triID = triID;
        this.name = name;
    }

    public TribulationDTO(String triID, String startDate, String endDate) {
        this.triID = triID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TribulationDTO(String triID, String name, String des, String locate, String startDate, String endDate, int numNG) {
        this.triID = triID;
        this.name = name;
        this.des = des;
        this.locate = locate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numNG = numNG;
    }

    public String getTriID() {
        return triID;
    }

    public void setTriID(String triID) {
        this.triID = triID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNumNG() {
        return numNG;
    }

    public void setNumNG(int numNG) {
        this.numNG = numNG;
    }

}
