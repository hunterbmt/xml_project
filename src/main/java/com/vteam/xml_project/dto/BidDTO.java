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
public class BidDTO implements Serializable{
    private Integer id;
    private Integer last_userid;
    private Integer product_id;
    private Double current_price;
    private Date start_date;
    private Date end_date;
    private Date last_edit;

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
        this.current_price = current_price;
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
