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
 * @author TH11032013
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderHistoryList")
public class OrderHistoryListDTO extends BaseDTO {

    @XmlElement(name = "orderHistory", type = OrderHistoryDTO.class)
    private List<OrderHistoryDTO> orderList;
    private int numberOfOrder = 0;

    public OrderHistoryListDTO() {

        orderList = new ArrayList<OrderHistoryDTO>();
    }

    public List<OrderHistoryDTO> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderHistoryDTO> orderList) {
        this.orderList = orderList;
    }

    public int getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(int numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }
}
