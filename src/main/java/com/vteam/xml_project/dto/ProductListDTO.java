/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Crick
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "productList")
public class ProductListDTO extends BaseDTO {

    private int numberOfProduct;
    @XmlElement(name = "product", type = ProductDTO.class)
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

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }
}
