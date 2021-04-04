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
public class PropsDTO {
    private String propsID, name, des, image;
    private int quantity;
    
    public Vector toVector(){
        Vector v=new Vector();
        v.add(propsID);
        v.add(name);
        v.add(quantity);
        return v;
    }

    public PropsDTO(String propsID, String name) {
        this.propsID = propsID;
        this.name = name;
    }

    public PropsDTO(String propsID, String name, int quantity) {
        this.propsID = propsID;
        this.name = name;
        this.quantity = quantity;
    }

    public PropsDTO(String propsID, String name, String des, String image, int quantity) {
        this.propsID = propsID;
        this.name = name;
        this.des = des;
        this.image = image;
        this.quantity = quantity;
    }

    
    public String getPropsID() {
        return propsID;
    }

    public void setPropsID(String propsID) {
        this.propsID = propsID;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PropsDTO{" + "propsID=" + propsID + ", name=" + name + ", des=" + des + ", image=" + image + ", quantity=" + quantity + '}';
    }
    
    
}
