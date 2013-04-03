/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.NinCodeDTO;
import com.vteam.xml_project.dto.NinCodeListDTO;
import com.vteam.xml_project.dto.OrderHistoryDTO;
import com.vteam.xml_project.dto.OrderHistoryListDTO;
import com.vteam.xml_project.hibernate.dao.OrderHistoryDAO;
import com.vteam.xml_project.hibernate.orm.CardCode;
import com.vteam.xml_project.hibernate.orm.OrderHistory;
import com.vteam.xml_project.hibernate.orm.Users;
import com.vteam.xml_project.util.XMLUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.fop.apps.FOPException;
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
    @Autowired
    private ServletContext servletContext;
    
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
     @Transactional
    public ByteArrayOutputStream exportOrderHistoryToPdf(int id) {
        try {
            List<OrderHistory> orderList = orderDAO.getOrderHistorysList(id);
            OrderHistoryListDTO orderListDTO = new OrderHistoryListDTO();
            OrderHistoryDTO orderDTO;
            for (OrderHistory order : orderList) {
                orderDTO = new OrderHistoryDTO();
                orderDTO.setProduct_name(order.getProduct().getProductName());
                orderDTO.setAddress(order.getAddress());
                orderDTO.setDate(order.getOrderDay());
                orderDTO.setAmmount(order.getAmount());
                orderListDTO.getOrderList().add(orderDTO);
            }
            File xmlFile = File.createTempFile(UUID.randomUUID().toString(), "_order.xml");
            XMLUtil.Marshall(orderListDTO, xmlFile.getAbsolutePath());
            String appPath = servletContext.getRealPath("WEB-INF/views/resources/xsl");

            return XMLUtil.printPDF(xmlFile.getAbsolutePath(), appPath + File.separator + "order_history_pdf.xsl");
        } catch (IOException ex) {
            log.error(ex);
        } catch (JAXBException ex) {
            log.error(ex);
        } catch (TransformerConfigurationException ex) {
            log.error(ex);
        } catch (TransformerException ex) {
            log.error(ex);
        } catch (FOPException ex) {
            java.util.logging.Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
