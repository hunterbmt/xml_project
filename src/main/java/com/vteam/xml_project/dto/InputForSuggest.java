/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.dto;

import java.io.Serializable;

/**
 *
 * @author HunterBMT
 */
public class InputForSuggest implements Serializable{
    private String addr;
    private int budget;
    private int numberOfPeople;

    /**
     * @return the addr
     */
    public String getAddr() {
        return addr;
    }

    /**
     * @param addr the addr to set
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }

    /**
     * @return the budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     * @param budget the budget to set
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * @return the numberOfPeople
     */
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * @param numberOfPeople the numberOfPeople to set
     */
    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
