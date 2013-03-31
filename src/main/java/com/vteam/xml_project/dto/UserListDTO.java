/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TH11032013
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "userList")
public class UserListDTO extends BaseDTO{
    @XmlElement(name = "user", type = UserDTO.class)
    List<UserDTO> userList;

    public List<UserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }
    public UserListDTO() {
        userList = new ArrayList<UserDTO>();
    }

   
}
