/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.CategoryDTO;
import com.vteam.xml_project.dto.CategoryListDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.service.CategoryService;
import com.vteam.xml_project.service.ProductService;
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
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/getCategoryList",produces = "application/json")
    public @ResponseBody
    CategoryListDTO getCategoryList() {

        CategoryListDTO result = categoryService.getCategoryList();
        return result;
    }

    @RequestMapping(value = "/searchProductByCategoryId", method = RequestMethod.POST,produces = "application/json")
    public @ResponseBody
    ProductListDTO searchProductByCategoryId(
            @RequestParam int categoryId, int page) {
        ProductListDTO searchCategoryResult = productService.searchProductByCategoryId(categoryId,page,false);
        return searchCategoryResult;
    }
    @RequestMapping(value = "/getCategoryDetail", method = RequestMethod.POST)
    public @ResponseBody CategoryDTO getCategoryDetail(
            @RequestParam int category_id) {
        CategoryDTO result = categoryService.getCategoryDetail(category_id);
        return result;
    }
}
