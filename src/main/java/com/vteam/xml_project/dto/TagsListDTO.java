/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phitt60230
 */
public class TagsListDTO extends BaseDTO{
    private List<TagsDTO> tagsList;
    
    public TagsListDTO() {
        tagsList = new ArrayList<TagsDTO>();
    }


    public List<TagsDTO> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<TagsDTO> tagsList) {
        this.tagsList = tagsList;
    }
    
}
