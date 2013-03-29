/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;

import com.vteam.xml_project.hibernate.orm.Tags;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author phitt60230
 */
@Repository
public class TagsDAO extends BaseDAO {
    
    public Tags getTagById (int id) throws HibernateException {
        Query query = this
                .sessionFactory
                .getCurrentSession()
                .createQuery("FROM Tags WHERE id=:id");
        query.setParameter("id", id);
        return (Tags) query.uniqueResult();
    }
    
    public List<Tags> getTagsList() throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Tags");
        return query.list();
    }
}
