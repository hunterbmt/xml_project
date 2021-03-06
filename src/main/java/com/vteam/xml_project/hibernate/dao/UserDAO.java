/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import com.vteam.xml_project.hibernate.orm.Users;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TH31012013
 */
@Repository
public class UserDAO extends BaseDAO{
    public UserDAO() {
    }
    
    public Users findUserByUuid(Integer uuid) throws HibernateException {
        Users returnUser ;
        String sql = "From Users where id = ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setInteger(0,uuid);
        returnUser =  (Users) query.uniqueResult();
        return returnUser;
    }
    public Users findUserByEmail(String email) throws HibernateException {
        Users returnUser ;
        String sql = "From Users where email = ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setString(0, email);
        returnUser =  (Users) query.uniqueResult();
        return returnUser;
    }
  public Users findUserByEmailAndPassword(String email,String password) throws HibernateException{
        String sql ="From Users where email = ? and password = ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setString(0, email);
        query.setString(1, password);
        return (Users) query.uniqueResult();
    }
  public List<Users> getUserList() throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Users");
        return query.list();
    }
}
