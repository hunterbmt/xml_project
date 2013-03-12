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
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserSession session;

    @RequestMapping(value = "/")
    @Transactional
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("main");
        return mav;
    }

    @RequestMapping(value = "/admin")
    @Transactional
    public ModelAndView admin(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin");
        return mav;
    }
    
    @RequestMapping(value = "/catalogs")
    @Transactional
    public ModelAndView catalogs(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("catalogs");
        return mav;
    }
    @RequestMapping(value = "/keywords")
    @Transactional
    public ModelAndView keywords(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("keywords");
        return mav;
    }
    
}
