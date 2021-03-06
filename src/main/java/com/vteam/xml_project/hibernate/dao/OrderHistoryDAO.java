/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;

import com.vteam.xml_project.hibernate.orm.OrderHistory;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TH11032013
 */
@Repository
public class OrderHistoryDAO extends BaseDAO {

    public OrderHistory getOrderById(int order_id) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM OrderHistory where id = ?");
        query.setParameter(0, order_id);
        return (OrderHistory) query.uniqueResult();
    }

    public List<OrderHistory> getOrderHistorysListByUserID(int id) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM OrderHistory where user_id = :uid");
        query.setParameter("uid", id);
        return query.list();
    }

    public List<OrderHistory> getOrderHistorysList() throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM OrderHistory");
        return query.list();
    }

    public List<OrderHistory> getOrderHistorysList(int page, int pageSize) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM OrderHistory");
        query = query.setFirstResult(pageSize * (page - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }

    public int getNumberOfOrderHistory() throws HibernateException {
        return ((Long) this.getSessionFactory()
                .getCurrentSession()
                .createQuery("Select count(o) from OrderHistory o")
                .uniqueResult()).intValue();
    }
}
