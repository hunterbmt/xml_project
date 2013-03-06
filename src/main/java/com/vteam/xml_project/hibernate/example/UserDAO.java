/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.example;

//import com.vteam.xml_project.hibernate.orm.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Administrator
 */
@Repository
public class UserDAO extends BaseDAO {

    public UserDAO() {
    }

    public User findUserByUuid(String uuid) throws HibernateException {
        User returnUser ;
        String sql = "From User where uuid = ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setString(0, uuid);
        returnUser = (User) query.uniqueResult();
        return returnUser;
    }
    public User findUserByUUIDAndPassword(String uuid,String password) throws HibernateException{
        String sql ="From User where uuid = ? and password = ?";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setString(0, uuid);
        query.setString(1, password);
        return (User) query.uniqueResult();
    }

}
