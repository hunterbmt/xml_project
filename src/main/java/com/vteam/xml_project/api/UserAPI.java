/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.dto.UserProfileDTO;
import com.vteam.xml_project.service.UserService;
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
            @RequestParam String email, String password) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        boolean result = userService.checkLogin(email, password);
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
            @RequestParam String uuid, String password, String name, String email, String phoneNumber) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        UserDTO newUser = new UserDTO();
        newUser.setUuid(uuid);
        newUser.setPassword(password);
        UserProfileDTO newProfile = new UserProfileDTO();
        newProfile.setEmail(email);
        newProfile.setName(name);
        newProfile.setPhoneNumber(phoneNumber);
        boolean result = userService.createNewUser(newUser, newProfile);
        if (result) {
            returnMap.put("status", "success");
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;
    }
}
