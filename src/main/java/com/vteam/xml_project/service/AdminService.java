/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.CategoryDTO;
import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.dao.CategoryDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.orm.Bids;
import com.vteam.xml_project.hibernate.orm.Category;
import com.vteam.xml_project.hibernate.orm.Product;
import com.vteam.xml_project.util.DateUtil;
import java.text.ParseException;
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
    private DateUtil dateUtil;

    @Transactional
    public ProductDTO insertProduct(int categoryId, String productName, String description, String img, double minPrice, double maxPrice) {
        ProductDTO productDTO = new ProductDTO();
        try {
            Category category = categoryDAO.getCategoryById(categoryId);
            Product newProduct = new Product(category, productName, description, img, Product.Status.AVAILABLE, minPrice, maxPrice, true);
            productDAO.save(newProduct);
            productDTO.setName(newProduct.getProductName());
            productDTO.setDescription(newProduct.getDescription());
            productDTO.setImage(newProduct.getImage());
            productDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            productDTO.setStatus("error");
            productDTO.setMsg("Have some errors . Try again");
        }
        return productDTO;

    }

    @Transactional
    public ProductDTO updateProduct(int productId, int categoryId, String productName, String description, String img, double minPrice, double maxPrice) {
        ProductDTO productDTO = new ProductDTO();
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
            productDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            productDTO.setStatus("error");
            productDTO.setMsg("Have some errors . Try again");
        }
        return productDTO;
    }

    @Transactional
    public BidDTO insertBid(int product_id, String startDateStr, String endDateStr, int cost) {
        BidDTO bidDTO = new BidDTO();
        try {
            Date startDate = dateUtil.parseFromString(startDateStr, "MM/dd/yyyy HH:mm");
            Date endDate = dateUtil.parseFromString(endDateStr, "MM/dd/yyyy HH:mm");
            Product product = productDAO.getProductById(product_id);
            Bids newBid = new Bids(product, startDate, endDate, Bids.Status.UNCOMPLETED, cost);
            bidDAO.save(newBid);

            bidDTO.setId(newBid.getId());
            bidDTO.setEnd_date(endDate);
            bidDTO.setProduct_id(product_id);
            bidDTO.setProduct_name(product.getProductName());
            bidDTO.setStatus(newBid.getStatus().name());
            bidDTO.setCost(cost);

            // update bid_id of that product
            product.setBidId(newBid.getId());
            product.setStatus(Product.Status.UNAVAILABLE);
            productDAO.save(product);
            bidDTO.setStatus("success");
            return bidDTO;
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            bidDTO.setStatus("error");
            bidDTO.setMsg("Have some errors. Try again");
        } catch (ParseException ex) {
            log.error(ex.getStackTrace());
            bidDTO.setStatus("error");
            bidDTO.setMsg("Wrong date time format");
        }
        return bidDTO;
    }

    @Transactional
    public BidDTO updateBid(int bid_id, int product_id, String startDateStr, String endDateStr, String status, int cost) {
        BidDTO bidDTO = new BidDTO();
        try {
            Date startDate = dateUtil.parseFromString(startDateStr, "MM/dd/yyyy HH:mm");
            Date endDate = dateUtil.parseFromString(endDateStr, "MM/dd/yyyy HH:mm");
            Product product = productDAO.getProductById(product_id);
            Bids newBid = bidDAO.getBidById(bid_id);
            newBid.setCost(cost);
            newBid.setProduct(product);
            newBid.setStartDate(startDate);
            newBid.setEndDate(endDate);
            
            newBid.setStatus(Bids.Status.valueOf(status));
            if (newBid.getStatus().toString().equalsIgnoreCase("completed")) {
                product.setStatus(Product.Status.AVAILABLE);  
                product.setBidId(null);
            }
            product.setBidId(newBid.getId());
            productDAO.save(product);
            bidDAO.save(newBid);
            bidDTO.setEnd_date(endDate);
            bidDTO.setProduct_id(product_id);
            bidDTO.setProduct_name(product.getProductName());
            bidDTO.setStatus(newBid.getStatus().name());
            bidDTO.setCost(cost);
            bidDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            bidDTO.setStatus("error");
            bidDTO.setMsg("Have some errors. Try again");
        }catch (ParseException ex) {
            log.error(ex.getStackTrace());
            bidDTO.setStatus("error");
            bidDTO.setMsg("Wrong date time format");
        }
        return bidDTO;
    }
    
    @Transactional
    public CategoryDTO insertCategory(String categoryName, String description) {
        CategoryDTO categoryDTO = new CategoryDTO();
        try {
             Category category = new Category(categoryName, description);
             categoryDAO.save(category);
             categoryDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            categoryDTO.setStatus("error");
            categoryDTO.setMsg("Have some errors . Try again");
        }
        return categoryDTO;
    }
    @Transactional
    public CategoryDTO updateCategory(int categoryID,String description) {
        CategoryDTO categoryDTO = new CategoryDTO();
        try {
             Category category = categoryDAO.getCategoryById(categoryID);
             category.setDescription(description);
             categoryDAO.save(category);
             categoryDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            categoryDTO.setStatus("error");
            categoryDTO.setMsg("Have some errors . Try again");
        }
        return categoryDTO;
    }
}
