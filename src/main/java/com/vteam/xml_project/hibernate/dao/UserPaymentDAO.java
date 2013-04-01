/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;

import com.vteam.xml_project.hibernate.orm.UserPayment;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TH11032013
 */
@Repository
public class UserPaymentDAO extends BaseDAO{
        public UserPayment getPaymentById(int payment_id) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM UserPayment where id = ?");
        query.setParameter(0, payment_id);
        return (UserPayment) query.uniqueResult();
    }
    public List<UserPayment> getPaymentHistorysList(int id) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM UserPayment where user_id = :uid");
        query.setParameter("uid", id);
        return query.list();
    }
}
