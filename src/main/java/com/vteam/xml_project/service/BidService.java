/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.orm.Bid;
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
public class BidService {

    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
     private BidDAO bidDAO;
     private Bid dbBid;
//    @Autowired
//    private UserProfileDAO userProfileDAO;

    @Transactional
    public BidDTO getBidByID(Integer _id) {
        try {
            dbBid = bidDAO.getBidById(_id);
            return dbBid; 
        } catch (HibernateException ex) {
           log.error(ex);
       }
        return null;
    }
    @Transactional
    public Bid getBidsByStartDate(Integer _id) {
        try {
            BidListDTO lists = new BidListDTO();
            List<Bid> bidList = bidSer
            
            
        } catch (HibernateException ex) {
           log.error(ex);
       }
        return null;
    }
}
