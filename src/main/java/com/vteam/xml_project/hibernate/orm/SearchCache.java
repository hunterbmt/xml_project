package com.vteam.xml_project.hibernate.orm;
// Generated Mar 11, 2013 2:33:25 PM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CardCode generated by hbm2java
 */
@Entity
@Table(name = "tbl_search_cache", catalog = "xml_project")
public class SearchCache implements java.io.Serializable {

    private Integer id;
    private String query;
    private String fileName;
    private Date cacheDate;

    public SearchCache() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "query", nullable = false, length = 255)
    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Column(name = "file_name", nullable = false, length = 255)
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cache_date", length = 19)
    public Date getCacheDate() {
        return this.cacheDate;
    }

    public void setCacheDate(Date cacheDate) {
        this.cacheDate = cacheDate;
    }
}
