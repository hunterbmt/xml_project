<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : ongoingBid_list.xsl
    Created on : April 5, 2013, 11:26 AM
    Author     : Crick
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"> 
    <xsl:output method="xml" omit-xml-declaration="yes" version="1.0" encoding="UTF-8" indent="yes"/>
    <xsl:param name="cDate"/>
    <xsl:template match="/">        
        <xsl:apply-templates/>            
    </xsl:template>
    
    <xsl:template match="bidList">    
        <xsl:variable name="datetime-punctuation" select="'/: '" />
        <xsl:variable name="stripped-current-datetime"
                      select="number(translate($cDate, $datetime-punctuation, ''))" />   
        <bidList>            
            <xsl:for-each 
                select="//bid[
                            isCompleted != 1 and
                            number(translate(start_date, $datetime-punctuation, '')) 
                            &lt;= $stripped-current-datetime 
                            and 
                            number(translate(end_date, $datetime-punctuation, ''))
                            &gt; $stripped-current-datetime
                            ]"
            ><bid>
                    <id><xsl:value-of select="id"/></id>
                    <last_userid><xsl:value-of select="last_userid"/></last_userid>
                    <last_username><xsl:value-of select="last_username"/></last_username>
                    <cost><xsl:value-of select="cost"/></cost>
                    <product_id><xsl:value-of select="product_id"/></product_id>
                    <product_name><xsl:value-of select="product_name"/></product_name>
                    <current_price><xsl:value-of select='format-number(current_price,"###,###")'/> VND</current_price>
                    <start_date><xsl:value-of select="start_date"/></start_date>
                    <end_date><xsl:value-of select="end_date"/></end_date>
                    <last_edit><xsl:value-of select="last_edit"/></last_edit>
                </bid>
            </xsl:for-each>  
        </bidList>
                         
    </xsl:template>
</xsl:stylesheet>
