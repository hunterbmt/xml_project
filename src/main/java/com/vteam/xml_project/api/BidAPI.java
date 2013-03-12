/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.service.BidService;
import com.vteam.xml_project.util.DateUtil;
import java.text.ParseException;
import java.util.Date;
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
    private DateUtil dateUtil;

    public BidAPI() {
        dateUtil = new DateUtil();
    }

    @RequestMapping(value = "/get_bid_by_id", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> getBidByID(
            @RequestParam int _id) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        BidDTO result = bidService.getBidByID(_id);
        if (result != null) {
            returnMap.put("status", "success");
            returnMap.put("bid", result);
        } else {
            returnMap.put("status", "error");
            returnMap.put("message", "Cannot get");
        }
        return returnMap;
    }

    @RequestMapping(value = "/get_bids_list", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> getBidByID(
            @RequestParam int page, int page_size) {

        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        BidListDTO result = bidService.getBidsList(page, page_size);
        if (result != null) {
            returnMap.put("status", "success");
            returnMap.put("bid_list", result);
        } else {
            returnMap.put("status", "error");
            returnMap.put("message", "Cannot get");
        }
        return returnMap;
    }

    @RequestMapping(value = "/get_bids_by_start_date", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> getBidByID(
            @RequestParam String start_date, String format_date) throws ParseException {

        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        if (format_date == null) {
            format_date = "MM/dd/yyyy HH:mm:ss";
        }
        Date parseDate = dateUtil.parseFromString(start_date, format_date);
        BidListDTO result = bidService.getBidsByStartDate(parseDate);
        if (result != null) {
            returnMap.put("status", "success");
            returnMap.put("bid_list", result);
        } else {
            returnMap.put("status", "error");
            returnMap.put("message", "Cannot get");
        }
        return returnMap;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> create(
            @RequestParam int product_id, String start_date, String format_date) throws ParseException {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        BidDTO newBid = new BidDTO();
        newBid.setProduct_id(product_id);

        if (format_date == null) {
            format_date = "MM/dd/yyyy HH:mm:ss";
        }
        //Date parseDate = dateUtil.parseFromString(start_date, DateUtil.SERVER_RETURN_FORMATE_STRING);
        Date parseDate = dateUtil.parseFromString(start_date, format_date);
        newBid.setStart_date(parseDate);

        boolean result = bidService.createNewBid(newBid);
        if (result) {
            returnMap.put("status", "success");
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;
    }
}
