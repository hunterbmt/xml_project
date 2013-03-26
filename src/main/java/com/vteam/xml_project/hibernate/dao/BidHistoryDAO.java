package com.vteam.xml_project.hibernate.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import com.vteam.xml_project.hibernate.orm.BidHistory;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Crick
 */
@Repository
public class BidHistoryDAO extends BaseDAO{
    public BidHistoryDAO() {
    }
    
    public List<BidHistory> getBidHistoryByBidId(int id) throws HibernateException {
        List<BidHistory> returnObj ;
        String sql = "From BidHistory where bid.id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("id", id);
        returnObj =  query.list();
        return returnObj;
    }
}
