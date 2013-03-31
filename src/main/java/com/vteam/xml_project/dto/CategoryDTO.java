/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author phitt60230
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "category")
public class CategoryDTO extends BaseDTO {

    private int id;
    private String name;
    private String description;
    @XmlElement(name = "productList", type = ProductListDTO.class)
    private ProductListDTO productListDTO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductListDTO getProductListDTO() {
        return productListDTO;
    }

    public void setProductListDTO(ProductListDTO productListDTO) {
        this.productListDTO = productListDTO;
    }
}
