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
public class BidListDTO {
    private List<BidDTO> lists;

    public BidListDTO() {
        this.lists = new ArrayList<BidDTO>();
    }

    public List<BidDTO> getLists() {
        return lists;
    }

    public void setLists(List<BidDTO> lists) {
        this.lists = lists;
    }
    
    
}
