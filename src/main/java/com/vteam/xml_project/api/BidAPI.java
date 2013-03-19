/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.service.BidService;
import com.vteam.xml_project.service.UserService;
import com.vteam.xml_project.util.DateUtil;
import java.text.ParseException;
import java.util.Date;
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
@RequestMapping(value = "/bid")
public class BidAPI {

    private static Logger logger = Logger.getLogger(BidAPI.class.getName());
    @Autowired
    private UserSession userSession;
    @Autowired
    private BidService bidService;
    @Autowired
    private UserService userService;
    @Autowired
    private DateUtil dateUtil;

    /* ========== place bid ============ */
    @RequestMapping(value = "/do_bid", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> doBid(
            @RequestParam int bid_id) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();

        //String uuid = (String) userSession.get("uuid");
        String email = userSession.get("email").toString();
        UserDTO u = userService.getUserByEmail(email);
        logger.debug("do_bid: placing a bid, wait for respond ,from uuid :" + u.getId() + " - Name: " + u.getFullname() );

        double price = bidService.doBid(u, bid_id);
        if (price > 0) {
            
            
            returnMap.put("allowed", "ok");
            returnMap.put("message", "The bid has been set");
            returnMap.put("price",price);
        } else {
            returnMap.put("allowed", "no");
            returnMap.put("message", "The bid has locked to other bidder");
        }
        return returnMap;
    }

    /* =============== bid manipulations ================= */
    @RequestMapping(value = "/get_bid_by_id", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> get_bid_by_id(
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
    HashMap<String, Object> get_bids_list(
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
    HashMap<String, Object> get_bids_by_start_date(
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

    @RequestMapping(value = "/get_upcoming_bids", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> get_upcoming_bids() throws ParseException {

        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        Date curDate = dateUtil.getCurrentDate();
        BidListDTO result = bidService.getBidsFromDate(curDate);
        if (result != null) {
            returnMap.put("status", "success");
            returnMap.put("upcoming_bid_list", result);
        } else {
            returnMap.put("status", "error");
            returnMap.put("message", "Cannot get");
        }
        return returnMap;
    }

    @RequestMapping(value = "/get_ongoing_bids", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> get_ongoing_bids() throws ParseException {

        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        Date curDate = new Date();
        BidListDTO result = bidService.getOngoingBids(curDate);
        if (result != null) {
            returnMap.put("status", "success");
            returnMap.put("ongoing_bid_list", result);
        } else {
            returnMap.put("status", "error");
            returnMap.put("message", "Cannot get");
        }
        return returnMap;
    }

    @RequestMapping(value = "/get_completed_bids", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> get_completed_bids() throws ParseException {

        HashMap<String, Object> returnMap = new HashMap<String, Object>();

        BidListDTO result = bidService.getCompletedBids();
        if (result != null) {
            returnMap.put("status", "success");
            returnMap.put("completed_bid_list", result);
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
