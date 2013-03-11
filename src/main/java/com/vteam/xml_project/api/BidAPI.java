/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;


import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.dto.UserProfileDTO;
import com.vteam.xml_project.service.BidService;
import java.util.HashMap;
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
@RequestMapping(value = "/bid")
public class BidAPI {

    @Autowired
    private UserSession session;
    @Autowired
    private BidService bidService;

    @RequestMapping(value = "/get_bid_by_id", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> getBidByID(
            @RequestParam int _id) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        BidDTO result = bidService.getBidByID(_id);
        if (result != null) {
            returnMap.put("status", "success");
            returnMap.put("rs", result);
        } else {
            returnMap.put("status", "error");
            returnMap.put("message", "Cannot get");
        }
        return returnMap;

    }

    

    
}

