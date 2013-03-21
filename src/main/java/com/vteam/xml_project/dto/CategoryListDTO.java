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
public class CategoryListDTO extends BaseDTO{
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
