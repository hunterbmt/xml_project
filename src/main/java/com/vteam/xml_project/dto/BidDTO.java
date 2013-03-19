/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Crick
 */
public class BidDTO implements Serializable {

    private Integer id;
    private Integer last_userid;
    private String last_username;
    private String status;
    private Integer cost;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    public String getLast_username() {
        return last_username;
    }
    
    public void setLast_username(String last_username) {
        this.last_username = last_username;
    }
    
    public String getProduct_name() {
        return product_name;
    }
    
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    private Integer product_id;
    private String product_name;
    private Double current_price;
    private Date start_date;
    private Date end_date;
    private Date last_edit;
    
    public BidDTO() {
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getLast_userid() {
        return last_userid;
    }
    
    public void setLast_userid(Integer last_userid) {
        this.last_userid = last_userid;
    }
    
    public Integer getProduct_id() {
        return product_id;
    }
    
    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }
    
    public Double getCurrent_price() {
        return current_price;
    }
    
    public void setCurrent_price(Double current_price) {
        if (current_price != null) {
            this.current_price = current_price;
        } else {
            this.current_price = Double.parseDouble("0.0");
        }
    }
    
    public Date getStart_date() {
        return start_date;
    }
    
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
    
    public Date getEnd_date() {
        return end_date;
    }
    
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
    
    public Date getLast_edit() {
        return last_edit;
    }
    
    public void setLast_edit(Date last_edit) {
        this.last_edit = last_edit;
    }
}
