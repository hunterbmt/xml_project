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
public class NinCodeListDTO extends BaseDTO {

    private int numberOfNin;
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
