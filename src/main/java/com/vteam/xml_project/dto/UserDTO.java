/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HunterBMT
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
public class UserDTO extends BaseDTO{
    private String email;
    private String password;
    private String fullname;
    private Date birthday;
    private String address;
    private String phone;
    private Integer balance;
    private Integer id;
    @XmlElement(name = "orderList", type = OrderHistoryListDTO.class)
    private OrderHistoryListDTO oderListDTO;

    public OrderHistoryListDTO getOderListDTO() {
        return oderListDTO;
    }

    public void setOderListDTO(OrderHistoryListDTO oderListDTO) {
        this.oderListDTO = oderListDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    

    /**
     * @return the uuid
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param uuid the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }


}
