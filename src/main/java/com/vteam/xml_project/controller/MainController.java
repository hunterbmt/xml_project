/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.controller;

import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.hibernate.orm.Users;
import com.vteam.xml_project.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Administrator
 */
@Controller
public class MainController {

    private static final String VIEW_LISTING = "userInfo";
    @Autowired
    private UserService userService;
    @Autowired
    private UserSession session;
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/")
    @Transactional
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("product_list");
        return mav;
    }

    @RequestMapping(value = "/admin")
    @Transactional
    public ModelAndView admin(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin");
        return mav;
    }

    @RequestMapping(value = "/product")
    @Transactional
    public ModelAndView productList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("product_list");
        return mav;
    }

    @RequestMapping(value = "/product/detail", method = RequestMethod.GET)
    @Transactional
    public String productDetail(HttpServletRequest request, HashMap<String, Object> m) {
        m.put("pid", request.getParameter("pid"));
        return "product_detail";
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

    @RequestMapping(value = "user/detail", method = RequestMethod.GET)
    @Transactional
    public String showListingPage(Map<String, Object> model) {
        String viewName = "userInfo";
        Users user = new Users();

        user = userDAO.findUserByEmail((String) session.get("email"));

        model.put("user_email", user.getEmail());
        model.put("user_fullname", user.getFullname());
        return viewName;
    }

    @RequestMapping(value = "/test")
    @Transactional
    public ModelAndView testPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("test");
        return mav;
    }
}
