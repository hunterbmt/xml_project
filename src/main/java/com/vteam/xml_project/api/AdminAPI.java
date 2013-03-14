/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.service.AdminService;
import java.util.HashMap;
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

    
    @RequestMapping(value = "/insert_product", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> insertProduct (
            @RequestParam String productName,String description,String img,double minPrice,double maxPrice) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        System.out.println("blabla");
        ProductDTO productDTO = adminService.insertProduct(1, productName, description, img, minPrice, maxPrice);
        if(productDTO ==null){
            returnMap.put("status", "error");
        }else{
            returnMap.put("status","success_ok");
            returnMap.put("product", productDTO);
        }
        return returnMap;
    }
    
}
