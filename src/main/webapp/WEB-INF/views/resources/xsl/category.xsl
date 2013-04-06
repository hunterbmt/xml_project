<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : category.xsl
    Created on : March 31, 2013, 9:46 AM
    Author     : Lenovo
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="categoryList">
        <xsl:for-each select="category">
            <li class="submenu"> 
                <a class="nav" href="javascript:void(0)" onclick="searchByCategoryAtLocal({id},1)" id= "category_id">
                    <xsl:value-of select="name"/>
                </a> 
            </li>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
