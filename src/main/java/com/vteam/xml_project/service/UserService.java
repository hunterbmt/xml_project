        /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.dto.UserProfileDTO;
//import com.vteam.xml_project.hibernate.dao.UserDAO;
//import com.vteam.xml_project.hibernate.dao.UserProfileDAO;
//import com.vteam.xml_project.hibernate.orm.User;
//import com.vteam.xml_project.hibernate.orm.UserProfile;
import com.vteam.xml_project.hibernate.orm.User;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.util.StringUtil;
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
     private User dbUser;
//    @Autowired
//    private UserProfileDAO userProfileDAO;

    @Transactional
    public boolean checkLogin(String email, String password) {
        try {
            dbUser = userDAO.findUserByEmailAndPassword(email, password);
            if (dbUser != null) {
               return true;
           }
        } catch (HibernateException ex) {
           log.error(ex);
       }
        return false;
    }
    @Transactional
    public boolean createNewUser(UserDTO newUser ,UserProfileDTO newProfile) {
//        try {
//            UserProfile dbProfile = new UserProfile();
//            dbProfile.setEmail(newProfile.getEmail());
//            dbProfile.setName(newProfile.getName());
//            dbProfile.setPhoneNumber(newProfile.getPhoneNumber());
//            String storePassword = StringUtil.createPasswordForDB(newUser.getPassword());
//            User dbUser = new User(newUser.getUuid(), dbProfile, storePassword, User.Status.UNACTIVED);
//            userDAO.save(dbUser);
//            return true;
//        } catch (NullPointerException ex) {
//            log.error(ex);
//        } catch (HibernateException ex) {
//            log.error(ex);
//        }catch (Exception ex){
//            log.error(ex);
//        }
        return false;
    }
}
