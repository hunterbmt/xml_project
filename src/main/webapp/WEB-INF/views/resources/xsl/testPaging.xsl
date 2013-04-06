<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : testPaging.xsl
    Created on : April 5, 2013, 4:25 PM
    Author     : Crick
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <!--<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>-->
<!--    <xsl:output method="xml" omit-xml-declaration="yes" version="1.0" encoding="UTF-8" indent="yes"/>-->
    <xsl:output method="html" version="3.2" encoding="UTF-8"/>
    <xsl:param name="Page" select="1" />
    <xsl:param name="PageSize" select="2" />
 
    <xsl:template name="results" match="/">


        <xsl:variable name="numberOfRecords" select="count(MenuItems//Item)"/>
        <xsl:variable name="selectedRowCount" select="floor((number($numberOfRecords)-1) div $PageSize)+1"/>


        <xsl:for-each select="MenuItems//Item">
            <!-- Pagination logic -->
            <xsl:if test="position() &gt;= ($Page * $PageSize) + 1">
                <xsl:if test="position() &lt;= $PageSize + ($PageSize * $Page)">

                    <!-- Do display here -->
                    <xsl:value-of select=".//name"/>
         
                </xsl:if>
            </xsl:if>
        </xsl:for-each>


        <!-- Prev link for pagination -->
        <xsl:choose>
            <xsl:when test="number($Page)-1 &gt;= 0">&#160;
                <A>
                    <xsl:attribute name="href">_dirresult?page=<xsl:value-of select="number($Page)-1"/>&amp;pagesize=<xsl:value-of 
                            select="$PageSize"/>
                    </xsl:attribute>
          &lt;&lt;Prev
                </A>
            </xsl:when>
            <xsl:otherwise>
                <!-- display something else -->
            </xsl:otherwise>
        </xsl:choose>
      
        <xsl:if test="$selectedRowCount &gt; 1">
       &#160;
            <b class="blacktext">
                <xsl:value-of select="number($Page)+1"/>&#160;of&#160;<xsl:value-of 
                    select="number($selectedRowCount)"/>
            </b>&#160;
        </xsl:if>
               
        <!-- Next link for pagination -->
        <xsl:choose>
            <xsl:when test="number($Page)+1 &lt; number($selectedRowCount)">&#160;
                <A>
                    <xsl:attribute name="href">_dirresult?page=<xsl:value-of select="number($Page)+1"/>&amp;pagesize=<xsl:value-of 
                            select="$PageSize"/>
                    </xsl:attribute>
                    Next&gt;&gt;
                </A>
            </xsl:when>
            <xsl:otherwise>
                <!-- display something else -->
            </xsl:otherwise>
        </xsl:choose>

   
    </xsl:template>
</xsl:stylesheet>
