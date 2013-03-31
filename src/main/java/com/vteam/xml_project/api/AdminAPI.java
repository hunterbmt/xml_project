/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.CategoryDTO;
import com.vteam.xml_project.dto.CategoryListDTO;
import com.vteam.xml_project.dto.NinCodeListDTO;
import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.dto.TagsDTO;
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

    @RequestMapping(value = "/getProductList", method = RequestMethod.POST,produces = "application/json")
    public @ResponseBody
    ProductListDTO insertProduct(
            @RequestParam int page, int pageSize) {
        ProductListDTO productsDTO = adminService.getProductList(page, pageSize);
        return productsDTO;
    }
    
    @RequestMapping(value = "/insert_product", method = RequestMethod.POST,produces = "application/json")
    public @ResponseBody
    ProductDTO insertProduct(
            @RequestParam int categoryId, String productName, String description, String img, double minPrice, double maxPrice,String tags) {
        ProductDTO productDTO = adminService.insertProduct(categoryId, productName, description, img, minPrice, maxPrice,tags);
        return productDTO;
    }

    @RequestMapping(value = "/update_product", method = RequestMethod.POST,produces = "application/json")
    public @ResponseBody
    ProductDTO updateProduct(
            @RequestParam int productId, int categoryId, String productName, String description, String img, double minPrice, double maxPrice, String tags) {
        ProductDTO result = adminService.updateProduct(productId, categoryId, productName, description, img, minPrice, maxPrice,tags);
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
        BidDTO bDTO = adminService.insertBid(product_id,
                start_date.toString(), end_date.toString(), cost);
        return bDTO;
    }

    @RequestMapping(value = "/getProductNameList")
    public @ResponseBody
    HashMap<Integer, String> admin_bid_getPList(HttpServletRequest request) {

        ProductListDTO productList = productService.getProductNameList(1, 99999);
        List<ProductDTO> list = productList.getProductList();
        HashMap<Integer, String> ProductNameList = new HashMap<Integer, String>();
        for (int i = 0; i < list.size(); i++) {
                ProductNameList.put(list.get(i).getId(), list.get(i).getName());           
        }
        return ProductNameList;
    }
    
    @RequestMapping(value = "/getAllProductNameList")
    public @ResponseBody
    HashMap<Integer, String> admin_bid_getAllProductNameList(HttpServletRequest request) {

        ProductListDTO productList = productService.getAllProductNameList(1, 99999);
        List<ProductDTO> list = productList.getProductList();
        HashMap<Integer, String> ProductNameList = new HashMap<Integer, String>();
        for (int i = 0; i < list.size(); i++) {
                ProductNameList.put(list.get(i).getId(), list.get(i).getName());           
        }
        return ProductNameList;
    }
    
    

    @RequestMapping(value = "/getCategoryNameList", method = RequestMethod.POST)
    public @ResponseBody
    CategoryListDTO getCategoryNameList() {

        CategoryListDTO categoryListDTO = categoryService.getCategoryList();
        return categoryListDTO;
    }

    @RequestMapping(value = "/insert_category", method = RequestMethod.POST)
    public @ResponseBody
    CategoryDTO insertCategory(
            @RequestParam String categoryName, String description) {
        CategoryDTO categoryDTO = adminService.insertCategory(categoryName, description);
        return categoryDTO;
    }

    @RequestMapping(value = "/update_category", method = RequestMethod.POST)
    public @ResponseBody
    CategoryDTO updateCategory(
            @RequestParam int categoryId, String description) {
        CategoryDTO categoryDTO = adminService.updateCategory(categoryId, description);
        return categoryDTO;
    }
    
    @RequestMapping(value = "/generate_nin", method = RequestMethod.POST)
    public @ResponseBody
    NinCodeListDTO generateNin(
            @RequestParam int amount, int quantity) {
        NinCodeListDTO ninCodeListDTO = adminService.generateNin(amount, quantity);
        return ninCodeListDTO;
    }
    
    @RequestMapping(value = "/insert_tag", method = RequestMethod.POST)
    public @ResponseBody
    TagsDTO insertTag(
            @RequestParam String tagName, String description) {
        TagsDTO tagDTO = adminService.insertTag(tagName, description);
        return tagDTO;
    }
    
        
    @RequestMapping(value = "/update_tag", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    TagsDTO updateTag(
            @RequestParam int tagId, String description) {
        TagsDTO tagDTO = adminService.updateTag(tagId, description);
        return tagDTO;
    }
}
