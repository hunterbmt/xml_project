/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.hibernate.orm.Bids;
import com.vteam.xml_project.hibernate.orm.Product;
import com.vteam.xml_project.hibernate.orm.Users;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
    @Autowired
    private UserDAO userDAO;
    
    private Bids dbBid;
    private static long BID_DURATION = (long) 35.00;

    private List<BidDTO> getTmpList(List<Bids> bidList) {
        BidDTO tmp;
        List<BidDTO> tmpList = new ArrayList<BidDTO>();
        for (Bids bid : bidList) {
            tmp = new BidDTO();
            Integer uuid = bid.getLastUserid();
            
            tmp.setId(bid.getId());
            tmp.setCurrent_price(bid.getCurrentPrice());
            tmp.setLast_edit(bid.getLastEdit());
            tmp.setLast_userid(bid.getLastUserid());
            tmp.setStart_date(bid.getStartDate());
            tmp.setProduct_id(bid.getProduct().getId());
            tmp.setProduct_name(bid.getProduct().getProductName());
            
            if (uuid != null) {
                Users u = userDAO.findUserByUuid(uuid);
                tmp.setLast_username(u.getFullname());
            } else {
                tmp.setLast_username("None");
            }
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
        } catch (HibernateException he) {
            log.error(he);
        } catch (NullPointerException ne) {
            log.error(ne);
        } catch (Exception ex) {
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
        } catch (HibernateException he) {
            log.error(he);
        } catch (NullPointerException ne) {
            log.error(ne);
        } catch (Exception ex) {
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

    private void update_bid(Bids dbBid, Date current_date, Integer uuid) {
        Random rand = new Random();
        double maxPrice = dbBid.getProduct().getMaxPrice();
        double minPrice = dbBid.getProduct().getMinPrice();
        double randomPrice = minPrice + (maxPrice - minPrice) * rand.nextDouble();
        dbBid.setLastEdit(current_date);
        dbBid.setLastUserid(uuid);
        DecimalFormat df = new DecimalFormat("0");
        Double price = Double.valueOf(df.format(randomPrice));
        dbBid.setCurrentPrice(price);
    }

    @Transactional
    public boolean doBid(Integer uuid, int bid_id) {
        Bids dbBid = bidDAO.getBidById(bid_id);
        Date last_edit = dbBid.getLastEdit();
        Date current_date = new Date();

        if (last_edit == null) {
            update_bid(dbBid, current_date, uuid);
            return true;
        } else { // not null
            long seconds = (current_date.getTime() - last_edit.getTime()) / 1000;
            if (seconds > BID_DURATION) {
                update_bid(dbBid, current_date, uuid);
                return true;
            } else {
                return false;
            }
        }
    }

    @Transactional
    public BidListDTO getBidsFromDate(Date parseDate) {
        try {
            BidListDTO list = new BidListDTO();
            List<Bids> bidList = bidDAO.getBidsFromDate(parseDate);
            list.setLists(getTmpList(bidList));
            return list;
        } catch (HibernateException he) {
            log.error(he);
        } catch (NullPointerException ne) {
            log.error(ne);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }
}
