/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;

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
public class ProductDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public  List<Product> getProductList(int page, int pageSize) throws HibernateException {
        Query query = this
                .sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product");
       
        query = query.setFirstResult(pageSize * (page - 1));
		query.setMaxResults(pageSize);
		return query.list();    
    }  

    public Product getProductById(Integer product_id) throws HibernateException {
        Query query = this
                .sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product where id = ?");
        query.setInteger(0, product_id);
        return (Product) query.uniqueResult();
    }
}
