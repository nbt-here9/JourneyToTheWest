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
public class RoleDistributionDTO {

    private String triID, roleID;

    public RoleDistributionDTO(String triID, String roleID) {
        this.triID = triID;
        this.roleID = roleID;
    }

    public Vector toVector(String rolename, String castname) {
        Vector v = new Vector();
        v.add(rolename);
        v.add(castname);
        return v;
    }

    public String getTriID() {
        return triID;
    }

    public void setTriID(String triID) {
        this.triID = triID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    

}
