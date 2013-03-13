        /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.UserDTO;

//import com.vteam.xml_project.hibernate.dao.UserDAO;
//import com.vteam.xml_project.hibernate.dao.UserProfileDAO;
//import com.vteam.xml_project.hibernate.orm.User;
//import com.vteam.xml_project.hibernate.orm.UserProfile;
import com.vteam.xml_project.hibernate.orm.Users;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.util.DateUtil;
import com.vteam.xml_project.util.StringUtil;
import java.security.NoSuchAlgorithmException;
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

    @Transactional
    public boolean checkLogin(UserDTO user) throws NoSuchAlgorithmException {
        try {

            String storagepass = StringUtil.createPasswordForDB(user.getPassword());
            Users dbUser = userDAO.findUserByEmailAndPassword(user.getEmail(), storagepass);
            if (dbUser != null) {
                return true;
            }
        } catch (HibernateException ex) {
            log.error(ex);
        }
        return false;
    }

    @Transactional
    public boolean upadateUser(String email, String address, String phone, String birthday) {
        try {
            Users dbUser = userDAO.findUserByEmail(email);
            dbUser.setAddress(address);
            DateUtil util=new DateUtil();
            Date parseDay=util.parseFromString(birthday);
            dbUser.setBirthday(parseDay);
            dbUser.setPhone(phone);
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
    public boolean createNewUser(UserDTO newUser) {
        try {
            //UserInfo dbProfile = new UserInfo();
            //dbProfile.setFullname(newUser.getFullname());
            //dbProfile.setAddress("aaaaa");
            //Date dt = new Date();
            //dbProfile.setBirthday(dt);
            //dbProfile.setPhone("12344");
            String storePassword = StringUtil.createPasswordForDB(newUser.getPassword());
            Users dbUser = new Users(newUser.getEmail(), storePassword, newUser.getFullname(), null, null, null);
            //dbUser.setUserInfo(dbProfile);
            userDAO.save(dbUser);
            //userinfoDAO.save(dbProfile);
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
        try {
            Users dbUser = userDAO.findUserByUuid(id);
            UserDTO returnUser = new UserDTO();
            returnUser.setFullname(dbUser.getFullname());
            return returnUser;
        } catch (NullPointerException ex) {
            log.error(ex);
        } catch (HibernateException ex) {
            log.error(ex);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }
}
