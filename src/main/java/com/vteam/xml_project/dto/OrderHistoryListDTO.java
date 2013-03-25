/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TH11032013
 */
public class OrderHistoryListDTO extends BaseDTO{
    private List<OrderHistoryDTO> orderList;
    
    public OrderHistoryListDTO(){
        orderList=new ArrayList<OrderHistoryDTO>();
    }

    public List<OrderHistoryDTO> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderHistoryDTO> orderList) {
        this.orderList = orderList;
    }
}
