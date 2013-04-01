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
@XmlRootElement(name = "ninCodeList")
public class NinCodeListDTO extends BaseDTO {

    private int numberOfNin;
    @XmlElement(name = "ninCode", type = NinCodeDTO.class)
    private List<NinCodeDTO> ninList;

    public NinCodeListDTO() {
        this.ninList = new ArrayList<NinCodeDTO>();
    }

    public List<NinCodeDTO> getNinList() {
        return ninList;
    }

    public void setNinList(List<NinCodeDTO> ninList) {
        this.ninList = ninList;
    }

    public void setNumberOfNin(int numberOfNin) {
        this.numberOfNin = numberOfNin;
    }

    public int getNumberOfNin() {
        return numberOfNin;
    }
}
