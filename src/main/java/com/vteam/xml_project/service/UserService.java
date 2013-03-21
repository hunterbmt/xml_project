        /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.controller.UserSession;
import com.vteam.xml_project.dto.UserDTO;

import com.vteam.xml_project.hibernate.orm.Users;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.util.DateUtil;
import com.vteam.xml_project.util.StringUtil;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
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
public class UserService {

    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DateUtil util;

    @Transactional
    public boolean checkLogin(String email, String password) {
        try {

            String storagepass = StringUtil.createPasswordForDB(password);
            Users dbUser = userDAO.findUserByEmailAndPassword(email, storagepass);
            if (dbUser != null) {
                return true;
            }
        } catch (NoSuchAlgorithmException ex) {
            log.error(ex);
        }
        return false;
    }

    @Transactional
    public boolean upadateUser(String email, String address, String phone, String birthday, String formatDate) {
        try {
            Users dbUser = userDAO.findUserByEmail(email);
            dbUser.setAddress(address);
            dbUser.setPhone(phone);
            if (formatDate == null) {
                formatDate = "MM/dd/yyyy HH:mm:ss";
            }
            Date parseDay = util.parseFromString(birthday, formatDate);
            dbUser.setBirthday(parseDay);
            userDAO.save(dbUser);
            return true;
        } catch (NullPointerException ex) {
            log.error(ex);
        } catch (HibernateException ex) {
            log.error(ex);

        } catch (ParseException ex) {
            log.error(ex);
        } catch (Exception ex) {
            log.error(ex);
        }
        return false;
    }

    @Transactional
    public boolean createNewUser(UserDTO newUser) {
        try {
            String storePassword = StringUtil.createPasswordForDB(newUser.getPassword());
            Users dbUser = new Users(newUser.getEmail(), storePassword, newUser.getFullname(), null, null, null);
            userDAO.save(dbUser);
            return true;
        } catch (NullPointerException ex) {
            log.error(ex);
        } catch (HibernateException ex) {
            log.error(ex);
        } catch (Exception ex) {
            log.error(ex);
        }
        return false;
    }

    @Transactional
    public UserDTO getUserById(Integer id) {
        UserDTO returnUser = new UserDTO();
        try {
            Users dbUser = userDAO.findUserByUuid(id);

            returnUser.setFullname(dbUser.getFullname());
            returnUser.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            returnUser.setStatus("error");
        }
        return returnUser;
    }

    @Transactional
    public UserDTO getUserByEmail(String email) {
        UserDTO returnUser = new UserDTO();
        try {
            Users dbUser = userDAO.findUserByEmail(email);
            returnUser.setEmail(dbUser.getEmail());
            returnUser.setFullname(dbUser.getFullname());
            returnUser.setId(dbUser.getId());
            returnUser.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            returnUser.setStatus("error");
        }
        return returnUser;
    }

    @Transactional
    public boolean checkPassword(String email, String currentPass, String newPassword) throws NoSuchAlgorithmException {
        try {
            Users user = userDAO.findUserByEmail(email);
            String storagepass = StringUtil.createPasswordForDB(currentPass);
            if (user.getPassword().equals(storagepass)) {
                String covertNewPass = StringUtil.createPasswordForDB(newPassword);
                user.setPassword(covertNewPass);
                userDAO.save(user);
                return true;
            }
        } catch (HibernateException ex) {
            log.error(ex);
        }
        return false;
    }
}
