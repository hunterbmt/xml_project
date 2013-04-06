/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Crick
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bidList")
public class BidListDTO extends BaseDTO {

    private int numberOfBid;
    @XmlElement(name = "bid", type = BidDTO.class)
    private List<BidDTO> bidList;

    public BidListDTO() {
        this.bidList = new ArrayList<BidDTO>();
    }

    public List<BidDTO> getBidList() {
        return bidList;
    }

    public void setBidList(List<BidDTO> bidList) {
        this.bidList = bidList;
    }

    public int getNumberOfBid() {
        return numberOfBid;
    }

    public void setNumberOfBid(int numberOfBid) {
        this.numberOfBid = numberOfBid;
    }
}
