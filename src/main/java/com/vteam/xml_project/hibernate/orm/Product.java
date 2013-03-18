package com.vteam.xml_project.hibernate.orm;
// Generated Mar 11, 2013 3:58:45 PM by Hibernate Tools 3.2.1.GA

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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Product generated by hbm2java
 */
@Entity
@Table(name = "tbl_product", catalog = "xml_project")
public class Product implements java.io.Serializable {

    public enum Status {

        AVAILABLE ,UNAVAILABLE, DELETE;
    }
    private Integer id;
    private Category category;
    private String productName;
    private String description;
    private String image;
    private Status status;
    private double minPrice;
    private double maxPrice;
    private Date lastUpdate;
    private boolean isActive;
    private Set<Tags> tagses = new HashSet(0);
    private Set<Bids> bidses = new HashSet(0);
    private Set<OrderHistory> orderHistories = new HashSet(0);

    public Product() {
    }

    public Product(Category tblCategory, String productName, String description, String image, Status status, double minPrice, double maxPrice, boolean isActive) {
        this.category = tblCategory;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.status = status;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.isActive = isActive;
    }

    public Product(Category tblCategory, String productName, String description, String image, Status status, double minPrice, double maxPrice, Date lastUpdate, boolean isActive, Set tblTagses, Set tblBidses, Set tblOrderHistories) {
        this.category = tblCategory;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.status = status;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.lastUpdate = lastUpdate;
        this.isActive = isActive;
        this.tagses = tblTagses;
        this.bidses = tblBidses;
        this.orderHistories = tblOrderHistories;
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
    @JoinColumn(name = "category_id", nullable = false)
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category tblCategory) {
        this.category = tblCategory;
    }

    @Column(name = "product_name", nullable = false, length = 100)
    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Lob
    @Column(name = "description", nullable = false, length = 65535)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "image", nullable = false, length = 200)
    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "min_price", nullable = false, precision = 22, scale = 0)
    public double getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    @Column(name = "max_price", nullable = false, precision = 22, scale = 0)
    public double getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update", length = 19)
    public Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Column(name = "is_active", nullable = false)
    public boolean isIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_tags_product", catalog = "xml_project", joinColumns = {
        @JoinColumn(name = "product_id", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "tags_id", nullable = false, updatable = false)})
    public Set<Tags> getTagses() {
        return this.tagses;
    }

    public void setTagses(Set<Tags> tblTagses) {
        this.tagses = tblTagses;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    public Set<Bids> getBidses() {
        return this.bidses;
    }

    public void setBidses(Set<Bids> tblBidses) {
        this.bidses = tblBidses;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    public Set<OrderHistory> getOrderHistories() {
        return this.orderHistories;
    }

    public void setOrderHistories(Set<OrderHistory> tblOrderHistories) {
        this.orderHistories = tblOrderHistories;
    }
}
