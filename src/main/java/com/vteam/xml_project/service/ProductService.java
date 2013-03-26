/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.dao.TagsDAO;
import com.vteam.xml_project.hibernate.orm.Bids;
import com.vteam.xml_project.hibernate.orm.Product;
import com.vteam.xml_project.hibernate.orm.Tags;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private BidDAO bidDAO;
    @Autowired
    private TagsDAO tagsDAO;

    @Transactional
    public ProductListDTO getProductList(int page, int pageSize) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.getProductList(page, pageSize);
            ProductDTO p;
            Bids bid;
            long time = 0;
            Date currentDate = new Date();
            for (Product d : dbProducts) {
                if (d.getBidId() != null) {
                    bid = bidDAO.getBidById(d.getBidId());
                    time = bid.getStartDate().getTime() - currentDate.getTime();
                }
                p = new ProductDTO();
                p.setBidTimeRemain(time);
                p.setName(d.getProductName());
                p.setDescription(d.getDescription());
                p.setImage("/resources/img/product/" + d.getImage());
                p.setImageName(d.getImage());
                p.setId(d.getId());
                p.setBidId(d.getBidId());
                list.getProductList().add(p);
            }
            list.setNumberOfProduct(productDAO.getNumberOfProduct());
            list.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;

    }

    @Transactional
    public ProductListDTO searchProduct(String txtSearch, int page, int pageSize) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.searchProduct(txtSearch, page, pageSize);
            ProductDTO p;

            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {
                p = new ProductDTO();
                p.setName(d.getProductName());
                p.setId(d.getId());
                p.setDescription(d.getDescription());
                p.setImage("/resources/img/product/" + d.getImage());
                p.setImageName(d.getImage());
                p.setCategoryName(d.getCategory().getCategoryName());
                p.setBidId(d.getBidId());
                tmpList.add(p);
            }
            list.setProductList(tmpList);
            list.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;
    }

    @Transactional
    public ProductDTO getProductById(int id) {
        ProductDTO productDTO = new ProductDTO();
        Bids bid;
        long time = 0;
        Date currentDate = new Date();
        try {
            Product product = productDAO.getProductById(id);
            if (product.getBidId() != null) {
                bid = bidDAO.getBidById(product.getBidId());
                time = bid.getStartDate().getTime() - currentDate.getTime();
            }
            productDTO.setBidTimeRemain(time);
            productDTO.setId(product.getId());
            productDTO.setName(product.getProductName());
            productDTO.setCategoryName(product.getCategory().getCategoryName());
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setDescription(product.getDescription());
            productDTO.setMinPrice(product.getMinPrice());
            productDTO.setMaxPrice(product.getMaxPrice());
            productDTO.setImage("/resources/img/product/" + product.getImage());
            productDTO.setImageName(product.getImage());
            productDTO.setBidId(product.getBidId());
            if (product.getBidId() != null) {
                Bids bids = bidDAO.getBidById(product.getBidId());
                if (bids != null) {
                    productDTO.setBidCost(bids.getCost());
                }
            }
            productDTO.setStatus("success");

        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            productDTO.setStatus("error");
            productDTO.setMsg("Have some errors. Try again");
        }
        return productDTO;
    }

    @Transactional
    public ProductListDTO searchProductByCategoryId(int category_id, int page, int pageSize) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.searchProductByCategoryId(category_id, page, pageSize);
            ProductDTO pd;

            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {
                pd = new ProductDTO();
                pd.setName(d.getProductName());
                pd.setId(d.getId());
                pd.setDescription(d.getDescription());
                pd.setImage("/resources/img/product/" + d.getImage());
                pd.setBidId(d.getBidId());
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
    public ProductListDTO searchProductByTagsId(int tags_id, int page, int pageSize) {
        ProductListDTO list = new ProductListDTO();
        try {
            Tags tag = tagsDAO.getTagById(tags_id);
            List<Product> dbProducts = productDAO.searchProductByTagID(tag, page, pageSize);
            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            ProductDTO pd;
            for (Product d : dbProducts) {
                pd = new ProductDTO();
                pd.setName(d.getProductName());
                pd.setId(d.getId());
                pd.setDescription(d.getDescription());
                pd.setImage("/resources/img/product/" + d.getImage());
                pd.setBidId(d.getBidId());
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

    @Transactional
    public ProductListDTO getProductNameList(int page, int pageSize) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.getProductNameList(page, pageSize);
            ProductDTO p;

            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {

                p = new ProductDTO();
                p.setName(d.getProductName());
                p.setId(d.getId());
                p.setBidId(d.getBidId());

                tmpList.add(p);
            }
            list.setProductList(tmpList);
            list.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getMessage());
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;
    }

    @Transactional
    public ProductListDTO getAllProductNameList(int page, int pageSize) {
        ProductListDTO list = new ProductListDTO();
        try {
            List<Product> dbProducts = productDAO.getAllProductNameList(page, pageSize);
            ProductDTO p;

            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
            for (Product d : dbProducts) {

                p = new ProductDTO();
                p.setName(d.getProductName());
                p.setId(d.getId());
                p.setBidId(d.getBidId());

                tmpList.add(p);
            }
            list.setProductList(tmpList);
            list.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getMessage());
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;
    }
}
