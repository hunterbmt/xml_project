/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author phitt60230
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "categoryList")
public class CategoryListDTO extends BaseDTO{
    @XmlElement(name = "category", type = CategoryDTO.class)
    List<CategoryDTO> categoryList;
    public CategoryListDTO() {
        categoryList = new ArrayList<CategoryDTO>();
    }

    public List<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDTO> categoryList) {
        this.categoryList = categoryList;
    }
    
}
