/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.orm.Bids;
import com.vteam.xml_project.hibernate.orm.Product;
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
    @Autowired
    private ProductDAO productDAO;
    private Bids dbBid;

    private List<BidDTO> getTmpList(List<Bids> bidList) {
        BidDTO tmp;
        List<BidDTO> tmpList = new ArrayList<BidDTO>();
        for (Bids bid : bidList) {
            tmp = new BidDTO();
            tmp.setId(bid.getId());
            tmp.setCurrent_price(bid.getCurrentPrice());
            tmp.setLast_userid(bid.getLastUserid());
            tmp.setStart_date(bid.getStartDate());
            tmpList.add(tmp);
        }
        return tmpList;
    }

    @Transactional
    public BidDTO getBidByID(Integer _id) {
        try {
            dbBid = bidDAO.getBidById(_id);

            BidDTO bid = new BidDTO();
            bid.setId(dbBid.getId());
            bid.setProduct_id(dbBid.getProduct().getId());
            bid.setCurrent_price(dbBid.getCurrentPrice());
            bid.setStart_date(dbBid.getStartDate());
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
            BidListDTO list = new BidListDTO();
            List<Bids> bidList = bidDAO.getBidsByStartDate(_date);            
            list.setLists(getTmpList(bidList));
            return list;
        } catch (HibernateException ex) {
            log.error(ex);
        }
        return null;
    }

    @Transactional
    public BidListDTO getBidsList(int page, int page_size) {
        try {
            BidListDTO list = new BidListDTO();
            List<Bids> bidList = bidDAO.getBidsList(page, page_size);
            list.setLists(getTmpList(bidList));
            return list;
        } catch (HibernateException ex) {
            log.error(ex);
        }
        return null;
    }

    @Transactional
    public boolean createNewBid(BidDTO newBid) {
        boolean success = true;
        try {
            Product product = productDAO.getProductById(newBid.getProduct_id());
            dbBid = new Bids(
                    product,
                    newBid.getStart_date());
            bidDAO.save(dbBid);
        } catch (NullPointerException ex) {
            log.error(ex);
            success = false;
        } catch (HibernateException ex) {
            log.error(ex);
            success = false;
        } catch (Exception ex) {
            log.error(ex);
            success = false;
        }
        return success;
    }
}
