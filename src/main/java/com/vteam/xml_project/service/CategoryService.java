/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.CategoryListDTO;
import com.vteam.xml_project.dto.CategoryDTO;
import com.vteam.xml_project.hibernate.dao.CategoryDAO;
import com.vteam.xml_project.hibernate.orm.Category;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phitt60230
 */
@Service
public class CategoryService {

    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
    private CategoryDAO categoryDAO;

    @Transactional
    public CategoryListDTO getCategoryList() {
        CategoryListDTO categoryList = new CategoryListDTO();
        try {
            List<Category> dbCategory = categoryDAO.getCategoryList();
            CategoryDTO c;
            List<CategoryDTO> tmpList = new ArrayList<CategoryDTO>();
            for (Category category : dbCategory) {
                c = new CategoryDTO();
                c.setName(category.getCategoryName());
                c.setId(category.getId());
                tmpList.add(c);
            }
            categoryList.setCategoryList(tmpList);
            categoryList.setStatus("success");

        } catch (HibernateException ex) {
            log.error(ex);
            categoryList.setMsg("Have some errors. Try again");
            categoryList.setStatus("error");
        }
        return categoryList;
    }

    @Transactional
    public CategoryListDTO getCategoryNameList() {
        CategoryListDTO categoryList = new CategoryListDTO();
        try {
            List<Category> categories = categoryDAO.getCategoryList();
            CategoryDTO category;
            for (Category c : categories) {
                category = new CategoryDTO();
                category.setName(c.getCategoryName());
                category.setId(c.getId());
                categoryList.getCategoryList().add(category);
            }
            categoryList.setStatus("error");
        } catch (HibernateException ex) {
            log.error(ex);
            categoryList.setMsg("Have some errors. Try again");
            categoryList.setStatus("error");
        }
        return categoryList;
    }

    @Transactional
    public CategoryDTO getCategoryDetail(int categoryID) {
        CategoryDTO categoryDTO = new CategoryDTO();
        try {
            Category dbCategory = categoryDAO.getCategoryById(categoryID);
            categoryDTO.setName(dbCategory.getCategoryName());
            categoryDTO.setDescription(dbCategory.getDescription());
            categoryDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            categoryDTO.setMsg("Have some errors. Try again");
            categoryDTO.setStatus("error");
        }
        return categoryDTO;
    }
}
