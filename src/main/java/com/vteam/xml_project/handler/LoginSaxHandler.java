/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.handler;

import com.vteam.xml_project.util.DateUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author TH11032013
 */
public class LoginSaxHandler extends DefaultHandler {

    private String username;
    private String password;
    private boolean bUser;
    private boolean bStatus;
    private boolean bEmail;
    private boolean bPass;
    private String tagName;
    private boolean found;
    private String status = "";
    private String address = "";
    private String phone = "";
    private String fullname = "";
    private String balance = "";
    private String id = "";
    private String birthday = "";

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullname() {
        return fullname;
    }

    public String getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }

    public String getBirthday() {
        return birthday;
    }

    public LoginSaxHandler() {
        bUser = false;
        bEmail = false;
        bPass = false;
        bStatus = false;
        found = false;
        tagName = "";
        status = "";
        birthday = "";
        address = "";
        phone = "";
        fullname = "";
        balance = "";
        id = "";
    }

    public LoginSaxHandler(String name, String pass) {
        this.username = name;
        this.password = pass;
        bUser = false;
        bEmail = false;
        bStatus = false;
        bPass = false;
        found = false;
        status = "";
        birthday = "";
        address = "";
        phone = "";
        fullname = "";
        balance = "";
        id = "";
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if (!found) {
            tagName = qName;
            if (qName.equals("status")) {
                bStatus = true;
            }
            if (qName.equals("user")) {

                bUser = true;

            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        tagName = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!found) {
            if (bStatus) {
                status = new String(ch, start, length).trim();
                bStatus = false;
            }
            if (bUser) {
                if (tagName.equals("email")) {
                    String email = new String(ch, start, length).trim();
                    if (email.equals(username)) {
                        bEmail = true;
                    }
                } else if (tagName.equals("password")) {
                    String pass = new String(ch, start, length).trim();
                    if (pass.equals(password)) {
                        bPass = true;
                    }
                }
            }
            if (bPass) {
                if (tagName.equals("fullname")) {
                    String full = new String(ch, start, length);
                    fullname = full.trim();
                } else if (tagName.equals("birthday")) {
                    String date = new String(ch, start, length);
                    birthday = date.trim();
                } else if (tagName.equals("address")) {
                    String add = new String(ch, start, length);
                    address = add.trim();
                } else if (tagName.equals("balance")) {
                    String bal = new String(ch, start, length);
                    balance = bal.trim();
                } else if (tagName.equals("id")) {
                    String u_id = new String(ch, start, length);
                    id = u_id.trim();
                    found = true;
                }

            }
        } else {
            bPass = false;
            bEmail = false;
            bUser = false;
        }
    }

    public String getStatus() {
        return status;
    }

    public boolean isFound() {
        return found;
    }
}
