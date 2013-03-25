/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Crick
 */
public class BidListDTO extends BaseDTO {

    private int numberOfBid;
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
