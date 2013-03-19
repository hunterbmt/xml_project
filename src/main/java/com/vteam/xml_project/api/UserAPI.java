/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.hibernate.orm.Users;
import com.vteam.xml_project.service.UserService;
import com.vteam.xml_project.util.DateUtil;
import com.vteam.xml_project.util.StringUtil;
import java.security.NoSuchAlgorithmException;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> login(
            @RequestParam String email, String password) throws NoSuchAlgorithmException {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        UserDTO user = new UserDTO();
        user.setEmail(email);
        user.setPassword(password);
        boolean result = userService.checkLogin(user);
        if (result) {
            session.put("email", email);
            returnMap.put("email", email);
            returnMap.put("status", "success");
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> logout() {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String currentUUID = (String) session.get("email");
        session.remove("email");
        returnMap.put("status", "success");
        return returnMap;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> create(
            @RequestParam String email, String password, String fullname) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        UserDTO newUser = new UserDTO();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFullname(fullname);
        //UserInfoDTO newProfile = new UserInfoDTO();
        //newProfile.setFullname(fullname);
        boolean result = userService.createNewUser(newUser);
        if (result) {
            returnMap.put("status", "success");
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> update(
            @RequestParam String address, String birthday, String phone, String formatDate) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        UserDTO newUser = new UserDTO();
        //newUser.setEmail(email);
        //newUser.setPassword(password);
        //newUser.setFullname(fullname);
        //UserInfoDTO newProfile = new UserInfoDTO();
        //newProfile.setFullname(fullname);
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

    @RequestMapping(value = "/get_user_by_id", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> get_user_by_id(
            @RequestParam String id) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();

        UserDTO rs = userService.getUserById(Integer.valueOf(id));
        if (rs != null) {
            returnMap.put("status", "success");
            returnMap.put("user", rs);
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;
    }

    @RequestMapping(value = "/get_user_by_email", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> get_user_by_email() {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String email = (String) session.get("email");
        if (!StringUtil.validString(email)) {
            returnMap.put("status", "unlogin");
            return returnMap;
        }
        UserDTO rs = userService.getUserByEmail(email);
        if (rs != null) {

            returnMap.put("status", "success");
            returnMap.put("user_email", rs.getEmail());
            returnMap.put("user_fullname", rs.getFullname());
            //returnMap.put("session", session.get("email"));
        } else {
            returnMap.put("status", "error");
            returnMap.put("session", "no");
        }
        return returnMap;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> changePassword(
            @RequestParam String currentPass, String newPass) throws NoSuchAlgorithmException {
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
}
