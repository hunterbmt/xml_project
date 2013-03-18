/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.service.AdminService;
import com.vteam.xml_project.util.DateUtil;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author HunterBMT
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminAPI {

    private static Logger logger = Logger.getLogger(AdminAPI.class.getName());
    @Autowired
    private UserSession userSession;
    @Autowired
    private AdminService adminService;
    @Autowired
    private DateUtil dateUtil;

    @RequestMapping(value = "/insert_product", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> insertProduct(
            @RequestParam String productName, String description, String img, double minPrice, double maxPrice) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        ProductDTO productDTO = adminService.insertProduct(1, productName, description, img, minPrice, maxPrice);
        if (productDTO == null) {
            returnMap.put("status", "error");
        } else {
            returnMap.put("status", "success_ok");
            returnMap.put("product", productDTO);
        }
        return returnMap;
    }

    @RequestMapping(value = "/update_bid", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> updateBid(
            @RequestParam int bid_id, int last_user, int product_id, double current_price, 
            String last_edit,String start_date, String end_date, String status) {
        try {
            HashMap<String, Object> returnMap = new HashMap<String, Object>();
            BidDTO bDTO = adminService.updateBid(bid_id, last_user, product_id,
                    current_price,
                    dateUtil.parseFromString(last_edit, "MM/dd/yyyy HH:mm"),
                    dateUtil.parseFromString(start_date, "MM/dd/yyyy HH:mm"),
                    dateUtil.parseFromString(end_date, "MM/dd/yyyy HH:mm"),
                    status);
            if (bDTO == null) {
                returnMap.put("status", "error");
            } else {
                returnMap.put("status", "success_ok");
                returnMap.put("bid", bDTO);
            }
            return returnMap;
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(AdminAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @RequestMapping(value = "/insert_bid", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> insertBid(
            @RequestParam int last_user, int product_id, double current_price, String last_edit,
            String start_date,
            String end_date) {
        try {
            HashMap<String, Object> returnMap = new HashMap<String, Object>();
            BidDTO bDTO = adminService.insertBid(last_user, product_id,
                    current_price,
                    dateUtil.parseFromString(last_edit, "MM/dd/yyyy HH:mm"),
                    dateUtil.parseFromString(start_date, "MM/dd/yyyy HH:mm"),
                    dateUtil.parseFromString(end_date, "MM/dd/yyyy HH:mm"));
            if (bDTO == null) {
                returnMap.put("status", "error");
            } else {
                returnMap.put("status", "success_ok");
                returnMap.put("bid", bDTO);
            }
            return returnMap;
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(AdminAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
