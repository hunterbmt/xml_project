/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.service.UserService;
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
            returnMap.put("email", email);
            returnMap.put("status", "success");
            session.put("Email", email);
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> logout(
            @RequestParam String uuid) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String currentUUID = (String) session.get("UUID");
        if (currentUUID.equals(uuid)) {
            returnMap.put("status", "success");
            session.remove("UUID");
        } else {
            returnMap.put("status", "error");
        }
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
            @RequestParam String email, String address, String date,String phone) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        UserDTO newUser = new UserDTO();
        //newUser.setEmail(email);
        //newUser.setPassword(password);
        //newUser.setFullname(fullname);
        //UserInfoDTO newProfile = new UserInfoDTO();
        //newProfile.setFullname(fullname);
        boolean result = userService.upadateUser(email, address, phone, null);
        if (result) {
            returnMap.put("status", "success");
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;
    }
}
