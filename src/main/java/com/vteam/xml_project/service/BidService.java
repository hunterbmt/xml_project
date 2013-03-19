/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.hibernate.orm.Bids;
import com.vteam.xml_project.hibernate.orm.Product;
import com.vteam.xml_project.hibernate.orm.UserPayment;
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
            tmp.setEnd_date(bid.getEndDate());

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
            Integer uuid = dbBid.getLastUserid();
            bid.setId(dbBid.getId());
            bid.setProduct_name(dbBid.getProduct().getProductName());
            bid.setProduct_id(dbBid.getProduct().getId());
            bid.setCurrent_price(dbBid.getCurrentPrice());
            bid.setStart_date(dbBid.getStartDate());
            bid.setLast_userid(dbBid.getLastUserid());
            bid.setEnd_date(dbBid.getEndDate());
            bid.setStatus(dbBid.getStatus().toString());
            bid.setLast_edit(dbBid.getLastEdit());
            if (uuid != null) {
                Users u = userDAO.findUserByUuid(uuid);
                bid.setLast_username(u.getFullname());
            } else {
                bid.setLast_username("None");
            }

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

    private boolean update_balance(Users u, int cost) {
        int currBalance = u.getBalance();
        int left = currBalance - cost;
        if (left < 0) {
            return false;
        }
        u.setBalance(left);
        userDAO.save(u);
        return true;
    }

    @Transactional
    public boolean doBid(UserDTO u, int bid_id) {
        Bids dbBid = bidDAO.getBidById(bid_id);
        Date last_edit = dbBid.getLastEdit();
        Date current_date = new Date();
        Users user = userDAO.findUserByEmail(u.getEmail());

        if (last_edit == null) {
            if (update_balance(user, dbBid.getCost())) {
                update_bid(dbBid, current_date, user.getId());
                return true;
            } else {
                return false;
            }
        } else { // not null
            long seconds = (current_date.getTime() - last_edit.getTime()) / 1000;
            if (seconds > BID_DURATION) {
                if (update_balance(user, dbBid.getCost())) {
                    update_bid(dbBid, current_date, user.getId());
                    return true;
                } else {
                    return false;
                }
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

    @Transactional
    public BidListDTO getOngoingBids(Date curDate) {
        try {
            BidListDTO list = new BidListDTO();
            List<Bids> bidList = bidDAO.getOngoingBids(curDate);
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
    public BidListDTO getCompletedBids() {
        try {
            BidListDTO list = new BidListDTO();
            List<Bids> bidList = bidDAO.getCompleteBids();
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
