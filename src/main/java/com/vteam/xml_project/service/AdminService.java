/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.hibernate.dao.CategoryDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.orm.Category;
import com.vteam.xml_project.hibernate.orm.Product;
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
public class AdminService {

    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CategoryDAO categoryDAO;

    @Transactional
    public ProductDTO insertProduct(int categoryId, String productName, String description, String img, double minPrice, double maxPrice) {
        try {
            Category category = categoryDAO.getCategoryById(categoryId);
            Product newProduct = new Product(category, productName, description, img, Product.Status.AVAILABLE, minPrice, maxPrice, true);
            ProductDTO productDTO;
            productDAO.save(newProduct);
            productDTO = new ProductDTO();
            productDTO.setName(newProduct.getProductName());
            productDTO.setDescription(newProduct.getDescription());
            productDTO.setImage(newProduct.getImage());
            return productDTO;
        } catch (HibernateException ex) {
            log.error(ex);
            return null;
        }

    }
}
