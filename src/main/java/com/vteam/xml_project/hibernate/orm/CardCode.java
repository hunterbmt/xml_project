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
 * CardCode generated by hbm2java
 */
@Entity
@Table(name="tbl_card_code"
    ,catalog="xml_project"
)
public class CardCode  implements java.io.Serializable {


     private Integer id;
     private Users tblUsers;
     private String code;
     private Date usedDay;
     private int amount;

    public CardCode() {
    }

	
    public CardCode(String code, int amount) {
        this.code = code;
        this.amount = amount;
    }
    public CardCode(Users tblUsers, String code, Date usedDay, int amount) {
       this.tblUsers = tblUsers;
       this.code = code;
       this.usedDay = usedDay;
       this.amount = amount;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="used_by")
    public Users getTblUsers() {
        return this.tblUsers;
    }
    
    public void setTblUsers(Users tblUsers) {
        this.tblUsers = tblUsers;
    }
    
    @Column(name="code", nullable=false, length=20)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="used_day", length=19)
    public Date getUsedDay() {
        return this.usedDay;
    }
    
    public void setUsedDay(Date usedDay) {
        this.usedDay = usedDay;
    }
    
    @Column(name="amount", nullable=false)
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }




}

