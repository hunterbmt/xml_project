/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.orm;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import com.vteam.xml_project.hibernate.orm.User;

/**
 *
 * @author TH31012013
 */
public class User implements Serializable {
    private String uuid;
    private String email;
    private String password;
    
    public User() {
    }
    
    public User(String uuid,String email,String password)
    {
        this.uuid=uuid;
        this.email=email;
        this.password=password;
    }
    @Id
    @Column(name = "user_id", unique = true, nullable = false, length = 10)
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
