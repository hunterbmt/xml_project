/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.NinCodeDTO;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.dto.UserPaymentListDTO;
import com.vteam.xml_project.service.UserService;
import com.vteam.xml_project.util.StringUtil;
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
@RequestMapping(value = "/user")
public class UserAPI {

    @Autowired
    private UserSession session;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    UserDTO login(
            @RequestParam String email, String password) {
        if (StringUtil.validString(email) && StringUtil.validString(password)) {
            UserDTO result = userService.checkLogin(email, password);
            if (result.getStatus().equals("success")) {
                session.put("email", result.getEmail());
            }

            return result;
        }
        else{
            UserDTO result = new UserDTO();
            result.setStatus("error");
            result.setMsg("Have some error");
            return result;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    HashMap<String, Object> logout() {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        session.remove("email");
        returnMap.put("status", "success");
        return returnMap;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    HashMap<String, Object> create(
            @RequestParam String email, String password, String fullname) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        UserDTO newUser = new UserDTO();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFullname(fullname);
        boolean result = userService.createNewUser(newUser);
        if (result) {
            session.put("email", email);
            userService.updateAllXML();
            returnMap.put("status", "success");
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    HashMap<String, Object> update(
            @RequestParam String address, String birthday, String phone, String formatDate) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String email = (String) session.get("email");
        if (!StringUtil.validString(email)) {
            returnMap.put("status", "unlogin");
            return returnMap;
        }
        boolean result = userService.upadateUser(email, address, phone, birthday, formatDate);
        if (result) {
            returnMap.put("status", "success");
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;
    }

    @RequestMapping(value = "/get_user_by_id", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    UserDTO get_user_by_id(
            @RequestParam String id) {

        UserDTO rs = userService.getUserById(Integer.valueOf(id));
        return rs;
    }

    @RequestMapping(value = "/get_user_by_email", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    UserDTO get_user_by_email() {
        String email = (String) session.get("email");
        UserDTO result;
        if (!StringUtil.validString(email)) {
            result = new UserDTO();
            result.setStatus("unlogin");
            return result;
        }
        result = userService.getUserByEmail(email);
        return result;
    }
    @RequestMapping(value = "/check_email", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    boolean check_email(@RequestParam String email) {
        //String email = (String) session.get("email");
       if(userService.checkEmail(email)){
           return true;
       }
       return false;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    HashMap<String, Object> changePassword(
            @RequestParam String currentPass, String newPass) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String email = (String) session.get("email");
        if (!StringUtil.validString(email)) {
            returnMap.put("status", "unlogin");
            return returnMap;
        }
        boolean result = userService.checkPassword(email, currentPass, newPass);
        if (result) {
            returnMap.put("status", "success");
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;
    }

    @RequestMapping(value = "/input_payment_code", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    NinCodeDTO inputPaymentCode(@RequestParam String code) {
        NinCodeDTO ninCodeDTO;
        String email = (String) session.get("email");
        if (!StringUtil.validString(email)) {
            ninCodeDTO = new NinCodeDTO();
            ninCodeDTO.setStatus("unlogin");
            return ninCodeDTO;
        }
        ninCodeDTO = userService.inputPayment(email, code);
        return ninCodeDTO;
    }

    @RequestMapping(value = "/getPaymentList", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    UserPaymentListDTO getPaymentList(
            @RequestParam int id) {
        UserPaymentListDTO paymentResult = userService.getListByPaymentID(id);
        return paymentResult;
        //return result;
    }
}
