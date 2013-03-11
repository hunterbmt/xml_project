package com.vteam.xml_project.hibernate.orm;
// Generated Mar 11, 2013 2:33:25 PM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * OrderHistory generated by hbm2java
 */
@Entity
@Table(name = "tbl_order_history", catalog = "xml_project")
public class OrderHistory implements java.io.Serializable {

    private Integer id;
    private Users user;
    private Product tblProduct;
    private Date orderDay;
    private String address;
    private int amount;

    public OrderHistory() {
    }

    public OrderHistory(Users user, Product tblProduct, Date orderDay, String address, int amount) {
        this.user = user;
        this.tblProduct = tblProduct;
        this.orderDay = orderDay;
        this.address = address;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    public Product getTblProduct() {
        return this.tblProduct;
    }

    public void setTblProduct(Product tblProduct) {
        this.tblProduct = tblProduct;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_day", nullable = false, length = 19)
    public Date getOrderDay() {
        return this.orderDay;
    }

    public void setOrderDay(Date orderDay) {
        this.orderDay = orderDay;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "amount", nullable = false)
    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}