<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : testPaging.xsl
    Created on : April 5, 2013, 4:25 PM
    Author     : Crick
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" version="1.0" encoding="UTF-8" indent="yes"/>

    
    <xsl:template name="hotBidProductIDs" match="/">
        <xsl:variable name="sortOrder" select="'descending'"/>
        <hot_bid_products>
            <xsl:for-each select="bidList/bid[
                            biddedCounter >= 0
                        ]">
                <xsl:sort select="biddedCounter" order="$sortOrder"/>
                <product_id>            
                    <xsl:value-of select="product_id"/>
                </product_id>
            </xsl:for-each>                    
        </hot_bid_products>
    </xsl:template>
</xsl:stylesheet>
