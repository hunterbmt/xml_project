package com.vteam.xml_project.hibernate.orm;
// Generated Mar 11, 2013 2:33:25 PM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Bids generated by hbm2java
 */
@Entity
@Table(name = "tbl_bids", catalog = "xml_project")
public class Bids implements java.io.Serializable {

    public enum Status {

        UNCOMPLETED, COMPLETED
    };
    private Integer id;
    private Product product;
    private Integer lastUserid;
    private Double currentPrice;
    private Date startDate;
    private Date endDate;
    private Date lastEdit;
    private Status status;
    private Set<BidHistory> bidHistories = new HashSet<BidHistory>();
    private Integer cost;

//    @Transient
//    public long getLongStartDate() {
//        if (this.startDate != null) {
//            return this.startDate.getTime();
//        } else {
//            return 0;
//        }
//    }
//
//    @Transient
//    public long getLongEndDate() {
//        if (this.endDate != null) {
//            return this.endDate.getTime();
//        } else {
//            return 0;
//        }
//    }

    @Column(name = "cost", nullable = false)
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Bids() {
    }

    public Bids(Integer bid_id, Product product,
            Date start_date, Date end_date, String status) {
        this.id = bid_id;
        this.product = product;
        this.startDate = start_date;
        this.endDate = end_date;
        this.status = Status.valueOf(status);
    }

    public Bids(Product product, Date startDate, Date endDate, Integer cost) {
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
    }

    public Bids(Product product, Date startDate, Date endDate, Status s, Integer cost) {
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = s;
        this.cost = cost;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Bids(Product product, int lastUserid, double currentPrice, Date startDate) {
        this.product = product;
        this.lastUserid = lastUserid;
        this.currentPrice = currentPrice;
        this.startDate = startDate;
    }

    public Bids(Product product, int lastUserid, double currentPrice, Date startDate, Date endDate, Date lastEdit, Set tblBidHistories) {
        this.product = product;
        this.lastUserid = lastUserid;
        this.currentPrice = currentPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastEdit = lastEdit;
        this.bidHistories = tblBidHistories;
    }

    public Bids(Product product, int lastUserid, double currentPrice, Date startDate, Date endDate, Date lastEdit) {
        this.product = product;
        this.lastUserid = lastUserid;
        this.currentPrice = currentPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastEdit = lastEdit;

    }

    public Bids(int bid_id, Product product, int lastUserid, double currentPrice, Date startDate, Date endDate, Date lastEdit, String status) {
        this.id = bid_id;
        this.product = product;
        this.lastUserid = lastUserid;
        this.currentPrice = currentPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastEdit = lastEdit;
        this.status = Status.valueOf(status);

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
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product tblProduct) {
        this.product = tblProduct;
    }

    @Column(name = "last_userid", nullable = false)
    public Integer getLastUserid() {
        return this.lastUserid;
    }

    public void setLastUserid(Integer lastUserid) {
        this.lastUserid = lastUserid;
    }

    @Column(name = "current_price", nullable = true, precision = 22, scale = 0)
    public Double getCurrentPrice() {
        return this.currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false, length = 19)
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", length = 19)
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_edit", length = 19)
    public Date getLastEdit() {
        return this.lastEdit;
    }

    public void setLastEdit(Date lastEdit) {
        this.lastEdit = lastEdit;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bid")
    public Set<BidHistory> getBidHistories() {
        return this.bidHistories;
    }

    public void setBidHistories(Set<BidHistory> tblBidHistories) {
        this.bidHistories = tblBidHistories;
    }
}
