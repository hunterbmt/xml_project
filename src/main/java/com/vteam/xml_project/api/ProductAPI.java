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
@RequestMapping(value = "/product")
public class ProductAPI {
    @Autowired
    private UserSession session;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/getProductList", method = RequestMethod.POST)
    public @ResponseBody 
    HashMap<String, Object> getProductList( 
            @RequestParam int page, int pageSize) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        ProductListDTO product_result = productService.getProductList(page,pageSize);
        if (product_result != null) {
            returnMap.put("status", "success");
            returnMap.put("product_result", product_result);
        } else {
            returnMap.put("status", "error");
            returnMap.put("msg", "Cannot get");
        }
        return returnMap;
        //return result;
    } 
    
    @RequestMapping(value = "/searchProduct", method = RequestMethod.POST)
    public @ResponseBody 
    HashMap<String, Object> getSearchProductList( 
            @RequestParam String txtSearch) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        ProductListDTO search_result = productService.searchProduct(txtSearch);
        if (search_result != null) {
            returnMap.put("status", "success_search");
            returnMap.put("search_result", search_result);
        } else {
            returnMap.put("status", "error");
            returnMap.put("msg", "Cannot get");
        }
        return returnMap;
        //return result;
    }
}
