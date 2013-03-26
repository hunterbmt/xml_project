/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.service;

import com.vteam.xml_project.dto.BidDTO;
import com.vteam.xml_project.dto.BidListDTO;
import com.vteam.xml_project.dto.UserDTO;
import com.vteam.xml_project.hibernate.dao.BidDAO;
import com.vteam.xml_project.hibernate.dao.BidHistoryDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.hibernate.orm.BidHistory;
import com.vteam.xml_project.hibernate.orm.Bids;
import com.vteam.xml_project.hibernate.orm.Product;
import com.vteam.xml_project.hibernate.orm.Users;
import com.vteam.xml_project.util.DateUtil;
import java.text.DecimalFormat;
import java.text.ParseException;
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
    private BidHistoryDAO bhDAO;
    
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DateUtil dateUtil;
    private static long BID_DURATION = (long) 25.00;

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
            tmp.setCost(bid.getCost());
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
        BidDTO bid = new BidDTO();
        try {
            Bids dbBid = bidDAO.getBidById(_id);


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
            bid.setCost(dbBid.getCost());
            if (uuid != null) {
                Users u = userDAO.findUserByUuid(uuid);
                bid.setLast_username(u.getFullname());
            } else {
                bid.setLast_username("None");
            }
            bid.setStatus("success");

        } catch (HibernateException ex) {
            log.error(ex);
            bid.setStatus("success");
            bid.setMsg("Have some errors. Try again");
        }
        return bid;
    }

