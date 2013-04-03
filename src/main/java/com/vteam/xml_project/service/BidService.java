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
import com.vteam.xml_project.hibernate.dao.OrderHistoryDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.hibernate.orm.BidHistory;
import com.vteam.xml_project.hibernate.orm.Bids;
import com.vteam.xml_project.hibernate.orm.OrderHistory;
import com.vteam.xml_project.hibernate.orm.Product;
import com.vteam.xml_project.hibernate.orm.Users;
import com.vteam.xml_project.util.DateUtil;
import com.vteam.xml_project.util.XMLUtil;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;

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
    private OrderHistoryDAO orderHistoryDAO;
    @Autowired
    private DateUtil dateUtil;
    @Autowired
    ServletContext servletContext;
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

//    private BidListDTO convertCacheToBidListDTO(String filePath) throws JAXBException {
//        return XMLUtil.UnMarshall(BidListDTO.class, filePath);
//    }
//
//    private void converBidListDTOToCache(BidListDTO bidList, String filePath) throws JAXBException {
//        XMLUtil.Marshall(bidList, filePath);
//    }

    private BidListDTO convertFromNodeToBidListDTO(Node node) throws JAXBException {
        BidListDTO result = XMLUtil.UnMarshall(BidListDTO.class, node);
        return result;
    }

    private BidDTO findBidDTOInBidsXML(String realPath, int bidId) throws XMLStreamException, JAXBException {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        StreamSource xml = new StreamSource(realPath + "bids.xml");
        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
        XMLStreamReader xsr1 = xif.createXMLStreamReader(xml);
        xsr.nextTag();
        xsr1.nextTag();
        boolean found = false;
        while (xsr.hasNext()) {
            xsr.next();
            xsr1.next();
            if (xsr.getEventType() == XMLStreamReader.START_ELEMENT) {
                if (xsr.getLocalName().equals("bid")) {
                    xsr.nextTag();
                    if (xsr.getLocalName().equals("id")) {
                        xsr.next();
                        int id = Integer.parseInt(xsr.getText());
                        if (id == bidId) {
                            found = true;
                            break;
                        } else {
                            xsr1.next();
                            xsr1.nextTag();
                            xsr1.next();
                        }
                    }
                }
            }
            if (found) {
                break;
            }

        }
        if (found) {
            return XMLUtil.UnMarshall(BidDTO.class, xsr1);
        }
        return null;
    }

    @Transactional
    public BidDTO getBidByID(Integer _id) {
        BidDTO bid = new BidDTO();
        try {
            String realPath = servletContext.getRealPath("WEB-INF/views/resources/xml/") + "/";
            BidDTO returnBidDTO = findBidDTOInBidsXML(realPath, _id);
            returnBidDTO.setStatus("success");
            return returnBidDTO;

        } catch (HibernateException ex) {
            log.error(ex);
            bid.setStatus("success");
            bid.setMsg("Have some errors. Try again");
        } catch (XMLStreamException ex) {
            java.util.logging.Logger.getLogger(BidService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(BidService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bid;
    }

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
        BidHistory bh = new BidHistory(user, dbBid, dbBid.getCurrentPrice(), dbBid.getLastEdit());
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
            createOrderHistory(user,dbBid.getProduct(),dbBid.getCurrentPrice());
        }
        return true;
    }
    private void createOrderHistory(Users user, Product product,double amount){
        OrderHistory orderHistory = new OrderHistory(user, product, dateUtil.getCurrentDate(), user.getAddress(), (int) amount);
        orderHistoryDAO.save(orderHistory);
    }

    @Transactional
    public BidListDTO getUpcommingBid(int page, int pageSize) {
        Date currentDate = new Date();
        BidListDTO list = new BidListDTO();
        try {
            List<Bids> bidList = bidDAO.getBidsList(page, pageSize);
            List<Bids> filteredList = new ArrayList<Bids>();
            for (Bids b : bidList) {
                if (b.getStatus() != Bids.Status.COMPLETED) {
                    
                    if (b.getStartDate().getTime() - currentDate.getTime() > 0) {
                       // if (b.getStartDate().getHours() > currentDate.getHours())
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
        Date currentDate = new Date();
        BidListDTO list = new BidListDTO();
        try {
            List<Bids> bidList = bidDAO.getBidsList(page, pageSize);
            List<Bids> filteredList = new ArrayList<Bids>();
            for (Bids b : bidList) {
                if (b.getStatus() != Bids.Status.COMPLETED) {
                    if ((b.getStartDate().getTime() - currentDate.getTime() <= 0)
                            && (b.getEndDate().getTime() - currentDate.getTime() > 0)) {
//                        if ((b.getStartDate().getHours() < currentDate.getHours()) && 
//                                (b.getEndDate().getHours() > currentDate.getHours()))
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
