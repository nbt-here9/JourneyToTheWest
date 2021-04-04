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
public class RoleDTO {

    private String roleID, roleName, castID, castName, roleDes;

    public RoleDTO(String roleID, String roleName, String castID, String castName, String roleDes) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.castID = castID;
        this.castName = castName;
        this.roleDes = roleDes;
    }

    public RoleDTO(String roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    
    public Vector toVector(){
        Vector v=new Vector();
        v.add(roleID);
        v.add(roleName);
        v.add(castName);
        return v;
    }
    
    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCastID() {
        return castID;
    }

    public void setCastID(String castID) {
        this.castID = castID;
    }

    public String getRoleDes() {
        return roleDes;
    }

    public void setRoleDes(String roleDes) {
        this.roleDes = roleDes;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }
    
    
}
