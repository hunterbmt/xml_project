/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.OrderHistoryListDTO;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.hibernate.orm.Users;
import com.vteam.xml_project.service.OrderHistoryService;
import com.vteam.xml_project.service.UserService;
import java.io.ByteArrayOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author TH11032013
 */
@Controller
@RequestMapping(value = "/order")
public class OrderHistoryAPI {

    @Autowired
    private UserSession session;
    @Autowired
    private OrderHistoryService orderService;
    @Autowired
    private UserService usersevie;

    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    OrderHistoryListDTO getOrderList(
            @RequestParam int id) {
        OrderHistoryListDTO orderResult = orderService.getOrderListByUserID(id);
        return orderResult;
        //return result;
    }
      @RequestMapping(value = "/export_product_list_to_pdf", method = RequestMethod.GET)
    public void exportProductListToPdf(HttpServletResponse response) {
        try {
            String email=(String) session.get("email");
            UserDTO user=usersevie.getUserByEmail(email);
            ByteArrayOutputStream outStream = orderService.exportOrderHistoryToPdf(Integer.valueOf(user.getId()));
            byte[] pdfBytes = outStream.toByteArray();
            response.setContentType("application/pdf");
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
