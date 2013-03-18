/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
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
public class ProductService {

    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
    private ProductDAO productDAO;

    @Transactional
    public ProductListDTO getProductList(int page, int pageSize) {
        try {
            List<Product> dbProducts = productDAO.getProductList(page, pageSize);
            ProductDTO p;
            ProductListDTO list = new ProductListDTO();
            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {

                p = new ProductDTO();
                p.setName(d.getProductName());
                p.setDescription(d.getDescription());
                p.setImage("/resources/img/product/" + d.getImage());
                p.setId(d.getId());
                tmpList.add(p);
            }
            list.setProductList(tmpList);
            return list;
        } catch (HibernateException ex) {
            log.error(ex);
            return null;
        }

    }

    @Transactional
    public ProductListDTO searchProduct(String txtSearch) {
        try {
            List<Product> dbProducts = productDAO.searchProduct(txtSearch);
            ProductDTO p;
            ProductListDTO list = new ProductListDTO();
            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {
                p = new ProductDTO();
                p.setName(d.getProductName());
                p.setId(d.getId());
                p.setDescription(d.getDescription());
                p.setImage("/resources/img/product/" + d.getImage());
                tmpList.add(p);
            }
            list.setProductList(tmpList);
            return list;
        } catch (HibernateException ex) {
            log.error(ex);
        }
        return null;
    }

    @Transactional
    public ProductDTO getProductById(int id) {
        try {
            Product product = productDAO.getProductById(id);
            ProductDTO p = new ProductDTO();
            p.setId(product.getId());
            p.setName(product.getProductName());
            p.setImage("/resources/img/product/" + product.getImage());
            p.setDescription(product.getDescription());
            return p;

        } catch (HibernateException ex) {
            log.error(ex);
        }
        return null;
    }
    
    @Transactional
    public ProductListDTO searchProductByCategoryId(int category_id) {
        try {
            List<Product> dbProducts = productDAO.searchProductByCategoryId(category_id);
            ProductDTO pd;
            ProductListDTO list = new ProductListDTO();
            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {
                pd = new ProductDTO();
                pd.setName(d.getProductName());
                pd.setId(d.getId());
                pd.setDescription(d.getDescription());
                pd.setImage("/resources/img/product/" + d.getImage());
                tmpList.add(pd);
            }
            list.setProductList(tmpList);
            return list;
        } catch (HibernateException ex){
            log.error(ex);
        }
        return null;
    }
}
