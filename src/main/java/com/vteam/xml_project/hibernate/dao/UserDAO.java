/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import com.vteam.xml_project.hibernate.orm.User;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TH31012013
 */
@Repository
public class UserDAO extends BaseDAO{
    public UserDAO() {
    }
    
    public User findUserByUuid(String uuid) throws HibernateException {
        User returnUser ;
        String sql = "From User where user_id = ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setString(0, uuid);
        returnUser =  (User) query.uniqueResult();
        return returnUser;
    }
    public User findUserByEmail(String email) throws HibernateException {
        User returnUser ;
        String sql = "From User where email = ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setString(0, email);
        returnUser =  (User) query.uniqueResult();
        return returnUser;
    }
  public User findUserByEmailAndPassword(String uuid,String password) throws HibernateException{
        String sql ="From User where email = ? and password = ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setString(0, uuid);
        query.setString(1, password);
        return (User) query.uniqueResult();
    }
}
