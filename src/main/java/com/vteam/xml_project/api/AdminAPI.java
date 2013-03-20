/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.CategoryDTO;
import com.vteam.xml_project.dto.CategoryListDTO;
import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.service.AdminService;
import com.vteam.xml_project.service.CategoryService;
import com.vteam.xml_project.service.ProductService;
import com.vteam.xml_project.util.DateUtil;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "/admin")
public class AdminAPI {

    private static Logger logger = Logger.getLogger(AdminAPI.class.getName());
    @Autowired
    private UserSession userSession;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DateUtil dateUtil;

    @RequestMapping(value = "/insert_product", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> insertProduct(
            @RequestParam int categoryId, String productName, String description, String img, double minPrice, double maxPrice) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        ProductDTO productDTO = adminService.insertProduct(categoryId, productName, description, img, minPrice, maxPrice);
        if (productDTO == null) {
            returnMap.put("status", "error");
        } else {
            returnMap.put("status", "success_ok");
            returnMap.put("product", productDTO);
        }
        return returnMap;
    }

    @RequestMapping(value = "/update_product", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> updateProduct(
            @RequestParam int productId, int categoryId, String productName, String description, String img, double minPrice, double maxPrice) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        boolean result = adminService.updateProduct(productId, categoryId, productName, description, img, minPrice, maxPrice);
        if (!result) {
            returnMap.put("status", "error");
        } else {
            returnMap.put("status", "success_ok");
        }
        return returnMap;
    }

    @RequestMapping(value = "/update_bid", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> updateBid(
            @RequestParam int bid_id, int product_id,
            String start_date, String end_date, String status) {
        try {
            HashMap<String, Object> returnMap = new HashMap<String, Object>();
            BidDTO bDTO = adminService.updateBid(bid_id, product_id,
                    dateUtil.parseFromString(start_date, "MM/dd/yyyy HH:mm"),
                    dateUtil.parseFromString(end_date, "MM/dd/yyyy HH:mm"),
                    status);
            if (bDTO == null) {
                returnMap.put("status", "error");
            } else {
                returnMap.put("status", "success_ok");
                returnMap.put("bid", bDTO);
            }
            return returnMap;
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(AdminAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @RequestMapping(value = "/insert_bid", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> insertBid(
            @RequestParam int product_id,
            String start_date,
            String end_date) {
        try {
            HashMap<String, Object> returnMap = new HashMap<String, Object>();
            Date currDate = new Date();
            Date nextDate = new Date(currDate.getTime() + 3600 * 24);
            start_date = (start_date.trim().equals("") ? currDate.toString() : start_date.trim());
            end_date = (end_date.trim().equals("") ? nextDate.toString() : end_date.trim());
            BidDTO bDTO = adminService.insertBid(product_id,
                    dateUtil.parseFromString(start_date, "MM/dd/yyyy HH:mm"),
                    dateUtil.parseFromString(end_date, "MM/dd/yyyy HH:mm"));
            if (bDTO == null) {
                returnMap.put("status", "error");
            } else {
                returnMap.put("status", "success_ok");
                returnMap.put("bid", bDTO);
            }
            return returnMap;
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(AdminAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @RequestMapping(value = "/getProductNameList")
    public @ResponseBody
    HashMap<Integer, String> admin_bid_getPList(HttpServletRequest request) {

        ProductListDTO productList = productService.getProductList(1, 99999);
        List<ProductDTO> list = productList.getProductList();
        HashMap<Integer, String> ProductNameList = new HashMap<Integer, String>();
        for (int i = 0; i < list.size(); i++) {
            ProductNameList.put(list.get(i).getId(), list.get(i).getName());
        }
        return ProductNameList;
    }

    @RequestMapping(value = "/getCategoryNameList", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<Integer, String> getCategoryNameList() {

        CategoryListDTO categoryListDTO = categoryService.getCategoryNameList();
        List<CategoryDTO> list = categoryListDTO.getCategoryList();
        HashMap<Integer, String> categoryNameList = new HashMap<Integer, String>();
        for (int i = 0; i < list.size(); i++) {
            categoryNameList.put(list.get(i).getId(), list.get(i).getName());
        }
        return categoryNameList;
    }
}
