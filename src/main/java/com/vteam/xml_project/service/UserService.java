        /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.NinCodeDTO;
import com.vteam.xml_project.dto.UserPaymentDTO;
import com.vteam.xml_project.dto.UserPaymentListDTO;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.hibernate.dao.CardCodeDAO;

import com.vteam.xml_project.hibernate.orm.Users;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.hibernate.dao.UserPaymentDAO;
import com.vteam.xml_project.hibernate.orm.CardCode;
import com.vteam.xml_project.hibernate.orm.OrderHistory;
import com.vteam.xml_project.hibernate.orm.UserPayment;
import com.vteam.xml_project.util.DateUtil;
import com.vteam.xml_project.util.StringUtil;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private CardCodeDAO cardCodeDAO;
    @Autowired
    private UserPaymentDAO userPaymentDAO;
    @Autowired
    private DateUtil util;
    
    @Transactional
    public UserDTO checkLogin(String email, String password) {
        UserDTO userDTO = new UserDTO();
        try {
            String storagepass = StringUtil.createPasswordForDB(password);
            Users dbUser = userDAO.findUserByEmailAndPassword(email, storagepass);
            if (dbUser != null) {
                userDTO.setId(dbUser.getId());
                userDTO.setEmail(dbUser.getEmail());
                userDTO.setFullname(dbUser.getFullname());
                userDTO.setPhone(dbUser.getPhone());
                userDTO.setAddress(dbUser.getAddress());
                userDTO.setBalance(dbUser.getBalance());
                userDTO.setBirthday(dbUser.getBirthday());
                userDTO.setStatus("success");
            }
            else {
                userDTO.setStatus("error");
            }
        } catch (NoSuchAlgorithmException ex) {
            log.error(ex.getStackTrace());
            userDTO.setStatus("error");
        }
        return userDTO;
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
            log.error(ex.getStackTrace());
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            
        } catch (ParseException ex) {
            log.error(ex.getStackTrace());
        } catch (Exception ex) {
            log.error(ex.getStackTrace());
        }
        return false;
    }
    
    @Transactional
    public boolean createNewUser(UserDTO newUser) {
        try {
            String storePassword = StringUtil.createPasswordForDB(newUser.getPassword());
            Users dbUser = new Users(newUser.getEmail(), storePassword, newUser.getFullname(), null, null, null);
            dbUser.setBalance(5);
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
            returnUser.setAddress(dbUser.getAddress());
            returnUser.setPhone(dbUser.getPhone());
            returnUser.setBirthday(dbUser.getBirthday());
            returnUser.setBalance(dbUser.getBalance());
            returnUser.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            returnUser.setStatus("error");
        }
        return returnUser;
    }
    
    @Transactional
    public boolean checkPassword(String email, String currentPass, String newPassword) {
        try {
            Users user = userDAO.findUserByEmail(email);
            String storagepass = StringUtil.createPasswordForDB(currentPass);
            if (user.getPassword().equals(storagepass)) {
                String covertNewPass = StringUtil.createPasswordForDB(newPassword);
                user.setPassword(covertNewPass);
                userDAO.save(user);
                return true;
            }
        } catch (NoSuchAlgorithmException nx) {
            log.error(nx.getStackTrace());
        }
        return false;
    }
    
    @Transactional
    public NinCodeDTO inputPayment(String email, String code) {
        NinCodeDTO ninCode = new NinCodeDTO();
        try {
            Users currentUser = userDAO.findUserByEmail(email);
            if (currentUser != null) {
                CardCode cardCode = cardCodeDAO.getCardCodeByCode(code);
                if (cardCode != null) {
                    currentUser.setBalance(currentUser.getBalance() + cardCode.getAmount());
                    userDAO.save(currentUser);
                    cardCode.setUsedDay(util.getCurrentDate());
                    cardCode.setUser(currentUser);
                    cardCodeDAO.save(cardCode);
                    UserPayment userPayment = new UserPayment();
                    userPayment.setUser(currentUser);
                    userPayment.setAmount(cardCode.getAmount());
                    userPayment.setCardCode(cardCode.getCode());
                    userPayment.setPaymentDay(util.getCurrentDate());
                    userPaymentDAO.save(userPayment);
                    ninCode.setStatus("success");
                } else {
                    ninCode.setStatus("error");
                    ninCode.setMsg("Sai mã hoặc mã đã được sử dụng");
                }
            } else {
                ninCode.setStatus("error");
                ninCode.setMsg("Không tìm thấy người dùng");
            }
        } catch (HibernateException ex) {
            log.error(ex.getStackTrace());
            ninCode.setStatus("error");
            ninCode.setMsg("Có lỗi xẩy ra");
        }
        return ninCode;
    }
    @Transactional
    public UserPaymentListDTO getListByPaymentID(int id){
        UserPaymentListDTO listOrders=new UserPaymentListDTO();
        try{
            Users user = new Users();
            List<UserPayment> dbPayments = userPaymentDAO.getPaymentHistorysList(id);
            
            
            UserPaymentDTO o;

            List<UserPaymentDTO> tmpList = new ArrayList<UserPaymentDTO>();
            for (UserPayment d : dbPayments) {

                o = new UserPaymentDTO();
                o.setCard_code(d.getCardCode());
                o.setUser_id(d.getUser().getId());
                o.setAmmount(d.getAmount());
                o.setPayment_date(d.getPaymentDay());

               tmpList.add(o);
            }
            listOrders.setPaymentList(tmpList);
            listOrders.setStatus("success");
        }catch (HibernateException ex){
            log.error(ex.getStackTrace());
           listOrders.setStatus("error");
           listOrders.setMsg("Have some errors. Try again");
        }
        return  listOrders;
    }
}
