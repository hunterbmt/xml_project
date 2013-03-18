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
import com.vteam.xml_project.hibernate.dao.UserDAO;
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
    @Autowired
    private UserDAO userDAO;

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

    public BidDTO insertBid(int last_user, int product_id, double current_price, Date last_edit, Date start_date, Date end_date) {
        try {
            Product product = productDAO.getProductById(product_id);
            Bids newBid = new Bids(product, last_user, current_price, last_edit, start_date, end_date);
            bidDAO.save(newBid);
            BidDTO bidDTO = new BidDTO();
            bidDTO.setId(newBid.getId());
            bidDTO.setCurrent_price(current_price);
            bidDTO.setEnd_date(end_date);
            bidDTO.setLast_edit(last_edit);
            bidDTO.setLast_userid(last_user);
            bidDTO.setProduct_id(product_id);
            bidDTO.setProduct_name(product.getProductName());
            bidDTO.setStatus(newBid.getStatus().name());
            bidDTO.setLast_username(userDAO.findUserByUuid(new Integer(last_user)).getFullname());
            return bidDTO;
        } catch (HibernateException ex) {
            log.error(ex);
            return null;
        } catch (Exception ex) {
            log.error(ex);
            return null;
        }
    }
    
    public BidDTO updateBid(int bid_id, int last_user, int product_id, double current_price, Date last_edit, Date start_date, Date end_date, String status) {
        try {
            Product product = productDAO.getProductById(product_id);
            Bids newBid = new Bids(bid_id, product, last_user, current_price,
                    start_date, end_date, last_edit, status);
            bidDAO.save(newBid);
            BidDTO bidDTO = new BidDTO();
            bidDTO.setCurrent_price(current_price);
            bidDTO.setEnd_date(end_date);
            bidDTO.setLast_edit(last_edit);
            bidDTO.setLast_userid(last_user);
            bidDTO.setProduct_id(product_id);
            bidDTO.setProduct_name(product.getProductName());
            bidDTO.setStatus(newBid.getStatus().name());
            bidDTO.setLast_username(userDAO.findUserByUuid(new Integer(last_user)).getFullname());
            return bidDTO;
        } catch (HibernateException ex) {
            log.error(ex);
            return null;
        }
    }
}
