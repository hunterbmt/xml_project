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
    <xsl:param name="Page" select="0" />
    <xsl:param name="PageSize" select="5" />
    <xsl:param name="_target" select="bid" />
 
    <xsl:template name="results" match="/">
        <xsl:variable name="numberOfRecords" select="count(bidList/bid)"/>
        <xsl:variable name="selectedRowCount" select="floor((number($numberOfRecords)-1) div $PageSize)+1"/>
        <xsl:variable name="target" select="$_target"/>
        
        <div class="widget widget-table action-table">
            <div class="widget-header">
                <i class="icon-list-alt"></i>
                <h3>Ongoing Bids</h3>
            </div>
            <div class="widget-content">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Product</th>
                            <th>Start date</th>
                            <th>Current user</th>
                            <th>Current price</th>
                            <th>Last bid</th>
                            <th class="td-actions" style="width: 8%"></th>
                        </tr>
                    </thead>
                    <tbody id="onGoingBid">
                        <xsl:for-each select="bidList/bid">
                            <!-- Pagination logic -->
                            <xsl:if test="position() &gt;= ($Page * $PageSize) + 1">
                                <xsl:if test="position() &lt;= $PageSize + ($PageSize * $Page)">
                                    <tr>
                                        <td>
                                            <xsl:value-of select="id"/>
                                        </td>
                                        <td>
                                            <xsl:value-of select="product_name"/>
                                        </td>
                                        <td>
                                            <xsl:value-of select="start_date"/>
                                        </td>
                                        <td>
                                            <xsl:value-of select="last_username"/>
                                        </td>
                                        <td>
                                            <xsl:value-of select="current_price"/>
                                        </td>
                                        <td>
                                            <xsl:value-of select="last_edit"/>
                                        </td>
                                        <td class="td-actions">
                                            <a href="javascript:void(0)" 
                                               onclick="bid_details({id})" class="btn btn-warning btn-small">
                                                <i class="btn-icon-only icon-edit"></i>
                                            </a>
                                        </td>
                                    </tr>                                    
                                </xsl:if>
                            </xsl:if>
                        </xsl:for-each>
                    </tbody>
                </table>    
                <div class="pagination-bar" style="padding-left:20px;">
                    <!-- Prev link for pagination -->
                    <xsl:choose>
                        <xsl:when test="number($Page)-1 &gt;= 0">&#160;
                            <A>
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$target"/>?page=<xsl:value-of select="number($Page)-1"/>&amp;pagesize=<xsl:value-of 
                                        select="$PageSize"/>
                                </xsl:attribute>
                            &lt;&lt;|
                            </A>
                        </xsl:when>
                        <xsl:otherwise>
                            &lt;&lt;|
                        </xsl:otherwise>
                    </xsl:choose>
                    <!-- number of current page out of total -->
                    <xsl:if test="$selectedRowCount &gt; 1">
                        &#160;Page<xsl:text> </xsl:text>
                        <b class="blacktext">
                            <xsl:value-of select="number($Page)+1"/>&#160;of&#160;<xsl:value-of 
                                select="number($selectedRowCount)"/>
                        </b>&#160;
                    </xsl:if>
                    <!-- Next link for pagination -->
                    <xsl:choose>
                        <xsl:when test="number($Page)+1 &lt; number($selectedRowCount)">&#160;
                            <A>
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$target"/>?page=<xsl:value-of select="number($Page)+1"/>&amp;pagesize=<xsl:value-of 
                                        select="$PageSize"/>
                                </xsl:attribute>
                                |&gt;&gt;
                            </A>
                        </xsl:when>
                        <xsl:otherwise>
                            |&gt;&gt;
                        </xsl:otherwise>
                    </xsl:choose>
                </div>                
            </div>
        </div> 
    </xsl:template>
</xsl:stylesheet>
