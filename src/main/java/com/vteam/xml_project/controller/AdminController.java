/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.controller;

import com.vteam.xml_project.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserSession session;

    @RequestMapping(value = "/product")
    @Transactional
    public ModelAndView admin_product(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin_product");
        return mav;
    }
    
    @RequestMapping(value = "/bid")
    @Transactional
    public ModelAndView admin_bid(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin_bid");
        
        return mav;
    }
    
    
    
    
    
    
    
}
