/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.OrderHistoryListDTO;
import com.vteam.xml_project.service.OrderHistoryService;
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

    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    OrderHistoryListDTO getOrderList(
            @RequestParam int id) {
        OrderHistoryListDTO orderResult = orderService.getListByOrderID(id);
        return orderResult;
        //return result;
    }
}
