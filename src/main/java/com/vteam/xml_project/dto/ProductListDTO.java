/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Crick
 */
public class ProductListDTO {
    private List<ProductDTO> productList;

    public ProductListDTO() {
        productList = new ArrayList<ProductDTO>();
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> list) {
        this.productList = list;
    }
    
    
}
