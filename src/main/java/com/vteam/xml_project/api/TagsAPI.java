/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.TagsListDTO;
import com.vteam.xml_project.service.TagsService;
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
@RequestMapping(value = "/tags")
public class TagsAPI {

    @Autowired
    private UserSession session;
    @Autowired
    private TagsService tagsService;

    @RequestMapping(value = "/getTagsByProductId", method = RequestMethod.POST)
    public @ResponseBody
    TagsListDTO getTagsByProductId(
            @RequestParam int product_id) {
        TagsListDTO resultTags = tagsService.getTagsByProductId(product_id);
        return resultTags;
    }
    
    @RequestMapping(value = "/getTagsList")
    public @ResponseBody
    TagsListDTO getTagsList() {
        TagsListDTO resultTags = tagsService.getTagsList();
        return resultTags;
    }
}
