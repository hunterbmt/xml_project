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
    
    public List<Bids> getBidsByStartDate(int page,int pageSize,Date date) throws HibernateException {        
        String sql = "From Bids where start_date = ?  and status = 0";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setDate(0, date);        
        query = query.setFirstResult(pageSize * (page - 1));
		query.setMaxResults(pageSize);
        return query.list();
    }

    public List<Bids> getBidsFromDate(int page,int pageSize,Date parseDate) {
        String sql = "From Bids where start_date > ? and status = :status";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setDate(0, parseDate);    
        query.setParameter("status", Bids.Status.UNCOMPLETED);
        query = query.setFirstResult(pageSize * (page - 1));
		query.setMaxResults(pageSize);
        return query.list();
    }
    public int getNumberOfUpcomingBid(Date currentdate){
        String sql = "Select count(bids) From Bids bids where bids.start_date > ? and bids.status =:status";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setDate(0, currentdate);
        query.setParameter("status", Bids.Status.UNCOMPLETED);
        return ((Long)query.uniqueResult()).intValue();
    }
    
    public List<Bids> getCompleteBids(int page,int pageSize) {
        String sql = "From Bids where status = :s";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);           
        query.setParameter("s", Bids.Status.COMPLETED);
        query = query.setFirstResult(pageSize * (page - 1));
		query.setMaxResults(pageSize);
        return query.list();
    }
    public int getNumberOfCompleteBid(){
        String sql = "Select count(bids) From Bids bids where bids.status =:status";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("status", Bids.Status.COMPLETED);
        return ((Long)query.uniqueResult()).intValue();
    }
    

    public List<Bids> getOngoingBids(int page,int pageSize,Date curDate) {
        String sql = "From Bids where (start_date <= ?) and (end_date > ?) and status =:status";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setDate(0, curDate);        
        query.setDate(1, curDate);
        query.setParameter("status", Bids.Status.UNCOMPLETED);
        query = query.setFirstResult(pageSize * (page - 1));
		query.setMaxResults(pageSize);
        return query.list();
    }
    public int getNumberOfOngoingBid(Date currentdate){
        String sql = "Select count(bids) From Bids bids where (bids.start_date <= ?) and (bids.end_date > ?) and bids.status =:status";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setDate(0, currentdate);
        query.setParameter("status", Bids.Status.UNCOMPLETED);
        return ((Long)query.uniqueResult()).intValue();
    }

    public Bids getBidByProductId(int p_id) {
        Bids returnObj ;
        String sql = "From Bids where product.id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("id", p_id);
        returnObj =  (Bids) query.uniqueResult();
        return returnObj;
    }
    
}
