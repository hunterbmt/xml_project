/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.CategoryListDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.service.CategoryService;
import com.vteam.xml_project.service.ProductService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author phitt60230
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryAPI {
    @Autowired
    private UserSession session;
    @Autowired
    private CategoryService categoryService;
                   
    @RequestMapping (value = "/getCategoryList", method = RequestMethod.GET)
    public @ResponseBody
    HashMap<String, Object> getCategoryList() {
        HashMap<String, Object> returnMap =  new HashMap<String, Object>();
        
        CategoryListDTO result = categoryService.getCategoryList();
        if (result != null) {
            returnMap.put("status", "success");
            returnMap.put("result", result);
        } else {
            returnMap.put("status", "error");
            returnMap.put("msg", "Cannot get");
        }
        return returnMap;
    }
}