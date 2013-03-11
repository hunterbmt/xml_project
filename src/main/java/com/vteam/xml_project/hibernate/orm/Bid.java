/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.orm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;

/**
 *
 * @author Crick
 */
public class Bid implements Serializable {

    public enum Status {

        UNAPPROVED, APPROVED, EXPIRED
    }
    private Integer id;
    private Integer last_userid;
    private Integer product_id;
    private Double current_price;
    private Date start_date;
    private Date end_date;
    private Date last_edit; /*  */

    private Status status;

    public Bid() {
    }

    
    
    public Bid(int product_id, Date start_date) {
        this.product_id = product_id;
        this.start_date = start_date;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer _id) {
        this.id = _id;
    }

    @Column(name = "last_userid", length = 10)
    public Integer getLast_userid() {
        return last_userid;
    }

    /**
     * @param last_userid the last_userid to set
     */
    public void setLast_userid(Integer last_userid) {
        this.last_userid = last_userid;
    }

    @Column(name = "product_id", length = 10)
    public Integer getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    @Column(name = "current_price")
    public Double getCurrent_price() {
        return current_price;
    }

    /**
     * @param current_price the current_price to set
     */
    public void setCurrent_price(Double current_price) {
        this.current_price = current_price;
    }

    @Column(name = "start_date", nullable = false)
    public Date getStart_date() {
        return start_date;
    }

    /**
     * @param start_date the start_date to set
     */
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    @Column(name = "end_date", nullable = true)
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * @param end_date the end_date to set
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    @Column(name = "last_edit", nullable = true)
    public Date getLast_edit() {
        return last_edit;
    }

    /**
     * @param last_edit the last_edit to set
     */
    public void setLast_edit(Date last_edit) {
        this.last_edit = last_edit;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status s) {
        this.status = s;
    }
}
