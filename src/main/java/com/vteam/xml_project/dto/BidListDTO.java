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
public class BidListDTO extends BaseDTO{
    private List<BidDTO> list;

    public BidListDTO() {
        this.list = new ArrayList<BidDTO>();
    }

    public List<BidDTO> getLists() {
        return list;
    }

    public void setLists(List<BidDTO> list) {
        this.list = list;
    }
    
    
}
