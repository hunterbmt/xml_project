/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;

import com.vteam.xml_project.hibernate.orm.CardCode;
import com.vteam.xml_project.hibernate.orm.SearchCache;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TH11032013
 */
@Repository
public class SearchCacheDAO extends BaseDAO {

    public SearchCache getSearchCacheByQuery(String query) {
        String sql = "From SearchCache where query = :query";
        Query q = this.sessionFactory.getCurrentSession().createQuery(sql);
        q.setParameter("query", query);
        SearchCache result = (SearchCache) q.uniqueResult();
        return result;
    }
}
