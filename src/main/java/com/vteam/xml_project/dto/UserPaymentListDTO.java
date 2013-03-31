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
public class UserPaymentListDTO extends BaseDTO{
    private List<UserPaymentDTO> paymentList;

    public List<UserPaymentDTO> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<UserPaymentDTO> paymentList) {
        this.paymentList = paymentList;
    }
    
    public UserPaymentListDTO(){
        paymentList=new ArrayList<UserPaymentDTO>();
    }

    
}
