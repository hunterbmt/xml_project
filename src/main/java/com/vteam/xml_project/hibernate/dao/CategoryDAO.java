/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;

import com.vteam.xml_project.hibernate.orm.Category;
import com.vteam.xml_project.hibernate.orm.Product;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author phitt60230
 */
@Repository
public class CategoryDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    
    
    public  List<Category> getCategoryList() throws HibernateException {
        Query query = this
                .sessionFactory
                .getCurrentSession()
                .createQuery("FROM Category");
		return query.list();    
    }  

    public Category getCategoryById(Integer category_id) throws HibernateException {
        Query query = this
                .sessionFactory
                .getCurrentSession()
                .createQuery("FROM Category where id = ?");
        query.setInteger(0, category_id);
        return (Category) query.uniqueResult();
    }
}
