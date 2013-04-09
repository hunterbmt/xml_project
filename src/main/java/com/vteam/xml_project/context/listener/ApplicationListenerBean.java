/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.context.listener;

import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.dto.CategoryDTO;
import com.vteam.xml_project.dto.CategoryListDTO;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.dto.UserListDTO;
import com.vteam.xml_project.service.BidService;
import com.vteam.xml_project.service.CategoryService;
import com.vteam.xml_project.service.OrderHistoryService;
import com.vteam.xml_project.service.ProductService;
import com.vteam.xml_project.service.UserService;
import com.vteam.xml_project.util.XMLUtil;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author Lenovo
 */
public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {

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
    private static String PRODUCT_XML_FILE_NAME = "product.xml";
    private static String USER_XML_FILE_NAME = "user.xml";
    private static String BID_XML_FILE_NAME = "bids.xml";
    private static String ORDER_XML_FILE_NAME = "order.xml";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        marshallCategory();
        marshallUser();
        marshallBids();
    }
    //@Scheduled(fixedRate=6000)
    @Scheduled(cron = "* * 1 * * ?")
    private void marshallCategory() {
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

    private void marshallUser() {
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

    private void marshallBids(){
        try {
            BidListDTO bidListDTO = bidService.getBidsList(1, 999);
            String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/");
            XMLUtil.Marshall(bidListDTO, realPath + "/" + BID_XML_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
