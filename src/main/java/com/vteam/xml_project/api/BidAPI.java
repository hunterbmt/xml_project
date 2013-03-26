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
@RequestMapping(value = "/bid")
public class BidAPI {

    private static Logger logger = Logger.getLogger(BidAPI.class.getName());
    @Autowired
    private UserSession userSession;
    @Autowired
    private BidService bidService;
    @Autowired
    private UserService userService;

    /* ========== place bid ============ */
    @RequestMapping(value = "/do_bid", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> doBid(
            @RequestParam int bid_id) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            //String uuid = (String) userSession.get("uuid");
            String email = userSession.get("email").toString();
            UserDTO u = userService.getUserByEmail(email);
            logger.debug("do_bid: placing a bid, wait for respond ,from uuid :" + u.getId() + " - Name: " + u.getFullname());

            double price = bidService.doBid(u, bid_id);
            if (price > 0) {
                returnMap.put("allowed", "ok");
                returnMap.put("message", "Chúc mừng, bạn đã đặt bid thành công!");
                returnMap.put("price", price);
                returnMap.put("bidId", bid_id);
            } else {
                String message = "";
                if (price == -113) {
                    message = "Tài khoản bạn không đủ NIL để thực hiện, vui lòng nạp thêm ^_^ !";
                } else {
                    message = "Bid này hiện đang được tạm giữ, vui lòng thử lại sau";
                }
                returnMap.put("allowed", "no");
                returnMap.put("message", message);
            }
        } catch (NullPointerException ex) {
            java.util.logging.Logger.getLogger(AdminAPI.class.getName()).log(Level.SEVERE, null, ex);
            returnMap.put("allowed", "no");
            returnMap.put("message", "Vui lòng đăng nhập để thực hiện tính năng này!");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AdminAPI.class.getName()).log(Level.SEVERE, null, ex);
            returnMap.put("allowed", "no");
            returnMap.put("message", ex.getMessage());
        }
        return returnMap;
    }
    
    @RequestMapping(value = "/do_buy", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> doBuy(
            @RequestParam int bid_id) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            //String uuid = (String) userSession.get("uuid");
            String email = userSession.get("email").toString();
            UserDTO u = userService.getUserByEmail(email);
            logger.debug("do_bid: buying a bid, wait for respond ,from uuid :" + u.getId() + " - Name: " + u.getFullname());

            boolean ok = bidService.doBuy(u, bid_id);
            if (ok) {
                returnMap.put("success", "yes");
                returnMap.put("message", "Chúc mừng, sản phẩm đã thuộc về bạn! ^_^");
                returnMap.put("bidId", bid_id);
            } else {
                String message = "Có lỗi trong quá trình xử lý! Vui lòng liên hệ admin";
                returnMap.put("success", "no");
                returnMap.put("message", message);
            }
        } catch (NullPointerException ex) {
            java.util.logging.Logger.getLogger(AdminAPI.class.getName()).log(Level.SEVERE, null, ex);
            returnMap.put("success", "no");
            returnMap.put("message", "Vui lòng đăng nhập để thực hiện tính năng này!");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AdminAPI.class.getName()).log(Level.SEVERE, null, ex);
            returnMap.put("success", "no");
            returnMap.put("message", ex.getMessage());
        }
        return returnMap;
    }

    /* =============== bid manipulations ================= */
    @RequestMapping(value = "/get_bid_by_id", method = RequestMethod.POST)
    public @ResponseBody
    BidDTO get_bid_by_id(
            @RequestParam int _id) {
        BidDTO result = bidService.getBidByID(_id);
        return result;
    }

    @RequestMapping(value = "/get_bids_list", method = RequestMethod.POST)
    public @ResponseBody
    BidListDTO get_bids_list(
            @RequestParam int page, int page_size) {
        BidListDTO result = bidService.getBidsList(page, page_size);
        return result;
    }

    @RequestMapping(value = "/get_upcoming_bids", method = RequestMethod.POST)
    public @ResponseBody
    BidListDTO get_upcoming_bids(@RequestParam int page,int pageSize) {
        BidListDTO result = bidService.getUpcommingBid(page,pageSize);
        return result;
    }

    @RequestMapping(value = "/get_ongoing_bids", method = RequestMethod.POST)
    public @ResponseBody
    BidListDTO get_ongoing_bids(@RequestParam int page,int pageSize) {
        BidListDTO result = bidService.getOngoingBids(page,pageSize);
        return result;
    }

    @RequestMapping(value = "/get_completed_bids", method = RequestMethod.POST)
    public @ResponseBody
    BidListDTO get_completed_bids(@RequestParam int page,int pageSize){
        BidListDTO result = bidService.getCompletedBids(page,pageSize);
        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    BidDTO create(
            @RequestParam int product_id, String start_date, String format_date) {

        if (format_date == null) {
            format_date = "MM/dd/yyyy HH:mm:ss";
        }
        BidDTO result = bidService.createNewBid(product_id, start_date, format_date);
        return result;
    }
}
