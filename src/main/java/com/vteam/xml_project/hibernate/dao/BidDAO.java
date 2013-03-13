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
    
    public  List<Bids> getBidsList(int page, int pageSize) throws HibernateException {
        Query query = this
                .sessionFactory
                .getCurrentSession()
                .createQuery("FROM Bids");
       
        query = query.setFirstResult(pageSize * (page - 1));
		query.setMaxResults(pageSize);
		return query.list();    
    }  
    
    public List<Bids> getBidsByStartDate(Date date) throws HibernateException {        
        String sql = "From Bids where start_date >= ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setDate(0, date);        
        return query.list();
    }

    public List<Bids> getBidsFromDate(Date parseDate) {
        String sql = "From Bids where start_date >= ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setDate(0, parseDate);        
        return query.list();
    }
    
    public List<Bids> getBidsFromDateToDate(Date fromDate, Date toDate) {
        String sql = "From Bids where start_date >= ? and start_date <= ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setDate(0, fromDate);        
        query.setDate(1, toDate);
        return query.list();
    }
    
}
