package com.vteam.xml_project.hibernate.orm;
// Generated Mar 11, 2013 2:33:25 PM by Hibernate Tools 3.2.1.GA

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

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "tbl_users", catalog = "xml_project")
public class Users implements java.io.Serializable {

    private Integer id;
    private String email;
    private String password;
    private Set tblOrderHistories = new HashSet(0);
    private Set tblUserPayments = new HashSet(0);
    private UserInfo user_info ;
    private Set tblCardCodes = new HashSet(0);
    private Set tblBidHistories = new HashSet(0);

    public Users() {
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users(String email, String password, Set tblOrderHistories, Set tblUserPayments, UserInfo user_info, Set tblCardCodes, Set tblBidHistories) {
        this.email = email;
        this.password = password;
        this.tblOrderHistories = tblOrderHistories;
        this.tblUserPayments = tblUserPayments;
        this.user_info = user_info;
        this.tblCardCodes = tblCardCodes;
        this.tblBidHistories = tblBidHistories;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tbl_users")
    public Set getTblOrderHistories() {
        return this.tblOrderHistories;
    }

    public void setTblOrderHistories(Set tblOrderHistories) {
        this.tblOrderHistories = tblOrderHistories;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tbl_users")
    public Set getTblUserPayments() {
        return this.tblUserPayments;
    }

    public void setTblUserPayments(Set tblUserPayments) {
        this.tblUserPayments = tblUserPayments;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tbl_users")
    public UserInfo getUserInfo() {
        return this.user_info;
    }

    public void setUserInfo(UserInfo user_info) {
        this.user_info = user_info;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tbl_users")
    public Set getTblCardCodes() {
        return this.tblCardCodes;
    }

    public void setTblCardCodes(Set tblCardCodes) {
        this.tblCardCodes = tblCardCodes;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tbl_users")
    public Set getTblBidHistories() {
        return this.tblBidHistories;
    }

    public void setTblBidHistories(Set tblBidHistories) {
        this.tblBidHistories = tblBidHistories;
    }
}
