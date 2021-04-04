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
public class PropsUsedDTO {
    private String triID, propsID;
    private int quantity;

    public PropsUsedDTO(String triID, String propsID, int quantity) {
        this.triID = triID;
        this.propsID = propsID;
        this.quantity = quantity;
    }

    public Vector toVector(String propsName){
        Vector v = new Vector();
        v.add(propsID);
        v.add(propsName);
        v.add(quantity);
        return v;
    }
    public String getTriID() {
        return triID;
    }

    public void setTriID(String triID) {
        this.triID = triID;
    }

    public String getPropsID() {
        return propsID;
    }

    public void setPropsID(String propsID) {
        this.propsID = propsID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
