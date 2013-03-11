/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import com.vteam.xml_project.hibernate.orm.Bid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Crick
 */
public class BidListDTO {
    private List<Bid> lists;

    public BidListDTO() {
        this.lists = new ArrayList<Bid>();
    }

    public List<Bid> getLists() {
        return lists;
    }

    public void setLists(List<Bid> lists) {
        this.lists = lists;
    }
    
    
}
