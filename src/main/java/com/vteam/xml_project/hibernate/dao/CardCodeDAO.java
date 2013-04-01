/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.hibernate.dao;

import com.vteam.xml_project.hibernate.orm.CardCode;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TH11032013
 */
@Repository
public class CardCodeDAO extends BaseDAO{
     public CardCode getCardCodeByCode(String code){
        String sql = "From CardCode where code = :code";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("code", code);
        CardCode result =  (CardCode) query.uniqueResult();
        if(result.getUser() == null){
            return result;
        }
        return null;
     }
    public List<CardCode> getCardCodeToPrint(){
        Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(CardCode.class);
        cr.add(Restrictions.isNull("user"));
        return cr.list();
    }
}
