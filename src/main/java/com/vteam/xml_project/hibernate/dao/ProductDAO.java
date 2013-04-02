/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;

import com.vteam.xml_project.hibernate.orm.Product;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author phitt60230
 */
@Repository
public class ProductDAO extends BaseDAO {

    public List<Product> getProductList(int page, int pageSize) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product WHERE status = :s");
        // get only the on-going bid products ( status = 2)
        query.setParameter("s", Product.Status.ONBID);
        query = query.setFirstResult(pageSize * (page - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }

    public List<Product> getProductList() throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product");
        return query.list();
    }

    public List<Product> getProductListInorgeStatus(int page, int pageSize) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product");
        query = query.setFirstResult(pageSize * (page - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }

    public Product getProductById(int product_id) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product where id = :pid");
        query.setParameter("pid", product_id);
        return (Product) query.uniqueResult();
    }

    public List<Product> searchProduct(String txtSearch, int page, int pageSize) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product where productName LIKE :q or description LIKE :q AND status = :s");
        query.setParameter("q", "%" + txtSearch + "%");
        query.setParameter("s", Product.Status.ONBID);
        query = query.setFirstResult(pageSize * (page - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }

    public List<Product> searchProductByCategoryId(int category_id, int page, int pageSize) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product where category.id =:id AND status = :s");
        query.setParameter("id", category_id);
        query.setParameter("s", Product.Status.ONBID);
        query = query.setFirstResult(pageSize * (page - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }

    public List<Product> searchProductByTagID(int tagId, int page, int pageSize) throws HibernateException {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product product where :tag in elements(product.tagses) AND product.status = :s");
        query.setParameter("tag", tagId);
        query.setParameter("s", Product.Status.ONBID);
        query = query.setFirstResult(pageSize * (page - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }

    public List<Product> getProductNameList(int page, int pageSize) {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product where status = :s");
        query.setParameter("s", Product.Status.AVAILABLE);

        query = query.setFirstResult(pageSize * (page - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }

    public List<Product> getAllProductNameList(int page, int pageSize) {
        Query query = this.sessionFactory
                .getCurrentSession()
                .createQuery("FROM Product ");

        query = query.setFirstResult(pageSize * (page - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }

    public int getNumberOfProduct() {
        return ((Long) this.getSessionFactory()
                .getCurrentSession()
                .createQuery("Select count(product) from Product product")
                .uniqueResult()).intValue();
    }
}
