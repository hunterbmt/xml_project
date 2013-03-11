package com.vteam.xml_project.hibernate.orm;
// Generated Mar 11, 2013 2:33:25 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tags generated by hbm2java
 */
@Entity
@Table(name="tbl_tags",catalog="xml_project"
)
public class Tags  implements java.io.Serializable {


     private Integer id;
     private String name;
     private String description;
     private Set tblTagsProducts = new HashSet(0);

    public Tags() {
    }

	
    public Tags(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Tags(String name, String description, Set tblTagsProducts) {
       this.name = name;
       this.description = description;
       this.tblTagsProducts = tblTagsProducts;
    }
   
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="name", nullable=false, length=50)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="description", nullable=false, length=100)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="tblTags")
    public Set getTblTagsProducts() {
        return this.tblTagsProducts;
    }
    
    public void setTblTagsProducts(Set tblTagsProducts) {
        this.tblTagsProducts = tblTagsProducts;
    }




}


