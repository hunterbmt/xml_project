/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.dao.CategoryDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.orm.Bids;
import com.vteam.xml_project.hibernate.orm.Category;
import com.vteam.xml_project.hibernate.orm.Product;
import java.util.Date;
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
    @Autowired
    private BidDAO bidDAO;

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
    
    @Transactional
    public boolean updateProduct(int productId,int categoryId, String productName, String description, String img, double minPrice, double maxPrice) {
        try {
            Product product = productDAO.getProductById(productId);
            Category category = categoryDAO.getCategoryById(categoryId);
            product.setCategory(category);
            product.setProductName(productName);
            product.setDescription(description);
            product.setImage(img);
            product.setMinPrice(minPrice);
            product.setMaxPrice(maxPrice);
            productDAO.save(product);
            return true;
        } catch (HibernateException ex) {
            log.error(ex);
            return false;
        }
    }
    
    @Transactional
    public BidDTO insertBid(int product_id, Date start_date, Date end_date, int cost) {
        try {            
            Product product = productDAO.getProductById(product_id);
            Bids newBid = new Bids(product, start_date, end_date, Bids.Status.UNCOMPLETED, cost);
            bidDAO.save(newBid);
            BidDTO bidDTO = new BidDTO();
            bidDTO.setId(newBid.getId());
            bidDTO.setEnd_date(end_date);
            bidDTO.setProduct_id(product_id);
            bidDTO.setProduct_name(product.getProductName());
            bidDTO.setStatus(newBid.getStatus().name());
            bidDTO.setCost(cost);
            
            // update bid_id of that product
            product.setBid_id(newBid.getId());
            productDAO.save(product);
            
            return bidDTO;
        } catch (HibernateException ex) {
            log.error(ex);
            return null;
        } catch (Exception ex) {
            log.error(ex);
            return null;
        }
    }
    @Transactional
    public BidDTO updateBid(int bid_id, int product_id, Date start_date, Date end_date, String status, int cost) {
        try {
            Product product = productDAO.getProductById(product_id);
            Bids newBid = bidDAO.getBidById(bid_id);
            newBid.setCost(cost);
            newBid.setProduct(product);
            newBid.setStartDate(start_date);
            newBid.setEndDate(end_date);
            newBid.setStatus(Bids.Status.valueOf(status));
            bidDAO.save(newBid);
            BidDTO bidDTO = new BidDTO();

            bidDTO.setEnd_date(end_date);
            bidDTO.setProduct_id(product_id);
            bidDTO.setProduct_name(product.getProductName());
            bidDTO.setStatus(newBid.getStatus().name());
            bidDTO.setCost(cost);

            return bidDTO;
        } catch (HibernateException ex) {
            log.error(ex);
            return null;
        }
    }
}
