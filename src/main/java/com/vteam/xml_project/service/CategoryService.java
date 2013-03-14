/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.CategoryListDTO;
import com.vteam.xml_project.dto.CategoryDTO;
import com.vteam.xml_project.hibernate.dao.CategoryDAO;
import com.vteam.xml_project.hibernate.orm.Category;
import com.vteam.xml_project.hibernate.orm.Product;
import com.vteam.xml_project.util.DateUtil;
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
        try {
            List<Category> dbCategory = categoryDAO.getCategoryList();
            CategoryListDTO categoryList = new CategoryListDTO();
            CategoryDTO c;
            List<CategoryDTO> tmpList = new ArrayList<CategoryDTO>();
            for (Category category : dbCategory) {
                c = new CategoryDTO();
                c.setName(category.getCategoryName());
                c.setId(category.getId());
                //c.setName(category.getId());
                //c.setImage("/resources/img/product/" + d.getImage());
                tmpList.add(c);
            }
            categoryList.setCategoryList(tmpList);
            return categoryList;
        } catch (HibernateException ex) {
            log.error(ex);
            return null;
        }

    }
    
    @Transactional
    public CategoryDTO getProductByCategoryId(int id) {
        try {
            Category category = categoryDAO.getCategoryById(id);
            
        }catch (HibernateException ex) {
            log.error(ex);
        }
        return null;
    }
}