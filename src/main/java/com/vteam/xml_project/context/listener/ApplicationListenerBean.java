/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.context.listener;

import com.vteam.xml_project.dto.CategoryDTO;
import com.vteam.xml_project.dto.CategoryListDTO;
import com.vteam.xml_project.service.CategoryService;
import com.vteam.xml_project.service.ProductService;
import com.vteam.xml_project.util.XMLUtil;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 *
 * @author Lenovo
 */
public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ServletContext servletContext;
    @Autowired
    ProductService productService;
    private static String CATEGORY_XML_FILE_NAME = "category.xml";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        marshallCategory();
    }

    private void marshallCategory() {
        try {
            CategoryListDTO categoryListDTO = categoryService.getCategoryList();
            for (CategoryDTO categoryDTO : categoryListDTO.getCategoryList()) {
                categoryDTO.setProductListDTO(productService.searchProductByCategoryId(categoryDTO.getId(), 1));
            }
            String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/");
            XMLUtil.Marshall(categoryListDTO, realPath + "/" + CATEGORY_XML_FILE_NAME);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }
}