//    @Transactional
//    public BidListDTO getBidsByStartDate(int page,int pageSize,String dateString, String formatStr) {
//        BidListDTO list = new BidListDTO();
//        try {
//            Date startDate = dateUtil.parseFromString(dateString, formatStr);
//            List<Bids> bidList = bidDAO.getBidsByStartDate(page,pageSize,startDate);
//            list.setBidList(getTmpList(bidList));
//            list.setStatus("success");
//        } catch (HibernateException he) {
//            log.error(he);
//            list.setStatus("error");
//            list.setMsg("Have some errors. Try again");
//        } catch(ParseException ex){
//            log.error(ex);
//            list.setStatus("error");
//            list.setMsg("Wrong date time format");
//        }
//        return list;
//    }
    @Transactional
    public BidListDTO getBidsList(int page, int page_size) {
        BidListDTO list = new BidListDTO();
        try {
            List<Bids> bidList = bidDAO.getBidsList(page, page_size);
            list.setBidList(getTmpList(bidList));
            list.setStatus("success");
        } catch (HibernateException he) {
            log.error(he);
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;
    }

    @Transactional
    public BidDTO createNewBid(int productID, String startDateStr, String formatStr) {
        BidDTO bidDTO = new BidDTO();
        try {
            Product product = productDAO.getProductById(productID);
            Date startDate = dateUtil.parseFromString(startDateStr, formatStr);
            Bids dbBid = new Bids(
                    product, startDate, null, null);
            dbBid.setStatus(Bids.Status.UNCOMPLETED);
            bidDAO.save(dbBid);
            bidDTO.setStatus("success");
        } catch (HibernateException ex) {
            log.error(ex);
            bidDTO.setStatus("error");
            bidDTO.setMsg("Have some errors. Try again");
        } catch (ParseException ex) {
            log.error(ex);
            bidDTO.setStatus("error");
            bidDTO.setMsg("Wrong date time formate");
        }
        return bidDTO;
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

    private void update_bought_bid(Bids dbBid, Date current_date, Integer uuid, double price) {
        DecimalFormat df = new DecimalFormat("0");
        Double _price = Double.valueOf(df.format(price));
        dbBid.setLastEdit(current_date);
        dbBid.setLastUserid(uuid);
        dbBid.setCurrentPrice(_price);
        dbBid.setStatus(Bids.Status.COMPLETED);
        
        Product product = productDAO.getProductById(dbBid.getProduct().getId());
        product.setBidId(dbBid.getId());
        product.setStatus(Product.Status.SOLD);
        productDAO.save(product);
    }

    private void updateBidsHistory(Users user, Bids dbBid) {
        BidHistory bh= new BidHistory(user, dbBid, dbBid.getCurrentPrice(), dbBid.getLastEdit());
        bhDAO.save(bh);
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
    public double doBid(UserDTO u, int bid_id) {
        /*
         * Error code:
         * -113 : unefficient balance
         * -100 : on hold by the other
         */
        Bids dbBid = bidDAO.getBidById(bid_id);
        Date last_edit = dbBid.getLastEdit();
        Date current_date = new Date();
        Users user = userDAO.findUserByEmail(u.getEmail());

        if (last_edit == null) {
            if (update_balance(user, dbBid.getCost())) {
                update_bid(dbBid, current_date, user.getId());
                updateBidsHistory(user, dbBid);
                return dbBid.getCurrentPrice();
            } else {
                return -113;
            }
        } else { // not null
            long seconds = (current_date.getTime() - last_edit.getTime()) / 1000;
            if (seconds > BID_DURATION) {
                if (update_balance(user, dbBid.getCost())) {
                    update_bid(dbBid, current_date, user.getId());
                    updateBidsHistory(user, dbBid);
                    return dbBid.getCurrentPrice();
                } else {
                    return -113;
                }
            } else {
                return -100;
            }
        }
    }

    @Transactional
    public boolean doBuy(UserDTO u, int bid_id) {

        Bids dbBid = bidDAO.getBidById(bid_id);
        Date last_edit = dbBid.getLastEdit();
        Date current_date = new Date();
        Users user = userDAO.findUserByEmail(u.getEmail());

        if (last_edit == null) {
            return false;
        } else { // not null
            update_bought_bid(dbBid, current_date, user.getId(), dbBid.getCurrentPrice());
            updateBidsHistory(user, dbBid);
        }
        return true;
    }

    @Transactional
    public BidListDTO getUpcommingBid(int page, int pageSize) {
        Date currentDate = dateUtil.getCurrentDate();
        BidListDTO list = new BidListDTO();
        try {
            List<Bids> bidList = bidDAO.getBidsList(page, pageSize);
            List<Bids> filteredList = new ArrayList<Bids>();
            for (Bids b : bidList) {
                if (b.getStatus() != Bids.Status.COMPLETED) {
                    if (b.getStartDate().getTime() - currentDate.getTime() > 0) {
                        filteredList.add(b);
                    }
                }
            }
            list.setBidList(getTmpList(filteredList));
            list.setNumberOfBid(filteredList.size());
            list.setStatus("success");
        } catch (HibernateException he) {
            log.error(he);
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;
    }

    @Transactional
    public BidListDTO getOngoingBids(int page, int pageSize) {
        Date currentDate = dateUtil.getCurrentDate();
        BidListDTO list = new BidListDTO();
        try {
            List<Bids> bidList = bidDAO.getBidsList(page, pageSize);
            List<Bids> filteredList = new ArrayList<Bids>();
            for (Bids b : bidList) {
                if (b.getStatus() != Bids.Status.COMPLETED) {
                    if ((b.getStartDate().getTime() - currentDate.getTime() <= 0)
                            && (b.getEndDate().getTime() - currentDate.getTime() > 0)) {
                        filteredList.add(b);
                    }
                }
            }
            list.setBidList(getTmpList(filteredList));
            list.setNumberOfBid(filteredList.size());
            list.setStatus("success");
        } catch (HibernateException he) {
            log.error(he);
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;
    }

    @Transactional
    public BidListDTO getCompletedBids(int page, int pageSize) {
        BidListDTO list = new BidListDTO();
        try {

            List<Bids> bidList = bidDAO.getBidsList(page, pageSize);
            List<Bids> filteredList = new ArrayList<Bids>();
            for (Bids b : bidList) {
                if (b.getStatus() == Bids.Status.COMPLETED) {
                    filteredList.add(b);
                }
            }
            list.setBidList(getTmpList(filteredList));
            list.setNumberOfBid(filteredList.size());
            list.setStatus("success");
        } catch (HibernateException he) {
            log.error(he);
            list.setStatus("error");
            list.setMsg("Have some errors. Try again");
        }
        return list;
    }
}
