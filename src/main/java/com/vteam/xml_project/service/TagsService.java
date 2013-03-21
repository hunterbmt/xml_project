/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.TagsDTO;
import com.vteam.xml_project.dto.TagsListDTO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.orm.Tags;
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
public class TagsService {

    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
    private ProductDAO productDAO;

    @Transactional
    public TagsListDTO getTagsByProductId(int product_id) {
        TagsListDTO listTags = new TagsListDTO();
        try {

            Set<Tags> dbTags = productDAO.getProductById(product_id).getTagses();
            TagsDTO td;

            List<TagsDTO> tmpListTags = new ArrayList<TagsDTO>();

            for (Tags t : dbTags) {
                td = new TagsDTO();
                td.setTagName(t.getName());
                td.setTagId(t.getId());
                td.setTagDescription(t.getDescription());
                tmpListTags.add(td);
            }
            listTags.setTagsList(tmpListTags);
            listTags.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            listTags.setStatus("error");
            listTags.setMsg("Have some errors. Try again");
        }
        return listTags;
    }
//    @Transactional
//    public ProductListDTO getProductList(int page, int pageSize) {
//        try {
//            List<Product> dbProducts = productDAO.getProductList(page, pageSize);
//            ProductDTO p;
//            ProductListDTO list = new ProductListDTO();
//            List<ProductDTO> tmpList = new ArrayList<ProductDTO>();
//            for (Product d : dbProducts) {
//
//                p = new ProductDTO();
//                p.setName(d.getProductName());
//                p.setDescription(d.getDescription());
//                p.setImage("/resources/img/product/" + d.getImage());
//                p.setId(d.getId());
//                tmpList.add(p);
//            }
//            list.setProductList(tmpList);
//            return list;
//        } catch (HibernateException ex) {
//            log.error(ex);
//            return null;
//        }
//
//    }
}
