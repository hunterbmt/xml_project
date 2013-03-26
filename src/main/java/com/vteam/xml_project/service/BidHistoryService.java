package com.vteam.xml_project.service;

import com.vteam.xml_project.hibernate.dao.BidHistoryDAO;
import com.vteam.xml_project.hibernate.dao.ProductDAO;
import com.vteam.xml_project.hibernate.dao.UserDAO;
import com.vteam.xml_project.hibernate.orm.BidHistory;
import com.vteam.xml_project.util.DateUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Crick
 */
@Service
public class BidHistoryService {

    private static Logger log = Logger.getLogger(UserService.class.getName());
    @Autowired
    private BidHistoryDAO bhDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DateUtil dateUtil;
    
    private final int NO_RECENT_BIDDER_RECORD = 5;
    
    @Transactional
    public List<String> getRecentBidderList(int bid_id) {
        List<String> rs = new ArrayList<String>();
        List<BidHistory> bhList = bhDAO.getBidHistoryByBidId(bid_id);
        String username = "";
        for (BidHistory bh : bhList) {
            username = bh.getUser().getFullname();
            if (rs.indexOf(username) == -1) {
                rs.add(username);
                if (rs.size() == NO_RECENT_BIDDER_RECORD) break;
            }
        }
        return rs;
    }
}
