/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.orm.Product;
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
public class ProductService {
    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
    private ProductDAO productDAO;
     
     @Transactional
     public ProductDTO getProductList(int page, int pageSize) {
         try {
             List<Product> dbProducts=  productDAO.getProductList(page, pageSize);
             ProductDTO p = new ProductDTO();
             p.setName(dbProducts.get(0).getProductName());
             p.setDescription(dbProducts.get(1).getDescription());
             p.setImage(dbProducts.get(2).getImage());
             return p;
         } catch (HibernateException ex) {
             log.error(ex);
             return null;
         }
         
     }
}