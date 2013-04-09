        /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.dto.CategoryDTO;
import com.vteam.xml_project.dto.CategoryListDTO;
import com.vteam.xml_project.dto.NinCodeDTO;
import com.vteam.xml_project.dto.UserPaymentDTO;
import com.vteam.xml_project.dto.UserPaymentListDTO;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.dto.UserListDTO;
import com.vteam.xml_project.hibernate.dao.CardCodeDAO;

import com.vteam.xml_project.hibernate.orm.Users;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.hibernate.dao.UserPaymentDAO;
import com.vteam.xml_project.hibernate.orm.CardCode;
import com.vteam.xml_project.hibernate.orm.UserPayment;
import com.vteam.xml_project.util.DateUtil;
import com.vteam.xml_project.util.PhoneNumberUtil;
import com.vteam.xml_project.util.StringUtil;
import com.vteam.xml_project.util.XMLUtil;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Service
public class XMLReloadService {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    ServletContext servletContext;
    @Autowired
    ProductService productService;
    @Autowired
    BidService bidService;
    @Autowired
    OrderHistoryService orderService;
    private static String CATEGORY_XML_FILE_NAME = "category.xml";
    private static String USER_XML_FILE_NAME = "user.xml";
    private static String BID_XML_FILE_NAME = "bids.xml";

    public void marshallCategory() {
        try {
            CategoryListDTO categoryListDTO = categoryService.getCategoryList();
            for (CategoryDTO categoryDTO : categoryListDTO.getCategoryList()) {
                categoryDTO.setProductListDTO(productService.searchProductByCategoryId(categoryDTO.getId(), 1, true));
            }
            String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/");
            XMLUtil.Marshall(categoryListDTO, realPath + "/" + CATEGORY_XML_FILE_NAME);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    public void marshallUser() {
        try {
            UserListDTO userListDTO = userService.getUserList();
            for (UserDTO userDTO : userListDTO.getUserList()) {
                userDTO.setOderListDTO(orderService.getOrderListByUserID(userDTO.getId()));
            }
            String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/");
            XMLUtil.Marshall(userListDTO, realPath + "/" + USER_XML_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void marshallBids() {
        try {
            BidListDTO bidListDTO = bidService.getBidsList(1, 999);
            String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/");
            XMLUtil.Marshall(bidListDTO, realPath + "/" + BID_XML_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
