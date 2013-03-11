/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import com.vteam.xml_project.hibernate.orm.Bids;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TH31012013
 */
@Repository
public class BidDAO extends BaseDAO{
    public BidDAO() {
    }
    
    public Bids getBidById(Integer id) throws HibernateException {
        Bids returnObj ;
        String sql = "From Bids where id = ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setInteger(0, id);
        returnObj =  (Bids) query.uniqueResult();
        return returnObj;
    }
    
    public List<Bids> getBidsByStartDate(Date date) throws HibernateException {        
        String sql = "From Bids where start_date = ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setDate(0, date);        
        return query.list();
    }
    
}
