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
import java.util.Set;
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
                p.setImageName(d.getImage());
                p.setId(d.getId());
                p.setBid_id(d.getBid_id());

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
                p.setImageName(d.getImage());
                p.setCategoryName(d.getCategory().getCategoryName());
                p.setBid_id(d.getBid_id());
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
            p.setCategoryName(product.getCategory().getCategoryName());
            p.setDescription(product.getDescription());
            p.setMinPrice(product.getMinPrice());
            p.setMaxPrice(product.getMaxPrice());
            p.setImage("/resources/img/product/" + product.getImage());
            p.setImageName(product.getImage());
            p.setBid_id(product.getBid_id());
            return p;

        } catch (HibernateException ex) {
            log.error(ex);
        }
        return null;
    }

    @Transactional
    public ProductListDTO searchProductByCategoryId(int category_id) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.searchProductByCategoryId(category_id);
            ProductDTO pd;

            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {
                pd = new ProductDTO();
                pd.setName(d.getProductName());
                pd.setId(d.getId());
                pd.setDescription(d.getDescription());
                pd.setImage("/resources/img/product/" + d.getImage());
                pd.setBid_id(d.getBid_id());
                tmpList.add(pd);
            }
            list.setProductList(tmpList);
            list.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            list.setStatus("error");
            list.setMsg("Have some errors ! Try again");
        }
        return list;
    }

    @Transactional
    public ProductListDTO searchProductByTagsId(int tags_id) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.searchProductByTagsId(tags_id);

            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            ProductDTO pd;
            for (Product d : dbProducts) {
                pd = new ProductDTO();
                pd.setName(d.getProductName());
                pd.setId(d.getId());
                pd.setDescription(d.getDescription());
                pd.setImage("/resources/img/product/" + d.getImage());
                pd.setBid_id(d.getBid_id());
                tmpList.add(pd);
            }
            list.setProductList(tmpList);
            list.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            list.setStatus("error");
            list.setMsg("Have some errors . Try again");
        }
        return list;
    }
}
