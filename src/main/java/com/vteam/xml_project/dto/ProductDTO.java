/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

/**
 *
 * @author phitt60230
 */
public class ProductDTO extends BaseDTO {

    private String name;
    private String description;
    private String image;
    private int id;
    private Integer bidId;
    private int categoryId;
    private String categoryName;
    private double minPrice;
    private double maxPrice;
    private String imageName;
    private int bidCost;
    private long bidTimeRemain;

    public long getBidTimeRemain() {
        return bidTimeRemain;
    }

    public void setBidTimeRemain(long bidTimeRemain) {
        this.bidTimeRemain = bidTimeRemain;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the minPrice
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * @param minPrice the minPrice to set
     */
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * @return the maxPrice
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * @param maxPrice the maxPrice to set
     */
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getBidCost() {
        return bidCost;
    }

    public void setBidCost(int bidCost) {
        this.bidCost = bidCost;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
