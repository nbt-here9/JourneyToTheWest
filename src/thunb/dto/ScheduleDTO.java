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
public class ScheduleDTO implements Comparable<ScheduleDTO>{
    private String triName, roleName, triLocate, startDate , endDate;

    public ScheduleDTO(String triName, String roleName, String triLocate, String startDate, String endDate) {
        this.triName = triName;
        this.roleName = roleName;
        this.triLocate = triLocate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Vector toVector(){
        Vector v = new Vector();
        v.add(triName);
        v.add(roleName);
        v.add(triLocate);
        v.add(startDate);
        v.add(endDate);
        return v;
    }
    
    public ScheduleDTO() {
    }

    public String getTriName() {
        return triName;
    }

    public void setTriName(String triName) {
        this.triName = triName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getTriLocate() {
        return triLocate;
    }

    public void setTriLocate(String triLocate) {
        this.triLocate = triLocate;
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

    @Override
    public String toString() {
        return "ScheduleDTO{" + "triName=" + triName + ", roleName=" + roleName + ", triLocate=" + triLocate + ", startDate=" + startDate + ", endDate=" + endDate + '}';
    }

        
    @Override
    public int compareTo(ScheduleDTO sche2) {
    try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date sd = new Date(sdf.parse(this.getStartDate()).getTime());
            Date sd2 = new Date(sdf.parse(sche2.getEndDate()).getTime());
            if (sd.getTime() > sd2.getTime()) {
                return -1;
            }
            if (sd.getTime() < sd2.getTime()) {
                return 1;
            }
            return 0;
        } catch (ParseException ex) {
            Logger.getLogger(TribulationDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
}
