/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.service.BidService;
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
    ProductListDTO getProductList(
            @RequestParam int page, int pageSize) {
        ProductListDTO productResult = productService.getProductList(page, pageSize);
        return productResult;
        //return result;
    }

    @RequestMapping(value = "/searchProduct", method = RequestMethod.POST)
    public @ResponseBody
    ProductListDTO getSearchProductList(
            @RequestParam String txtSearch) {
        ProductListDTO result = productService.searchProduct(txtSearch);
        return result;
    }

    @RequestMapping(value = "/searchProductByTags", method = RequestMethod.POST)
    public @ResponseBody
    HashMap<String, Object> searchProductByTags(
            @RequestParam int tags_id) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        ProductDTO p = productService.getProductById(tags_id);
        if (p != null) {
            returnMap.put("status", "success");
            returnMap.put("search_tags", p);
        } else {
            returnMap.put("status", "error");
            returnMap.put("msg", "Cannot get");
        }
        return returnMap;
    }

    @RequestMapping(value = "/getProductById", method = RequestMethod.POST)
    public @ResponseBody
    ProductDTO getProductById(
            @RequestParam int product_id) {

        ProductDTO result = productService.getProductById(product_id);

        return result;
    }
}
