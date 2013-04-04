/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.service.BidHistoryService;
import com.vteam.xml_project.service.ProductService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private BidHistoryService bhService;

    @RequestMapping(value = "/getProductList", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ProductListDTO getProductList(
            @RequestParam int page) {
        ProductListDTO productResult = productService.getProductList(page);
        return productResult;
    }

    @RequestMapping(value = "/searchProduct", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ProductListDTO getSearchProductList(
            @RequestParam String txtSearch, int page) {
        ProductListDTO result = productService.searchProduct(txtSearch, page);
        return result;
    }

    @RequestMapping(value = "/getRecentBidder", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    List<String> getRecentBidder(
            @RequestParam int bid_id) {
        List<String> result = bhService.getRecentBidderList(bid_id);
        return result;
    }

    @RequestMapping(value = "/searchProductByTags", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ProductListDTO searchProductByTags(
            @RequestParam int tag_id, int page) {
        ProductListDTO result = productService.searchProductByTagsId(tag_id, page);
        return result;
    }

    @RequestMapping(value = "/getProductById", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ProductDTO getProductById(
            @RequestParam int product_id) {
        ProductDTO result = productService.getProductById(product_id);
        return result;
    }

}
