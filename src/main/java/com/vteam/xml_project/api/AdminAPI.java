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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private UserSession userSession;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/insert_product", method = RequestMethod.POST)
    public @ResponseBody
    ProductDTO insertProduct(
            @RequestParam int categoryId, String productName, String description, String img, double minPrice, double maxPrice) {
        ProductDTO productDTO = adminService.insertProduct(categoryId, productName, description, img, minPrice, maxPrice);
        return productDTO;
    }

    @RequestMapping(value = "/update_product", method = RequestMethod.POST)
    public @ResponseBody
    ProductDTO updateProduct(
            @RequestParam int productId, int categoryId, String productName, String description, String img, double minPrice, double maxPrice) {
        ProductDTO result = adminService.updateProduct(productId, categoryId, productName, description, img, minPrice, maxPrice);
        return result;
    }

    @RequestMapping(value = "/update_bid", method = RequestMethod.POST)
    public @ResponseBody
    BidDTO updateBid(
            @RequestParam int bid_id, int product_id,
            String start_date, String end_date, String status,
            int cost) {
        BidDTO bDTO = adminService.updateBid(bid_id, product_id,
                start_date, end_date,
                status, cost);
        return bDTO;
    }

    @RequestMapping(value = "/insert_bid", method = RequestMethod.POST)
    public @ResponseBody
    BidDTO insertBid(
            @RequestParam int product_id, String start_date,
            String end_date, int cost) {
        Date currDate = new Date();
        Date nextDate = new Date(currDate.getTime() + 3600 * 24);

        start_date = (start_date.trim().equals("") ? currDate.toString() : start_date.trim());
        end_date = (end_date.trim().equals("") ? nextDate.toString() : end_date.trim());
        BidDTO bDTO = adminService.insertBid(product_id, start_date, end_date, cost);
        return bDTO;
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
