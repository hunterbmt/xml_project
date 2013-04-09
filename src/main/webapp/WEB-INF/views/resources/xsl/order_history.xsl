<?xml version="1.0" encoding="UTF-8"?>
   <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"> 
    <xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="yes" indent="yes"/>
    <xsl:param name="user_email"/>
    <xsl:template match="/">        
        <xsl:apply-templates/>            
    </xsl:template>
    <xsl:template match="/">          
            <xsl:for-each 
                select="//user[email=$user_email]/orderList/orderHistory">
                <tr>
                    <td>
                        <xsl:value-of select="productName"/>
                    </td>
                    <td>
                        <xsl:value-of select="address"/>
                    </td>
                    <td>
                        <xsl:value-of select="date"/>
                    </td>
                    <td>
                        <xsl:value-of select="ammount"/>
                    </td>
                </tr>
                
            </xsl:for-each>  
    </xsl:template>
</xsl:stylesheet>
