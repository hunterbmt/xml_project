/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "product")
public class ProductDTO extends BaseDTO {

    private int id;
    private String name;
    private String description;
    private String image;
    private Integer bidId;
    private int categoryId;
    private String categoryName;
    private double minPrice;
    private double maxPrice;
    private String imageName;
    private int bidCost;
    private long bidTimeRemain;
    private long bidEndTimeRemain;

    public long getBidEndTimeRemain() {
        return bidEndTimeRemain;
    }

    public void setBidEndTimeRemain(long bidEndTimeRemain) {
        this.bidEndTimeRemain = bidEndTimeRemain;
    }
    private double currentPrice;

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

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

    @XmlElement(name ="shortDescription")
    public String getShortDescription() {
        if (description == null || description.trim().equals("")) {
            return null;
        }
        int start = description.indexOf("<p>");
        int end = description.indexOf("</p>");
        if (start == -1||end ==-1) {
            if (description.length() < 50) {
                return description;
            } else {
                return description.substring(0, 50);
            }
        } else if (end - start > 50) {
            return description.substring(start + 3, start + 53);
        }
        return description.substring(start, end);
    }
}
