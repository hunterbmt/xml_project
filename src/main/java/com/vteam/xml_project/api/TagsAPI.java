/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.api;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.TagsListDTO;
import com.vteam.xml_project.service.TagsService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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
    HashMap<String, Object> getTagsByProductId(
            @RequestParam int product_id) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();

        TagsListDTO resultTags = tagsService.getTagsByProductId(product_id);
        if (resultTags != null) {
            returnMap.put("status", "getTagsSuccess");
            returnMap.put("result", resultTags);
        } else {
            returnMap.put("status", "error");
            returnMap.put("msg", "Cannot get");
        }
        return returnMap;
    }
}
