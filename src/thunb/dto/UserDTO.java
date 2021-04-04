/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dto;

/**
 *
 * @author Banh Bao
 */
public class UserDTO {

    private String username, password, actor;

    public UserDTO(String username, String actor) {
        this.username = username;
        this.actor = actor;
    }

    public UserDTO(String username, String password, String actor) {
        this.username = username;
        this.password = password;
        this.actor = actor;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

}
