/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.orm.Bids;
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
public class BidService {

    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
    private BidDAO bidDAO;
    
    private Bids dbBid;

    @Transactional
    public BidDTO getBidByID(Integer _id) {
        try {
            dbBid = bidDAO.getBidById(_id);
            
            BidDTO bid = new BidDTO(); 
            bid.setId(dbBid.getId());
            bid.setCurrent_price(dbBid.getCurrentPrice());
            bid.setLast_userid(dbBid.getLastUserid());
            
            return bid;
        } catch (HibernateException ex) {
           log.error(ex);
       }
        return null;
    }
    @Transactional
    public BidListDTO getBidsByStartDate(Date _date) {
        try {
            BidListDTO lists = new BidListDTO();
            List<Bids> bidList = bidDAO.getBidsByStartDate(_date);
            BidDTO tmp;
            List<BidDTO> tmpList = new ArrayList<BidDTO>();
           for (Bids bid : bidList) {
               tmp = new BidDTO();
               tmp.setId(bid.getId());
               tmp.setCurrent_price(bid.getCurrentPrice());
               tmp.setLast_userid(bid.getLastUserid());
               tmpList.add(tmp);
           }
           lists.setLists(tmpList);
           return lists;
            
            
        } catch (HibernateException ex) {
           log.error(ex);
       }
        return null;
    }
}
