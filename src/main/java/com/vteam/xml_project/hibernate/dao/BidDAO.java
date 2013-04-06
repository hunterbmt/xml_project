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
 * @author Crick
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
    

    public List<Bids> getBidsFromDate(int page,int pageSize,Date parseDate) {
        String sql = "From Bids where startDate > ? and status = :status";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setDate(0, parseDate);    
        query.setParameter("status", Bids.Status.UNCOMPLETED);
        query = query.setFirstResult(pageSize * (page - 1));
		query.setMaxResults(pageSize);
        return query.list();
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
