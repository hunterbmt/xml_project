/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.dto.SearchKeywordListDTO;
import com.vteam.xml_project.service.ProductService;
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
@RequestMapping(value = "/search")
public class SearchAPI {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/searchTypeAHeader", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    SearchKeywordListDTO searchTypeAHeader(
            @RequestParam String query) {
        SearchKeywordListDTO result = productService.searchSearchCacheKeyWord(query);
        return result;
    }
}
