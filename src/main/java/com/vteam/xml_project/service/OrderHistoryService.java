/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.OrderHistoryDTO;
import com.vteam.xml_project.dto.OrderHistoryListDTO;
import com.vteam.xml_project.hibernate.dao.OrderHistoryDAO;
import com.vteam.xml_project.hibernate.orm.OrderHistory;
import com.vteam.xml_project.hibernate.orm.Users;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TH11032013
 */
@Service
public class OrderHistoryService {
    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
    private OrderHistoryDAO orderDAO;
    @Transactional
    public OrderHistoryListDTO getListByOrderID(int id){
        OrderHistoryListDTO listOrders=new OrderHistoryListDTO();
        try{
            Users user = new Users();
            List<OrderHistory> dbOrders = orderDAO.getOrderHistorysList(id);
            
            
            OrderHistoryDTO o;

            List<OrderHistoryDTO> tmpList = new ArrayList<OrderHistoryDTO>();
            for (OrderHistory d : dbOrders) {

                o = new OrderHistoryDTO();
                o.setProduct_id(d.getProduct().getId());
                o.setProduct_name(d.getProduct().getProductName());
                o.setUser_id(d.getUser().getId());
                o.setAddress(d.getAddress());
                o.setDate(d.getOrderDay());
                o.setAmmount(d.getAmount());

                tmpList.add(o);
            }
            listOrders.setOrderList(tmpList);
            listOrders.setStatus("success");
        }catch (HibernateException ex){
            log.error(ex.getStackTrace());
           listOrders.setStatus("error");
           listOrders.setMsg("Have some errors. Try again");
        }
        return  listOrders;
    }
}
