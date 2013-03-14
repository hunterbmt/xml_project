    package com.vteam.xml_project.hibernate.orm;
// Generated Mar 11, 2013 2:33:25 PM by Hibernate Tools 3.2.1.GA

import com.vteam.xml_project.util.StringUtil;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "tbl_users", catalog = "xml_project")
public class Users implements java.io.Serializable {

    private Integer id;
    private String email;
    private String password;
    private String fullname;
    private Date birthday;
    private String address;
    private String phone;
    private Set<OrderHistory> orderHistories = new HashSet<OrderHistory>(0);
    private Set<UserPayment> userPayments = new HashSet<UserPayment>(0);
    private Set<CardCode> cardCodes = new HashSet<CardCode>(0);
    private Set<BidHistory> bidHistories = new HashSet<BidHistory>(0);

    public Users() {
    }

    public Users(String email, String password,String fullname,Date birthday, String address, String phone) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
    }

    public Users(String email, String password,String fullname,Date birthday, String address, String phone, Set tblOrderHistories, Set tblUserPayments,  Set tblCardCodes, Set tblBidHistories) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.orderHistories = tblOrderHistories;
        this.userPayments = tblUserPayments;
        this.cardCodes = tblCardCodes;
        this.bidHistories = tblBidHistories;
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

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name = "fullname", nullable = false)
    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthday", nullable = true, length = 19)
    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(name = "address", nullable = true)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone", nullable = true)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<OrderHistory> getTblOrderHistories() {
        return this.orderHistories;
    }

    public void setTblOrderHistories(Set<OrderHistory> tblOrderHistories) {
        this.orderHistories = tblOrderHistories;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<UserPayment> getTblUserPayments() {
        return this.userPayments;
    }

    public void setTblUserPayments(Set<UserPayment> tblUserPayments) {
        this.userPayments = tblUserPayments;
    }


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<CardCode> getTblCardCodes() {
        return this.cardCodes;
    }

    public void setTblCardCodes(Set<CardCode> tblCardCodes) {
        this.cardCodes = tblCardCodes;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<BidHistory> getTblBidHistories() {
        return this.bidHistories;
    }

    public void setTblBidHistories(Set<BidHistory> tblBidHistories) {
        this.bidHistories = tblBidHistories;
    }
}
