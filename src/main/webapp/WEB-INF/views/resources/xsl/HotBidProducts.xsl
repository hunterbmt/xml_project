<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : testPaging.xsl
    Created on : April 5, 2013, 4:25 PM
    Author     : Crick
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8"/>
    <xsl:template name="hotBidProducts" match="/">
            <xsl:for-each select="productList//product">
                <div class= "bid span4" style="height: 400px">
                    <div class= "bidHolder">
                        <a style="margin-left: 14%;" href="javascript:void(0)" class="bidImage imgLink" onclick ="view_product_detail({id})">
                            <img src="{image}" style="height:286px"/>
                        </a>
                        <div class= "convo attribution clearfix">
                            <a href="javascript:void(0)" onclick ="view_product_detail({id})">
                                <h5><xsl:value-of select="name"/></h5>
                            </a>
                            <p><xsl:value-of select="shortDescription"/></p>
                        </div>
                    </div>
                </div>
            </xsl:for-each>                    
    </xsl:template>
</xsl:stylesheet>
