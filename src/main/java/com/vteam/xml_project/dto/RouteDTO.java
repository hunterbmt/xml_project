/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HunterBMT
 */
public class RouteDTO implements Serializable {

    private int id;
    private int totalFee;
    private String[] placeses;
    private Date createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the totalFee
     */
    public int getTotalFee() {
        return totalFee;
    }

    /**
     * @param totalFee the totalFee to set
     */
    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * @return the placeses
     */
    public String[] getPlaceses() {
        return placeses;
    }

    /**
     * @param placeses the placeses to set
     */
    public void setPlaceses(String[] placeses) {
        this.placeses = placeses;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
}
